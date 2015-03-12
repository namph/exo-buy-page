package com.exoplatform.buypage.model.DTO;

import java.util.List;

public class TransactionDTO extends DTO {

  private PlanDTO planDTO;
  private List<AddonDTO> addonDTOs;
  private List<DiscountDTO> discountDTOs;
  private int total;
  private String customer_email;
  private String customer_organization;
  private String period;

  public PlanDTO getPlanDTO() {
    return planDTO;
  }

  public void setPlanDTO(PlanDTO planDTO) {
    this.planDTO = planDTO;
  }

  public List<AddonDTO> getAddonDTOs() {
    return addonDTOs;
  }

  public void setAddonDTOs(List<AddonDTO> addonDTOs) {
    this.addonDTOs = addonDTOs;
  }

  public List<DiscountDTO> getDiscountDTOs() {
    return discountDTOs;
  }

  public void setDiscountDTOs(List<DiscountDTO> discountDTOs) {
    this.discountDTOs = discountDTOs;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getCustomer_email() {
    return customer_email;
  }

  public void setCustomer_email(String customer_email) {
    this.customer_email = customer_email;
  }

  public String getCustomer_organization() {
    return customer_organization;
  }

  public void setCustomer_organization(String customer_organization) {
    this.customer_organization = customer_organization;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }
}
