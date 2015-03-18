package com.exoplatform.buypage.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

import com.braintreegateway.AddOn;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.Plan;
import com.braintreegateway.PlanGateway;
import com.braintreegateway.Subscription;
import com.braintreegateway.Transaction;
import com.braintreegateway.WebhookNotification;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.AddonDTO;
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
  
  
  private void sendMailToTechnicalTeam (Subscription subscription,Transaction transaction,
                                        Customer customer, PlanDTO planDTO,
                                        String productCode, String unlockKey) throws Exception{
    String technicalTeamEmail = exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_TECHNICAL_TEAM_EMAIL);
    
    log.info("Start sending email to technical Team: " + technicalTeamEmail);
    System.out.println("Start sending email to technical Team: " + technicalTeamEmail);
  
    Map<MailHeaders, String> mailHeaders = new HashMap<MailHeaders, String>();
    mailHeaders.put(MailHeaders.TO, technicalTeamEmail);
    mailHeaders.put(MailHeaders.SUBJECT, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_TECHNICAL_SUBJECT) + 
                    " " + customer.getFirstName() + " " + customer.getCompany());
    mailHeaders.put(MailHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
    mailHeaders.put(MailHeaders.FROM, exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SENDER));
    
    String mailTemplate = null;
    try {
      String mailTemplateConfigPath = exoBuyAdminConfiguration.getString(MailConfiguration.EXO_BUY_MAIL_SUBCRIPTION_TECHNICAL_TEMPLATE);
      mailTemplate = this.getClass().getResource(mailTemplateConfigPath).getPath();
    } catch (Exception e) {
     log.error("Can not sent mail Techincal Information to team: " + technicalTeamEmail, e);
     throw e;
    }
    
    Map<String, String> templateProperties  = new HashMap<String, String>();
    templateProperties.put("customer.name", customer.getFirstName());
    templateProperties.put("customer.company", customer.getCompany());
    templateProperties.put("customer.email", customer.getEmail());
    templateProperties.put("customer.phone", customer.getPhone());
    templateProperties.put("customer.id", customer.getId());

    templateProperties.put("transaction.date", CommonUtils.partString(transaction.getCreatedAt(), "dd/MM/yyyy hh:mm:ss a zz"));
    templateProperties.put("transaction.id", transaction.getId());
    templateProperties.put("transaction.amount", CommonUtils.convertAmount2String(transaction.getAmount()));
    templateProperties.put("plan.name", planDTO.getName() );
    templateProperties.put("product.code", productCode);
    templateProperties.put("unlock.key", unlockKey);
    
    List<AddOn> listAddonOfTransaction =transaction.getAddOns(); //must get addons from transaction
    List<AddOn> listAddonOfSubscription =subscription.getAddOns();
    StringBuffer bufferListAddon =  new StringBuffer();
    StringBuffer bufferListService =  new StringBuffer();
    for (AddOn addOnT : listAddonOfTransaction) {
      for (AddOn addOnS : listAddonOfSubscription) {
        //addons from transaction don't contain addon's Name so need to get Full Addon object from Braintree
        //AddOn addOnFromBraintree = gatewayService.getAddon(addOn.getId());
        
        //To avoid query Addon data from braintree server,
        //we will get addon information from Subscriptions because Subscriptions contains all addons of all transactions
        if(addOnT.getId().equals(addOnS.getId())){
          AddonDTO addonDTO = new AddonDTO(addOnS.getId(),addOnS.getName(),
                                           addOnS.getDescription(),addOnS.getAmount());
          if(!addonDTO.isService()){
            bufferListAddon.append(addOnS.getName());
            bufferListAddon.append("<br>");
          }else{
            bufferListService.append(addOnS.getName());
            bufferListService.append("<br>");
            
          }
          break;
        }
      }
    }
    
    templateProperties.put("list.addon", bufferListAddon.toString());
    
    templateProperties.put("list.service", bufferListService.toString());
    
    try {
      mailSender.sendMail(mailHeaders, mailTemplate, templateProperties);
      
      log.info("Finish sending email to technical Team: " + technicalTeamEmail);
      System.out.println("Finish sending email to technical Team: " + technicalTeamEmail);
      
    } catch (Exception e) {
      log.info("Can not sent mail Subscription Information to customer: " + customer.getEmail(), e);
      throw e;
    }
    
    
  }
  
  private void sendMailToUser(Subscription subscription, Transaction transaction,
                              Customer customer, PlanDTO planDTO,
                              String productCode, String unlockKey) throws Exception{
    
    log.info("Start sending email to" + customer.getEmail());
    System.out.println("Start sending email to: " + customer.getEmail());
    
    Map<MailHeaders, String> mailHeaders = new HashMap<MailHeaders, String>();
    
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
      
      log.info("Finish sending email to" + customer.getEmail());
      System.out.println("Finish sending email to" + customer.getEmail());
      
    } catch (Exception e) {
      log.info("Can not sent mail Subscription Information to customer: " + customerEmail, e);
      throw e;
    }
  }
  
  @RequestMapping(value = "/handle",method = RequestMethod.GET,produces = {"text/html"})
  @ResponseBody
  public Object handle(@RequestParam (value = "bt_challenge", required=false) String bt_challenge){
    BraintreeGateway gateway = ((com.exoplatform.buypage.gateway.ServiceImpl)gatewayService).getGateway();
    return gateway.webhookNotification().verify(bt_challenge);
  }
  
  @RequestMapping(value = "/handle",method = RequestMethod.POST,produces = {"text/html"})
  @ResponseBody
  public Object handle(HttpServletRequest request,
                       @RequestParam (value = "bt_signature", required=false) String bt_signature,
                       @RequestParam (value = "bt_payload", required=false) String bt_payload){

    System.out.println("START Webhook call");
    log.info("START Webhook call");
    
    String clientIpAddress = request.getRemoteAddr();
    String clientAgent = request.getHeader("user-agent");
    
    System.out.println("clientIpAddress: " + clientIpAddress);
    System.out.println("clientAgent: " + clientAgent);

    BraintreeGateway gateway = ((com.exoplatform.buypage.gateway.ServiceImpl)gatewayService).getGateway();
    
    Subscription subscription = null;
    try {
      WebhookNotification webhookNotification = gateway.webhookNotification().parse(bt_signature, bt_payload);
      
      subscription = webhookNotification.getSubscription();
      
      String result = ("[Webhook Received " + webhookNotification.getTimestamp().getTime() + "] | Kind: " + webhookNotification.getKind() + " | Subscription: " + subscription.getId());
      log.info(result);
      System.out.println(result);
      
      if(webhookNotification.getKind().equals(WebhookNotification.Kind.SUBSCRIPTION_CHARGED_SUCCESSFULLY)){
        //Do nothing
        //Do not send mails
        return new ResponseEntity<String>("", HttpStatus.OK);
      }

    } catch (Exception e) {
      log.info("Can not load subscription from braintree", e);
      e.printStackTrace();
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    
    try {
      List<Transaction> listTransaction = subscription.getTransactions();
      Transaction transaction = listTransaction.get(listTransaction.size()-1);
      Customer customer = gateway.customer().find(transaction.getCustomer().getId());
      Plan plan = gatewayService.getPlan(transaction.getPlanId());
      PlanDTO planDTO = new PlanDTO(transaction.getPlanId(),plan.getName(),plan.getDescription());
      String productCode = customer.getCustomFields().get("product_code");
      if(null == productCode || productCode.length()==0){
        productCode = UnLockUtils.generateProductCode();
      }
      String nbUser = planDTO.getOptionUser().toString();
      String unlockKey =  UnLockUtils.generateKey(productCode, nbUser);
      
      sendMailToUser(subscription, transaction, customer, planDTO, productCode, unlockKey);
      
      sendMailToTechnicalTeam(subscription, transaction, customer, planDTO, productCode, unlockKey);
      
    } catch (Exception e) {
      log.error("Error happen while Webhook call Buy-Page",e);
      e.printStackTrace();
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("",HttpStatus.OK);
  }
  
  /*This method is use for testing only*/
  /*
  @RequestMapping(value = "/handle",method = RequestMethod.POST,produces = {"text/html"})
  @ResponseBody
  public Object handle(HttpServletRequest request,
                       @RequestParam (value = "bt_signature", required=false) String bt_signature,
                       @RequestParam (value = "bt_payload", required=false) String bt_payload,
                       @RequestParam (value = "subscriptionId", required=false) String subscriptionId ){
    
    System.out.println("START Webhook call");
    log.info("START Webhook call");
    
    String clientIpAddress = request.getRemoteAddr();
    String clientAgent = request.getHeader("user-agent");
    
    System.out.println("clientIpAddress: " + clientIpAddress);
    System.out.println("clientAgent: " + clientAgent);

    BraintreeGateway gateway = ((com.exoplatform.buypage.gateway.ServiceImpl)gatewayService).getGateway();
    
    Subscription subscription = null;
    try {
      
      //System.out.println("bt_signature: " + bt_signature);
      //System.out.println("bt_payload: " + bt_payload);
      
      WebhookNotification webhookNotification = gateway.webhookNotification().parse(bt_signature, bt_payload);
      
      subscription = webhookNotification.getSubscription();
      
      String result = ("[Webhook Received " + webhookNotification.getTimestamp().getTime() + "] | Kind: " + webhookNotification.getKind() + " | Subscription: " + subscription.getId());
      log.info(result);
      System.out.println(result);

    } catch (Exception e) {
      log.info("Can not load subscription from braintree, test load subscription buy subscriptionId = " + subscriptionId, e);
      subscription = gateway.subscription().find(subscriptionId);
    }
    try {
      List<Transaction> listTransaction = subscription.getTransactions();
      Transaction transaction = listTransaction.get(listTransaction.size()-1);
      Customer customer = gateway.customer().find(transaction.getCustomer().getId());
      Plan plan = gatewayService.getPlan(transaction.getPlanId());
      PlanDTO planDTO = new PlanDTO(transaction.getPlanId(),plan.getName(),plan.getDescription());
      String productCode = customer.getCustomFields().get("product_code");
      if(null == productCode || productCode.length()==0){
        productCode = UnLockUtils.generateProductCode();
      }
      String nbUser = planDTO.getOptionUser().toString();
      String unlockKey =  UnLockUtils.generateKey(productCode, nbUser);
      
      sendMailToUser(subscription, transaction, customer, planDTO, productCode, unlockKey);
      
      sendMailToTechnicalTeam(subscription, transaction, customer, planDTO, productCode, unlockKey);
      
    } catch (Exception e) {
      log.error("Error happen while Webhook call Buy-Page",e);
      e.printStackTrace();
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("",HttpStatus.OK);
  }*/

}