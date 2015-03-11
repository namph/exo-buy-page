package com.exoplatform.buypage.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.utils.mail.MailConfiguration;
import com.exoplatform.buypage.utils.mail.MailSender;
import com.exoplatform.buypage.utils.mail.MailSender.MailHeaders;

@Controller
@RequestMapping(value = "/WebHook")
public class RestWebHookController {
  Logger log = LoggerFactory.getLogger(RestWebHookController.class);
  @Inject
  IService gatewayService;
  
  private Configuration exoBuyAdminConfiguration;
  private MailSender mailSender;

  public RestWebHookController(){
    try {
      exoBuyAdminConfiguration = new PropertiesConfiguration(MailConfiguration.CONFIGURATION_FILE);
    } catch (ConfigurationException e) {
      log.error("Can not load configuration file", e);
    } 
    mailSender = new MailSender(exoBuyAdminConfiguration);
  }
  
  @RequestMapping(value = "/test/{id}",method = RequestMethod.GET,produces = "application/json")
  @ResponseBody
  public String testSendMail(@PathVariable(value = "id") String id){
    
    Map<MailHeaders, String> mailHeaders = new HashMap<MailHeaders, String>();
    mailHeaders.put(MailHeaders.TO, "tungdt.dtt@gmail.com");
    mailHeaders.put(MailHeaders.SUBJECT, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_SUBJECT));
    mailHeaders.put(MailHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
    mailHeaders.put(MailHeaders.FROM, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SENDER));
    
    String mailTemplate = null;
    try {
      String mailTemplateConfigPath = exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_TEMPLATE);
      mailTemplate = this.getClass().getResource(mailTemplateConfigPath).getPath();
    } catch (Exception e1) {
     log.error(e1.getMessage());
     return "Test failured: " + id;
    }
    
    Map<String, String> templateProperties  = new HashMap<String, String>();
    templateProperties.put("expired.date", "10/10/2020");
    templateProperties.put("customer.name", "Mr eXo");
    templateProperties.put("platform.download.url", exoBuyAdminConfiguration.getString("exo.buy.mail.plf.download.url"));
    templateProperties.put("platform.unlock.key.url", exoBuyAdminConfiguration.getString("exo.buy.mail.unlock.key.url"));
    templateProperties.put("product.code", "DFGUIJHJNKNJH");
    templateProperties.put("unlock.code", "VfdfdHfdvIHFIDIHddJIJJ");
    templateProperties.put("support.email", exoBuyAdminConfiguration.getString("exo.buy.mail.support.email"));
   
    try {
      mailSender.sendMail(mailHeaders, mailTemplate, templateProperties);
    } catch (Exception e) {
      return "Test failured: " + id;
    }
    
    return "Test successfully: " + id;
  }
}
