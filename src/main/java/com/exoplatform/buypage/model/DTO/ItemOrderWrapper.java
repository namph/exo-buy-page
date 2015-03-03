package com.exoplatform.buypage.model.DTO;

import java.math.BigDecimal;
import java.util.List;
public class ItemOrderWrapper {
  private PlanDTO plan;
  private List<AddonDTO> addons;
  private List<AddonDTO> services;
  private String discountId;


  public String getDiscountId() {
    return discountId;
  }

  public void setDiscountId(String discountId) {
    this.discountId = discountId;
  }

  public PlanDTO getPlan() {
    return plan;
  }

  public void setPlan(PlanDTO plan) {
    this.plan = plan;
  }

  public List<AddonDTO> getAddons() {
    return addons;
  }

  public void setAddons(List<AddonDTO> addons) {
    this.addons = addons;
  }

  public List<AddonDTO> getServices() {
    return services;
  }

  public void setServices(List<AddonDTO> services) {
    this.services = services;
  }

  public BigDecimal getTotal(){
    BigDecimal total = plan.getPrice();
    for (AddonDTO addon:this.getAddons()){
      total = total.add(addon.getPrice());
    }
    for (AddonDTO service:this.getServices()){
      total = total.add(service.getPrice());
    }
    return total;
  }
}
