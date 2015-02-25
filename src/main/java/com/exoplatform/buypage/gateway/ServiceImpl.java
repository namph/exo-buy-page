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
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ServiceImpl implements IService {

  private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);
  public static final String ADMIN_CONFIG_FILE = "cloud.admin.configuration.file";

  public static final String ENVIRONMENT_PROPERTY = "provider.braintree.environment";

  public static final String MERCHANT_ID_PROPERTY = "provider.braintree.merchantId";

  public static final String PUBLIC_KEY_PROPERTY = "provider.braintree.publicKey";

  public static final String PRIVATE_KEY_PROPERTY = "provider.braintree.privateKey";

  public static final String ENCRYPT_KEY_PROPERTY = "provider.braintree.encryptionKey";

  private PropertiesConfiguration adminConfiguration;
  private BraintreeGateway gateway;
  public ServiceImpl(){
    try {
      this.adminConfiguration = new PropertiesConfiguration("braintree.properties");
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
      if (!plan.getId().startsWith("DISABLED") && !plan.getId().startsWith("OLD") && plan.getId().startsWith("EXO_PLT_ENT")) {
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
    return null;
  }

  @Override
  public Collection<AddOn> getActiveAddons() {
    return null;
  }

  @Override
  public AddOn getAddon(String id) {
    return null;
  }

  @Override
  public boolean cancelAddon(String subscriptionId, String addonId, String tenantName) {
    return false;
  }

  @Override
  public Discount getDiscount(String discountID) {
    return null;
  }

  public BraintreeGateway getGateway() {
    return gateway;
  }

  public void setGateway(BraintreeGateway gateway) {
    this.gateway = gateway;
  }
}
