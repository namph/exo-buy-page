package com.exoplatform.buypage.utils;

public class CommonUtils {

  public static String getMessageByBTCode(String strCode) {
    int code = Integer.valueOf(strCode);
    switch (code) {
      case 2001:
        return "Insufficient Funds";
      case 2002:
        return "Limit Exceeded";
      case 2003:
        return "Cardholder's Activity Limit Exceeded";
      case 2004:
        return "Expired Card";
      case 2005:
        return "Invalid Credit Card Number";
      case 2006:
        return "Invalid Expiration Date";
      case 2008:
        return "Card Account Length Error";
      case 2010:
        return "Card Issuer Declined CVV";
      case 2015:
        return "Transaction Not Allowed";
      default:
        return "We are unable to process your credit card payment, please verify the information you entered is correct";
    }
  }

}
