package com.exoplatform.buypage.model.DTO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Scope("session")
public class TransactionDTO{

  private String id;
  private PlanDTO planDTO;
  private List<AddonDTO> addonDTOs;
  private DiscountDTO discountDTO;
  private String customer_email;
  private String customer_organization;
  private BigDecimal totalBill;

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
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DiscountDTO getDiscountDTO() {
    return discountDTO;
  }

  public void setDiscountDTO(DiscountDTO discountDTO) {
    this.discountDTO = discountDTO;
  }

  public BigDecimal getTotalBill() {
    return totalBill;
  }

  public void setTotalBill(BigDecimal totalBill) {
    this.totalBill = totalBill;
  }
}
