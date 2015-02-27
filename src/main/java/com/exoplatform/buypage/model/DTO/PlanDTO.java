/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.buypage.model.DTO;

import java.util.List;

public class PlanDTO extends DTO {

  private List<OptionDTO> optionDTOs;

  public PlanDTO(){

  }
  public PlanDTO(String id, String name, String description){
    super(id,name,description);
  }
  private void generateOptionDTOs(){

  }
  public List<OptionDTO> getOptionDTOs() {
    return optionDTOs;
  }

  public void setOptionDTOs(List<OptionDTO> optionDTOs) {
    this.optionDTOs = optionDTOs;
  }
  public String getActive(){
    if(this.getId().contains("DEFAULT"))
      return "active";
    return "";
  }
  private String getCombinationValue(String type){
    String[] combinations =  this.getId().split("-");
    if (combinations.length > 0){
      if ("type".equals(type))
        return combinations[0];
      else if ("user".equals(type) && combinations.length > 1)
        return combinations[1];
    }
    return "";
  }
  public String getPlanType(){
    return this.getCombinationValue("type");
  }
  public Integer getOptionUser(){
    String options = this.getCombinationValue("user");
    if (!"".equals(options) && options.contains("USER") && options.split("_").length > 0){
      return Integer.parseInt(options.split("_")[1]);
    }
    return 5;
  }

}
