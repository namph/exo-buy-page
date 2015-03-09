package com.exoplatform.buypage.model.DTO;


import com.exoplatform.buypage.utils.CommonUtils;

import java.math.BigDecimal;

public class DiscountDTO extends DTO{
  private BigDecimal amount;
  public DiscountDTO(){}
  public DiscountDTO(String id, String name, String description){
    super(id,name,description);
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getStrAmount(){
    if (null != this.getAmount()){
      return CommonUtils.convertAmount2String(this.getAmount());
    }
    return "";
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
