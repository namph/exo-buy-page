package com.exoplatform.buypage.model.DTO;

import com.exoplatform.buypage.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class ItemOrderWrapper {
  private PlanDTO plan;
  private List<AddonDTO> addons;
  private List<AddonDTO> services;
  private DiscountDTO discount;
  private BigDecimal total;

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
    this.total = total;
    return total;
  }
  public List<String> getAddonIds(){
    List<String> addonIds = new ArrayList<String>();
    for (AddonDTO addon:this.getAddons()){
      addonIds.add(addon.getId());
    }
    for (AddonDTO service:this.getServices()){
      addonIds.add(service.getId());
    }
    return addonIds;
  }

  public DiscountDTO getDiscount() {
    return discount;
  }

  public void setDiscount(DiscountDTO discount) {
    this.discount = discount;
  }
}
