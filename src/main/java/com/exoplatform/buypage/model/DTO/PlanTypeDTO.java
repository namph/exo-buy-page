package com.exoplatform.buypage.model.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanTypeDTO extends DTO {

  private List<PlanDTO> planDTOs;
  private List<Integer> users;
  private String active;
  private int defaultNbUser;

  public PlanTypeDTO(String id, String name, String description) {
    super(id, name, description);
    this.setPlanDTOs(new ArrayList<PlanDTO>());
    this.setActive("");
    this.setDefaultNbUser(5);
    this.users = new ArrayList<Integer>();
  }

  public List<Integer> getUsers() {
    if(this.users != null)
      Collections.sort(this.users);
    return users;
  }

  public void addNumberUser(Integer nb) {
    if (!this.getUsers().contains(nb))
      this.getUsers().add(nb);
  }
  public float numberUserPercent(){
    int total = this.getTotalUsers();
    if (total > 0){
      return (this.getDefaultNbUser()/total)*100;
    }
    return 0;
  }
  public int getTotalUsers(){
    int total = 0;
    for(Integer i:this.getUsers()){
      total +=i;
    }
    return total;
  }

  public List<PlanDTO> getPlanDTOs() {
    return planDTOs;
  }

  public void setPlanDTOs(List<PlanDTO> planDTOs) {
    this.planDTOs = planDTOs;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public int getDefaultNbUser() {
    return defaultNbUser;
  }

  public void setDefaultNbUser(int defaultNbUser) {
    this.defaultNbUser = defaultNbUser;
  }
}
