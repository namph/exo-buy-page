package com.exoplatform.buypage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class UnLockUtils {
  public static final String KEY_CONTENT="Platform 4 - 30 days evaluation";
  public static final String PRODUCT_NAME = "Platform";
  
  public static String generateProductCode() {
    String productCode = PRODUCT_NAME + Math.random() + KEY_CONTENT;
    return getModifiedMD5Code(productCode.getBytes());
  }
  
  public static String generateKey(String productCode, String nbUser) {
    int nb = Integer.parseInt(nbUser);
    String nbString = new String(Base64.encodeBase64(String.valueOf(nb * 3).getBytes()));
    StringBuffer key = new StringBuffer(getModifiedMD5Code(productCode.getBytes()));
    String lengthString = key.length() < 10 ? "0" + String.valueOf(key.length()) : String
            .valueOf(key.length());
    key.insert(4, lengthString);
    key.append(nbString).append(',');
    key.append("-1").append(',');
    DateFormat d = new SimpleDateFormat("dd/MM/yyyy");
    String date = d.format(new Date());
    key.append(date);
    String keyLength = key.length() < 10 ? "0" + String.valueOf(key.length()+2) : String.valueOf(key.length()+2);
    key.insert(8, keyLength);
    String keyString = new String(Base64.encodeBase64(key.toString().getBytes()));
    if(keyString.endsWith("="))
        keyString = keyString.substring(0,keyString.length()-1);
    return keyString;
  }
  
  public static String getModifiedMD5Code(byte[] dataToHash) {
    Security.addProvider(new BouncyCastleProvider());
    Provider provBC = Security.getProvider("BC");
    MessageDigest digest = null;
    try {
        digest = MessageDigest.getInstance("MD5", provBC);
    } catch (NoSuchAlgorithmException exception) {
        throw new RuntimeException(exception);
    }
    digest.update(dataToHash);
    byte[] hashMD5 = digest.digest(dataToHash);
    StringBuffer hashMD5String = new StringBuffer();
    for (int i = 0; i < hashMD5.length; i++) {
        hashMD5[i] %= 26;
        hashMD5[i] = (byte) Math.abs(hashMD5[i]);
        hashMD5[i] += ((byte) 'A' - 1);
        hashMD5String.append(((char) hashMD5[i]));
    }
    return hashMD5String.toString();
  }
  
}
