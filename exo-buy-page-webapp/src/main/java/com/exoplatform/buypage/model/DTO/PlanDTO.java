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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanDTO extends DTO {

  private List<AddonDTO> services;
  private List<AddonDTO> addons;
  private int year;

  private int planCycle;
  public PlanDTO(){

  }
  public PlanDTO(String id){
    this.setId(id);
    this.setAddons(new ArrayList<AddonDTO>());
    this.setServices(new ArrayList<AddonDTO>());
  }
  public PlanDTO(String id, String name, String description){
    super(id, name, description);
    this.setAddons(new ArrayList<AddonDTO>());
    this.setServices(new ArrayList<AddonDTO>());
  }
  public String getActive(){
    if(this.getId().contains("DEFAULT"))
      return "active";
    return "";
  }
  public String getPlanType(){
    return this.getCombinationValue("type");
  }


  public int getPlanCycle() {
    return planCycle;
  }

  public void setPlanCycle(int planCycle) {
    this.planCycle = planCycle;
  }

  public String getPeriod() {
    String period = "";
    Calendar now = Calendar.getInstance();
    Calendar ending = Calendar.getInstance();
    ending.add(Calendar.YEAR,this.getPlanCycle()/12);

    period = (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DATE) + "/" + now.get(Calendar.YEAR) + " - "+(ending.get(Calendar.MONTH) + 1) + "/" + ending.get(Calendar.DATE) + "/" + ending.get(Calendar.YEAR);
    return period;
  }
  public List<AddonDTO> getServices() {
    return services;
  }

  public void setServices(List<AddonDTO> services) {
    this.services = services;
  }

  public List<AddonDTO> getAddons() {
    return addons;
  }

  public void setAddons(List<AddonDTO> addons) {
    this.addons = addons;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }
  public int getYearNumber(){
    String type = this.getPlanType();
    String[] typeArr = type.split("_");
    if (typeArr.length > 1)
      return  Integer.parseInt((typeArr[typeArr.length-1]).replaceAll("\\D+",""));
    return 1;
  }
}
