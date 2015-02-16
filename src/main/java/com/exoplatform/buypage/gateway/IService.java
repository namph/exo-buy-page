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

import com.braintreegateway.AddOn;
import com.braintreegateway.Discount;
import com.braintreegateway.Plan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * General contract between Product service and billing providers.
 */
@Service
public interface IService {

  /**
   * Return key to encrypt
   *
   * @return String
   */
  public String getEncryptKey();

  /**
   * Return all active plans.
   *
   * @return Collection<Plan>
   */
  public Collection<Plan> getActivePlans();

  /**
   * Return plan by id.
   *
   * @param id
   * @return {@link Plan}
   */
  public Plan getPlan(String id);

  /**
   * Return all available addons.
   *
   * @return Collection<Addon>
   */
  public Collection<AddOn> getActiveAddons();

  /**
   * Return addon by id.
   *
   * @param id
   * @return Addon
   */
  public AddOn getAddon(String id);

  /**
   * Cancel addon by id.
   *
   * @param subscriptionId
   * @param addonId
   * @param tenantName
   * @return boolean
   */
  public boolean cancelAddon(String subscriptionId, String addonId, String tenantName);

  /**
   * Get discount via dicountID
   *
   * @param discountID
   * @return Discount
   */
  public Discount getDiscount(String discountID);

}
