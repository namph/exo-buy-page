/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.buypage.gateway;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.UnexpectedException;
import com.exoplatform.buypage.model.DTO.AddonDTO;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import com.exoplatform.buypage.model.SubscriptionCustomer;
import com.exoplatform.buypage.utils.CommonUtils;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


public class ServiceImpl implements IService {

  private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);
  public static final String ADMIN_CONFIG_FILE = "cloud.admin.configuration.file";

  public static final String ENVIRONMENT_PROPERTY = "provider.braintree.environment";

  public static final String MERCHANT_ID_PROPERTY = "provider.braintree.merchantId";

  public static final String PUBLIC_KEY_PROPERTY = "provider.braintree.publicKey";

  public static final String PRIVATE_KEY_PROPERTY = "provider.braintree.privateKey";

  public static final String ENCRYPT_KEY_PROPERTY = "provider.braintree.encryptionKey";


  public static final String PREFIX_ADDON = "PLT_";
  public static final String PREFIX_SERVICE = "SV_";
  public static final String PREFIX_PLAN = "EXO_PLT_ENT";
  public static final String PREFIX_DISABLED = "DISABLED";
  public static final String PREFIX_OLD = "OLD";
  public static final String PREFIX_DISCOUNT_TYPE = "_";

  private PropertiesConfiguration adminConfiguration;
  private BraintreeGateway gateway;
  public ServiceImpl(){
    try {
      String envMode = System.getProperty("buypage.braintree.setting.mode");
      String configFilePath = "";
      if (null == envMode || "".equals(envMode) || "dev".equals(envMode)){
        configFilePath = System.getProperty("braintree.dev.configuration.file");
      }else{
        configFilePath = System.getProperty("braintree.prod.configuration.file");
      }
      this.adminConfiguration = new PropertiesConfiguration(configFilePath);
    } catch (ConfigurationException e) {
      log.error("Cannot load admin configuration file.");
    }

    if (this.adminConfiguration != null) {
      Environment env;
      String envp = this.adminConfiguration.getString(ENVIRONMENT_PROPERTY);
      if ("PRODUCTION".equalsIgnoreCase(envp)) {
        env = Environment.PRODUCTION;
      } else if ("DEVELOPMENT".equalsIgnoreCase(envp)) {
        env = Environment.DEVELOPMENT;
      } else if ("SANDBOX".equalsIgnoreCase(envp)) {
        env = Environment.SANDBOX;
      } else {
        log.warn("Braintree environment not specified, will use SANDBOX.");
        env = Environment.SANDBOX;
      }

      String merchantId = this.adminConfiguration.getString(MERCHANT_ID_PROPERTY);
      String publicKey = this.adminConfiguration.getString(PUBLIC_KEY_PROPERTY);
      String privateKey = this.adminConfiguration.getString(PRIVATE_KEY_PROPERTY);
      this.gateway = new BraintreeGateway(env, merchantId, publicKey,privateKey);
    }
  }

  @Override
  public String getEncryptKey() {
    return null;
  }

  private List<Plan> findActivePlans() {
    List<Plan> plans = new ArrayList<Plan>();
    for (Plan plan : gateway.plan().all()) {
      if (!plan.getId().startsWith(PREFIX_DISABLED) && !plan.getId().startsWith(PREFIX_OLD) && plan.getId().startsWith(PREFIX_PLAN)) {
        plans.add(plan);
      }
    }
    return plans;
  }

  @Override
  public Collection<Plan> getActivePlans() {
    return this.findActivePlans();
  }

  @Override
  public Plan getPlan(String id) {
    try {
      List<Plan> plans = findActivePlans();
      for (Plan plan : plans) {
        if (plan.getId().equals(id)) {
          return plan;
        }
      }
    } catch (Exception e) {
      log.error("Failed to connect to payment gateway", e.getMessage());
    }
    return null;
  }

  @Override
  public Collection<AddOn> getActiveAddons() {
    List<AddOn> addons = new ArrayList<AddOn>();
    for (AddOn addon : gateway.addOn().all()) {
      if (!addon.getId().startsWith(PREFIX_DISABLED) && !addon.getId().startsWith(PREFIX_OLD) && (addon.getId().startsWith(PREFIX_ADDON ) || addon.getId().startsWith(PREFIX_SERVICE ))) {
        addons.add(addon);
      }
    }
    return addons;
  }

  @Override
  public Map<String,String> subscribe(String customerId, String subscriptionId, SubscriptionCustomer subsCustomer){

    Map<String,String> result = new HashMap<String,String>();
   // user infos + addons ID + discout ID
    Result<Subscription> subscriptionResult = null;

    Result<Customer> customerResult = createOrUpdateCustomer(customerId, subsCustomer);
    if (!customerResult.isSuccess()) {
      result.put("msg", CommonUtils.getMessageByBTCode(getCustomerRequestError(customerResult)));
      return result;
    }
    // return userID
    // check subscrID
    CreditCard card = customerResult.getTarget().getCreditCards().get(0);
    String paymentMethodToken = card.getToken();
    SubscriptionRequest subscriptionRequest = new SubscriptionRequest().paymentMethodToken(paymentMethodToken);
    subscriptionRequest.planId(subsCustomer.getPlan().getId());

    List<AddonDTO> addonDTOs = subsCustomer.getAddons();
    ModificationsRequest addonUpdate = subscriptionRequest.addOns();
    for (AddonDTO addon:addonDTOs){
      addonUpdate.add().inheritedFromId(addon.getId()).done();
    }
    if (null != subsCustomer.getDiscount()){

      String couponCode = subsCustomer.getDiscount().getId();
      ModificationsRequest discountUpdate = subscriptionRequest.discounts();

      if (StringUtils.isNotEmpty(couponCode)) {
        Plan plan = getPlan(subsCustomer.getPlan().getId());
        if (null != plan){
          AddOn addOn = null;
          BigDecimal totalAddonsPrice = new BigDecimal(0);
          for (AddonDTO addon:addonDTOs){
            addOn = getAddon(addon.getId());
            if (null != addOn){
              totalAddonsPrice = totalAddonsPrice.add(addOn.getAmount());
            }
          }
          BigDecimal planPrice =  getPlanPrice(plan);
          planPrice = planPrice.add(totalAddonsPrice);
          discountUpdate = applyCoupon(plan.getBillingFrequency(),subsCustomer.getUserNumber(), planPrice, couponCode, discountUpdate);
        }
      }
    }

    subscriptionResult = gateway.subscription().create(subscriptionRequest);
    result = this.generateResponseFromBraintree(subscriptionResult);
    return result;
  }


  private Map<String,String> generateResponseFromBraintree(Result<Subscription> subsResult){

    Map<String,String> results = new HashMap<String,String>();
    if (subsResult.isSuccess()){
      Subscription.Status targetStatus = subsResult.getTarget().getStatus();
      // transaction successful
      if(targetStatus.equals(subsResult.getTarget().getStatus().ACTIVE)){
        results.put("msg", "ok");
        List<Transaction> transactions= subsResult.getTarget().getTransactions();
        if (transactions.size() > 0)
        results.put("transactionId",subsResult.getTarget().getTransactions().get(transactions.size()-1).getId());
      }else{
        // wait for confirmation email
        results.put("msg", "pending");
      }
    }else{
      //error for transaction
      if (subsResult.getTransaction() != null) {
        results.put("msg", CommonUtils.getMessageByBTCode(subsResult.getTransaction().getProcessorResponseCode()));
      }
      // subs error
      ValidationErrors errorLevel = subsResult.getErrors();
      if (errorLevel != null) {
        List<ValidationError> errors = errorLevel.getAllDeepValidationErrors();
        for (ValidationError error : errors) {
          results.put("msg", CommonUtils.getMessageByBTCode(error.getCode().code));
        }
      }
    }
    return results;
  }


  private Result<Customer> createOrUpdateCustomer(String customerId, SubscriptionCustomer subsCustomer) {
    CustomerRequest customerRequest = new CustomerRequest()
            .firstName(subsCustomer.getFirstName())
            .lastName(subsCustomer.getLastName()).phone(subsCustomer.getPhone())
            .company(subsCustomer.getOrganization()).email(subsCustomer.getEmail()).customField("product_code",subsCustomer.getProductCode())
            .creditCard().billingAddress().done()
            .number(subsCustomer.getCardNumber())
            .cardholderName(subsCustomer.getCardHolder())
            .expirationMonth(subsCustomer.getExpireMonth())
            .expirationYear(subsCustomer.getExpireYear())
            .cvv(subsCustomer.getCardCVV()).done();

    Result<Customer> result = null;
    if (StringUtils.isNotEmpty(customerId)) {
      result = gateway.customer().update(customerId, customerRequest);
    } else {
      result = gateway.customer().create(customerRequest);
    }
    return result;
  }

  private String getCustomerRequestError(Result<Customer> requestError) {
    String result = null;
    List<ValidationError> errors = requestError.getErrors().getAllDeepValidationErrors();
    if (errors.size() == 0){
      result = requestError.getMessage();
      if (result.contains("Rejected: cvv")){
        return "2010";
      }
    }
    for (ValidationError error : errors) {
      if (error.getCode().toString().contains("CREDIT_CARD_NUMBER")) {
        result = "2005";
      } else if (error.getCode().toString().contains("CREDIT_CARD_EXPIRATION_DATE")) {
        result = "2006";
      } else if (error.getCode().toString().contains("CREDIT_CARD_CVV")) {
        result = "2010";
      }
    }
    return result;
  }

  @Override
  public AddOn getAddon(String id) {
    if (StringUtils.isEmpty(id)) return null;
    try {
      Collection<AddOn> addons = getActiveAddons();
      for (AddOn addon : addons) {
        if (addon.getId().equals(id)) {
          return addon;
        }
      }
    } catch (Exception e) {
      log.error("Failed to connect to payment gateway", e.getMessage());
    }
    return null;
  }

  @Override
  public boolean cancelAddon(String subscriptionId, String addonId, String tenantName) {
    return false;
  }

  @Override
  public Discount getDiscount(String discountID) {
    try {
      Collection<Discount> discounts = gateway.discount().all();
      for (Discount discount : discounts) {
        String id = discount.getId();
        if (!id.startsWith(PREFIX_DISABLED) && !id.startsWith(PREFIX_OLD)) {
          if (id.equals(discountID)) {
            return discount;
          }
        }
      }
    } catch (UnexpectedException e) {
      log.error("Failed to connect to payment gateway", e.getMessage());
    }
    log.error("Cannot find discount with id or name: ", discountID);
    return null;
  }

  public BraintreeGateway getGateway() {
    return gateway;
  }

  public void setGateway(BraintreeGateway gateway) {
    this.gateway = gateway;
  }

  private ModificationsRequest applyCoupon(int planCycle, int userNumber, BigDecimal finalPrice, String discountId, ModificationsRequest discountUpdate) {
    Discount discount = getDiscount(discountId);
    if (null != discount){
      String name = discount.getName();
      String[] info = name.split(PREFIX_DISCOUNT_TYPE);
      if (info.length >= 2 && info[1].indexOf("PERCENT") != -1) {
        double percentage = (double) Integer.parseInt(info[2]) / 100;
        BigDecimal discountPrice = finalPrice.multiply(new BigDecimal(Double.toString(percentage)));
        discountUpdate.add().inheritedFromId(discount.getId()).amount(discountPrice).quantity(1).numberOfBillingCycles(1).done();
      } else if (info.length >= 2 && info[1].indexOf("MONTH") != -1) {
        discountUpdate.add().inheritedFromId(discount.getId()).quantity(userNumber).numberOfBillingCycles(1).done();
      } else if (info.length >= 3 && info[1].indexOf("USER") != -1) {
        discountUpdate.add().inheritedFromId(discount.getId()).amount(discount.getAmount().multiply(new BigDecimal(planCycle))).quantity(Integer.parseInt(info[2])).numberOfBillingCycles(1).done();

      }
    }
    return discountUpdate;
  }

  @Override
  public BigDecimal getDiscountAmount(DiscountDTO discountDTO, BigDecimal planPrice,int planCycle, int userNumber){
    BigDecimal discountPrice = new BigDecimal(0);
    String name = discountDTO.getName();
    if (null != name){
      String[] info = name.split(PREFIX_DISCOUNT_TYPE);
      if (info.length >= 2 && info[1].indexOf("PERCENT") != -1) {
        double percentage = (double) Integer.parseInt(info[2]) / 100;
        discountPrice = planPrice.multiply(new BigDecimal(Double.toString(percentage)));
      } else if (info.length >= 2 && info[1].indexOf("MONTH") != -1) {
        discountPrice = discountDTO.getAmount().multiply(new BigDecimal(userNumber));
      } else if (info.length >= 3 && info[1].indexOf("USER") != -1) {
        discountPrice = discountDTO.getAmount().multiply(new BigDecimal(planCycle)).multiply(new BigDecimal(info[2]));
      }
    }
    return discountPrice;
  }

  @Override
  public Transaction getTransaction(String transactionId) {
    try {
      return gateway.transaction().find(transactionId);
    }catch (Exception e){
      log.error("Failed to connect to payment gateway", e.getMessage());
    }
    return null;
    }

  // get price of plan plus all addons linked to plan and subtract all discount linked to plan
  @Override
  public BigDecimal getPlanPrice(Plan plan) {

    BigDecimal finalPrice = plan.getPrice();

    for (AddOn addon : plan.getAddOns()) {
      BigDecimal addonPrice = addon.getAmount();
      finalPrice = finalPrice.add(addonPrice);
    }

    BigDecimal finalPriceOrgi = finalPrice;
    List<Discount> discounts = plan.getDiscounts();
    for (Discount discount : discounts) {
      BigDecimal discountPrice = new BigDecimal(0);
      String[] discountInfo = discount.getId().split(PREFIX_DISCOUNT_TYPE);
      if (discountInfo[0].indexOf("PERCENT") != -1) {
        double percentage = (double) Integer.parseInt(discountInfo[1]) / 100;
        discountPrice = finalPriceOrgi.multiply(new BigDecimal(Double.toString(percentage)));
      } else if (discountInfo[0].indexOf("MONTH") != -1) {
        discountPrice = discount.getAmount();
      }
      finalPrice = finalPrice.subtract(discountPrice);
    }

    return finalPrice;
  }


}
