package com.exoplatform.buypage.model;


import com.exoplatform.buypage.model.DTO.AddonDTO;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import com.exoplatform.buypage.model.DTO.PlanDTO;

import java.math.BigDecimal;
import java.util.List;

public class SubscriptionCustomer {
  private String firstName;
  private String lastName;
  private String organization;
  private String phone;
  private String email;
  private String cardNumber;
  private String cardHolder;
  private String expireMonth;
  private String expireYear;
  private String cardCVV;
  private PlanDTO plan;
  private int userNumber;
  private List<AddonDTO> addons;
  private DiscountDTO discount;
  private int totalBill;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardHolder() {
    return cardHolder;
  }

  public void setCardHolder(String cardHolder) {
    this.cardHolder = cardHolder;
  }

  public String getExpireMonth() {
    return expireMonth;
  }

  public void setExpireMonth(String expireMonth) {
    this.expireMonth = expireMonth;
  }

  public String getExpireYear() {
    return expireYear;
  }

  public void setExpireYear(String expireYear) {
    this.expireYear = expireYear;
  }

  public String getCardCVV() {
    return cardCVV;
  }

  public void setCardCVV(String cardCVV) {
    this.cardCVV = cardCVV;
  }

  public int getUserNumber() {
    return userNumber;
  }

  public void setUserNumber(int userNumber) {
    this.userNumber = userNumber;
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

  public DiscountDTO getDiscount() {
    return discount;
  }

  public void setDiscount(DiscountDTO discount) {
    this.discount = discount;
  }

  public int getTotalBill() {
    return totalBill;
  }

  public void setTotalBill(int totalBill) {
    this.totalBill = totalBill;
  }
}
