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

/**
 * Collects names of all used mail properties
 *
 * @author eXo Platform SAS
 * @since 1.0.0
 */
public interface MailConfiguration {
  // Mail sender configuration
  
  public final static String CONFIGURATION_FILE                       = "exo-buy-configuration.properties";

  public final static String EXO_BUY_MAIL_AUTH                        = "exo.buy.mail.smtp.auth";

  public final static String EXO_BUY_MAIL_HOST                        = "exo.buy.mail.host";

  public final static String EXO_BUY_MAIL_PORT                        = "exo.buy.mail.port";

  public final static String EXO_BUY_MAIL_SMTP_PASSWORD               = "exo.buy.mail.smtp.auth.password";

  public final static String EXO_BUY_MAIL_SMTP_AUTH_USERNAME          = "exo.buy.mail.smtp.auth.username";

  public final static String EXO_BUY_MAIL_SMTP_SOCKETFACTORY_CLASS    = "exo.buy.mail.smtp.socketFactory.class";

  public final static String EXO_BUY_MAIL_SMTP_SOCKETFACTORY_FALLBACK = "exo.buy.mail.smtp.socketFactory.fallback";

  public final static String EXO_BUY_MAIL_SMTP_SOCKETFACTORY_PORT     = "exo.buy.mail.smtp.socketFactory.port";

  public final static String EXO_BUY_MAIL_TRANSPORT_PROTOCOL          = "exo.buy.mail.transport.protocol";
  

  public final static String EXO_BUY_MAIL_ADMIN_EMAIL                 = "exo.buy.mail.admin.email";
  
  public final static String EXO_BUY_MAIL_SENDER                      = "exo.buy.mail.sender";
  
  public final static String EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_TEMPLATE  = "exo.buy.mail.subscription.information.template";

  public final static String EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_SUBJECT   = "exo.buy.mail.subscription.information.subject";
  
  
  public final static String EXO_BUY_MAIL_TECHNICAL_TEAM_EMAIL            = "exo.buy.mail.technical.email";
  
  public final static String EXO_BUY_MAIL_SUBCRIPTION_TECHNICAL_TEMPLATE  = "exo.buy.mail.subscription.technical.template";

  public final static String EXO_BUY_MAIL_SUBCRIPTION_TECHNICAL_SUBJECT   = "exo.buy.mail.subscription.technical.subject";

}
