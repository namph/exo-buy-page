package com.exoplatform.buypage.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
  public static String getMessageByBTCode(String strCode) {
    int code = 0;
    try {
      if(null != strCode && !"".equals(strCode))
        code = Integer.valueOf(strCode);
    }
    catch (Exception e){}
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
        return "We are unable to process your credit card payment, please verify the information you entered is correct. If the problem persists, <a href='http://www.exoplatform.com/company/en/company/contact-us' target='_blank'>contact us</a> to complete your order.";
    }
  }

  public static String convertAmount2String(BigDecimal amount){
    return DECIMAL_FORMAT.format(amount);
  }
  
  public static  Date parseDate(String date,String formatStr){
    try {      
      SimpleDateFormat format = new SimpleDateFormat(formatStr);      
      Date ret = format.parse(date);        
      return ret;
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static  String partString(Date date, String format){
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    String s = formatter.format(date);
    return s;
  }
  
  public static  String partString(Calendar cal, String format){
    //for example format = "dd/MM/yyyy hh:mm:ss a zz" will return "17/03/2015 04:21:31 AM UTC"
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setCalendar(cal);
    String s = formatter.format(cal.getTime());
    return s;
  }
}
