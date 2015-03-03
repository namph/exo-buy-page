package com.exoplatform.buypage.model;


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
  private String planId;
  private String userNumber;
  private List<String> addonIds;
  private String discountId;

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

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public String getUserNumber() {
    return userNumber;
  }

  public void setUserNumber(String userNumber) {
    this.userNumber = userNumber;
  }

  public List<String> getAddonIds() {
    return addonIds;
  }

  public void setAddonIds(List<String> addonIds) {
    this.addonIds = addonIds;
  }

  public String getDiscountId() {
    return discountId;
  }

  public void setDiscountId(String discountId) {
    this.discountId = discountId;
  }
}
