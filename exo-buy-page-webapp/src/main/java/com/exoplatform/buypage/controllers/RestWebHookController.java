package com.exoplatform.buypage.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.Subscription;
import com.braintreegateway.WebhookNotification;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.PlanDTO;
import com.exoplatform.buypage.utils.CommonUtils;
import com.exoplatform.buypage.utils.UnLockUtils;
import com.exoplatform.buypage.utils.mail.MailConfiguration;
import com.exoplatform.buypage.utils.mail.MailSender;
import com.exoplatform.buypage.utils.mail.MailSender.MailHeaders;

@Controller
@RequestMapping(value = "/webhook")
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
      log.info(mailTemplate);
      System.out.println(mailTemplate);
    } catch (Exception e1) {
     e1.printStackTrace();
     log.error(e1.getMessage());
     return "Test failured: " + id;
    }
    String nbUser = "5";
    String productCode = UnLockUtils.generateProductCode();
    String unlockKey =  UnLockUtils.generateKey(productCode, nbUser);
    
    Map<String, String> templateProperties  = new HashMap<String, String>();
    templateProperties.put("expired.date", "10/10/2020");
    templateProperties.put("customer.name", "Mr eXo");
    templateProperties.put("platform.download.url", exoBuyAdminConfiguration.getString("exo.buy.mail.plf.download.url"));
    templateProperties.put("platform.unlock.key.url", exoBuyAdminConfiguration.getString("exo.buy.mail.unlock.key.url"));
    templateProperties.put("product.code", productCode);
    templateProperties.put("unlock.code", unlockKey);
    templateProperties.put("support.email", exoBuyAdminConfiguration.getString("exo.buy.mail.support.email"));
   
    try {
      mailSender.sendMail(mailHeaders, mailTemplate, templateProperties);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      return "Test failured: " + id;
    }
    
    return "Test successfully: " + id;
  }
  
  private void sendMailToUser(Subscription subscription) throws Exception{
    Map<MailHeaders, String> mailHeaders = new HashMap<MailHeaders, String>();
    
    Customer customer = subscription.getTransactions().get(0).getCustomer();
    String customerEmail = customer.getEmail();
    String customerName = customer.getFirstName();
    
    mailHeaders.put(MailHeaders.TO, customerEmail);
    mailHeaders.put(MailHeaders.SUBJECT, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_SUBJECT));
    mailHeaders.put(MailHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
    mailHeaders.put(MailHeaders.FROM, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SENDER));
    
    String mailTemplate = null;
    try {
      String mailTemplateConfigPath = exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_INFORMATION_TEMPLATE);
      mailTemplate = this.getClass().getResource(mailTemplateConfigPath).getPath();
    } catch (Exception e) {
     log.info("Can not sent mail Subscription Information to customer: " + customerEmail, e);
     log.error("Can not load Email Template", e);
     throw e;
    }
    PlanDTO planDTO = new  PlanDTO(subscription.getPlanId());
    String nbUser = planDTO.getOptionUser().toString();
    String productCode = UnLockUtils.generateProductCode();
    String unlockKey =  UnLockUtils.generateKey(productCode, nbUser);
    int licenseYear =  planDTO.getYearNumber();
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.YEAR, licenseYear);
    String expiredDateStr = CommonUtils.partString(cal.getTime(), "dd/MM/yyyy");
    
    
    
    Map<String, String> templateProperties  = new HashMap<String, String>();
    templateProperties.put("expired.date", expiredDateStr);
    templateProperties.put("customer.name", customerName);
    templateProperties.put("platform.download.url", exoBuyAdminConfiguration.getString("exo.buy.mail.plf.download.url"));
    templateProperties.put("platform.unlock.key.url", exoBuyAdminConfiguration.getString("exo.buy.mail.unlock.key.url"));
    templateProperties.put("product.code", productCode);
    templateProperties.put("unlock.code", unlockKey);
    templateProperties.put("support.email", exoBuyAdminConfiguration.getString("exo.buy.mail.support.email"));
   
    try {
      mailSender.sendMail(mailHeaders, mailTemplate, templateProperties);
    } catch (Exception e) {
      log.info("Can not sent mail Subscription Information to customer: " + customerEmail, e);
      throw e;
    }
  }
  
  @RequestMapping(value = "/handle",method = RequestMethod.GET,produces = {"text/html"})
  @ResponseBody
  public Object handle(@RequestParam (value = "bt_challenge", required=false) String bt_challenge){
    BraintreeGateway gateway = ((com.exoplatform.buypage.gateway.ServiceImpl)gatewayService).getGateway();
    //response.type("text/html");
    return gateway.webhookNotification().verify(bt_challenge);
  }
  
  @RequestMapping(value = "/handle",method = RequestMethod.POST,produces = {"text/html"})
  @ResponseBody
  public Object handle(spark.Request request, spark.Response response,
                       @RequestParam (value = "subscriptionId", required=false) String subscriptionId ){
    
    System.out.println("START Webhook call");
    System.out.println(request.toString());
    
    BraintreeGateway gateway = ((com.exoplatform.buypage.gateway.ServiceImpl)gatewayService).getGateway();
    
    Subscription subscription = null;
    try {
      String bt_signature = request.queryParams("bt_signature");
      String bt_payload = request.queryParams("bt_payload");
      
      System.out.println("bt_signature: " + bt_signature);
      System.out.println("bt_payload: " + bt_payload);
      
      WebhookNotification webhookNotification = gateway.webhookNotification().parse(bt_signature, bt_payload);
      
      subscription = webhookNotification.getSubscription();
      
      String result = ("[Webhook Received " + webhookNotification.getTimestamp().getTime() + "] | Kind: " + webhookNotification.getKind() + " | Subscription: " + subscription.getId());
      log.info(result);
      System.out.println(subscription.toString());

    } catch (Exception e) {
      log.info("Can not load subscription from braintree, test load subscription buy subscriptionId = " + subscriptionId, e);
      subscription = gateway.subscription().find(subscriptionId);
    }
    try {
      sendMailToUser(subscription);
    } catch (Exception e) {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("",HttpStatus.OK);
  }

}