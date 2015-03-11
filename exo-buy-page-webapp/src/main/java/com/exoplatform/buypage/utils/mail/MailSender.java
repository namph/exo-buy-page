/*
 * Copyright (C) 2003-2015 eXo Platform SAS.
 *
 * This file is part of UXPaaS :: MGT :: Services.
 *
 * UXPaaS :: MGT :: Services is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * UXPaaS :: MGT :: Services software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UXPaaS :: MGT :: Services; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.buypage.utils.mail;

import static com.exoplatform.buypage.utils.mail.MailConfiguration.*;

import com.exoplatform.buypage.utils.mail.Deserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for sending emails from exo-buy-admin. Resolves message body from
 * exo-buy-admin templates
 *
 * @author eXo Platform SAS
 * @since 1.0.0
 */
public class MailSender {
  
  /**
   * Specify mail headers available in {@link MailSender}
   */
  public enum MailHeaders {
    FROM, TO, REPLY_TO, SUBJECT, CONTENT_TYPE
  }

  /**
   * Constant {@code LOG}
   */
  private static final Logger LOG = LoggerFactory.getLogger(MailSender.class);

  private Configuration exoBuyAdminConfiguration;

  /**
   * <p>
   * Constructor for MailSender.
   * </p>
   *
   * @param exoBuyAdminConfiguration a
   *          {@link org.apache.commons.configuration.Configuration} object.
   */
  public MailSender(Configuration exoBuyAdminConfiguration) {
    super();
    if(exoBuyAdminConfiguration!=null)
      this.exoBuyAdminConfiguration = (PropertiesConfiguration) exoBuyAdminConfiguration;
    else{
      try {
        this.exoBuyAdminConfiguration = new PropertiesConfiguration(CONFIGURATION_FILE);
      } catch (ConfigurationException e) {
        LOG.error("Cannot load exo-buy-configuration file.");
      }
    }
  }

  /**
   * Send mail message with body formed from specified template. All information
   * needed for message sending specifies in headers.
   * <p/>
   * If you need to send more than one copy of email, then write needed
   * receivers separated by comma in header
   * {@link com.exoplatform.buypage.utils.mail.MailSender.MailHeaders#TO}.
   *
   * @param mailHeaders - headers which will be set to sent message; supports
   *          headers described in
   *          {@link com.exoplatform.buypage.utils.mail.MailSender.MailHeaders}
   * @param mailTemplate - path to mail template
   * @param templateProperties - variables map to resolve template
   * @throws Exception mail template
   *           was not found; mail sending failed.
   */
  public void sendMail(Map<MailHeaders, String> mailHeaders,
                       String mailTemplate,
                       Map<String, String> templateProperties) throws Exception {
    LOG.info("Mail request {} from template {} with properties {} ", new Object[] { mailHeaders,
        mailTemplate, templateProperties });

    try {
      String body = resolveMessageBody(mailTemplate, templateProperties);
      Session mailSession = getMailSession();
      MimeMessage message = prepareMailMessage(mailHeaders, body, mailSession);

      Transport.send(message);
    } catch (IOException | MessagingException e) {
      LOG.error("Error during sending mail", e);
      throw new Exception("Unable to send mail. Please contact support.", e);
    }
  }

  /**
   * <p>
   * resolveMessageBody.
   * </p>
   *
   * @param mailTemplateFile a {@link java.lang.String} object.
   * @param templateProperties a {@link java.util.Map} object.
   * @return a {@link java.lang.String} object.
   * @throws java.io.IOException if any.
   * @throws Exception if any.
   */
  private String resolveMessageBody(String mailTemplateFile, Map<String, String> templateProperties) throws IOException, Exception {
    if (mailTemplateFile == null) {
      throw new Exception("Mail template configuration not found. Please contact support.");
    }
    String templateContent = Deserializer.getResourceContent(mailTemplateFile);

    if (templateContent == null) {
      throw new Exception("Mail template from resource " + mailTemplateFile
          + "not found. Please " + "contact support.");
    }
    return Deserializer.resolveTemplate(templateContent, templateProperties);
  }

  /**
   * <p>
   * getMailSession.
   * </p>
   *
   * @return a {@link javax.mail.Session} object.
   */
  private Session getMailSession() {
    Properties props = new Properties();

    // SMTP protocol properties
    props.put("mail.transport.protocol",
              exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_TRANSPORT_PROTOCOL));
    props.put("mail.smtp.host", exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_HOST));
    props.put("mail.smtp.port", exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_PORT));
    props.put("mail.smtp.auth", exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_AUTH));

    if (Boolean.parseBoolean(props.getProperty("mail.smtp.auth"))) {
      props.put("mail.smtp.socketFactory.port",
                exoBuyAdminConfiguration.getProperty(EXO_BUY_MAIL_SMTP_SOCKETFACTORY_PORT));
      props.put("mail.smtp.socketFactory.class",
                exoBuyAdminConfiguration.getProperty(EXO_BUY_MAIL_SMTP_SOCKETFACTORY_CLASS));
      props.put("mail.smtp.socketFactory.fallback",
                exoBuyAdminConfiguration.getProperty(EXO_BUY_MAIL_SMTP_SOCKETFACTORY_FALLBACK));

      final String mailUserName = exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_SMTP_AUTH_USERNAME);
      final String mailPassword = exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_SMTP_PASSWORD);

      return Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(mailUserName, mailPassword);
        }
      });
    } else {
      return Session.getInstance(props);
    }
  }

  /**
   * <p>
   * prepareMailMessage.
   * </p>
   *
   * @param mailHeaders a {@link java.util.Map} object.
   * @param body a {@link java.lang.String} object.
   * @param mailSession a {@link javax.mail.Session} object.
   * @return a {@link javax.mail.internet.MimeMessage} object.
   * @throws javax.mail.MessagingException if any.
   * @throws javax.mail.internet.AddressException if any.
   */
  private MimeMessage prepareMailMessage(Map<MailHeaders, String> mailHeaders,
                                         String body,
                                         Session mailSession) throws MessagingException,
                                                             AddressException {
    String subject = mailHeaders.get(MailHeaders.SUBJECT);
    String from = mailHeaders.containsKey(MailHeaders.FROM) ? mailHeaders.get(MailHeaders.FROM)
                                                           : exoBuyAdminConfiguration.getString(EXO_BUY_MAIL_SENDER);
    String to = mailHeaders.get(MailHeaders.TO);
    String replyTo = mailHeaders.get(MailHeaders.REPLY_TO);

    String contentTypeHeader = mailHeaders.get(MailHeaders.CONTENT_TYPE);
    String contentType = "text/html";
    if (contentTypeHeader != null && !contentTypeHeader.isEmpty()) {
      contentType = contentTypeHeader;
    }

    MimeMessage message = new MimeMessage(mailSession);
    message.setContent(body, contentType);

    message.setSubject(subject);
    message.setFrom(new InternetAddress(from));
    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

    if (replyTo != null) {
      message.setReplyTo(InternetAddress.parse(replyTo));
    }
    return message;
  }

  /**
   * Send email quetly. If any error occurred then logs it and don't throw it.
   * Use if you want send mail, and don't want to catch a exception.
   *
   * @param mailHeaders a {@link java.util.Map} object.
   * @param mailTemplateFile a {@link java.lang.String} object.
   * @param templateProperties a {@link java.util.Map} object.
   */
  public void sendMailQuietly(Map<MailHeaders, String> mailHeaders,
                              String mailTemplateFile,
                              Map<String, String> templateProperties) {
    try {
      sendMail(mailHeaders, mailTemplateFile, templateProperties);
    } catch (Exception e) {
      LOG.error(e.getLocalizedMessage(), e);
    }
  }

}
