package com.exoplatform.buypage.model.DTO;

import java.math.BigDecimal;

public class DTO {

  private String id;
  private String name;
  private String description;
  private BigDecimal price;
  public DTO(){

  }
  public DTO(String id, String name, String description){
    this.setId(id);
    this.setName(name);
    this.setDescription(description);
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  protected String getCombinationValue(String type){
    String[] combinations =  this.getId().split("-");
    if (combinations.length > 0){
      if ("type".equals(type))
        return combinations[0];
      else if ("user".equals(type) && combinations.length > 1)
        return combinations[1];
    }
    return "";
  }
  public Integer getOptionUser(){
    String options = this.getCombinationValue("user");
    if (!"".equals(options) && options.contains("USER") && options.split("_").length > 0){
      return Integer.parseInt(options.split("_")[1]);
    }
    return 5;
  }
}
