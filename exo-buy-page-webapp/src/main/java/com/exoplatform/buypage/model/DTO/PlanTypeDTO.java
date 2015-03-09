package com.exoplatform.buypage.model.DTO;


import java.util.*;

public class PlanTypeDTO extends DTO {

  private List<PlanDTO> planDTOs;
  private String active;
  private int defaultNbUser;
  private int maxNbUser;
  private int planCycle;
  private List<Integer> users;
  public PlanTypeDTO(String id, String name, String description) {
    super(id, name, description);
    this.setPlanDTOs(new ArrayList<PlanDTO>());
    this.setActive("");
    this.setUsers(new ArrayList<Integer>());
  }
  public float numberUserPercent(){
    int total = this.getMaxNbUser();
    if (total > 0){
      return ((float)this.getDefaultNbUser()/(float)total)*100;
    }
    return 0;
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

  public int getMaxNbUser() {
    return maxNbUser;
  }

  public void setMaxNbUser(int maxNbUser) {
    this.maxNbUser = maxNbUser;
  }

  public int getPlanCycle() {
    return planCycle;
  }

  public void setPlanCycle(int planCycle) {
    this.planCycle = planCycle;
  }

  public List<Integer> getUsers() {
    Collections.sort(users);
    return users;
  }

  public void setUsers(List<Integer> users) {
    this.users = users;
  }
  public String generateUserSliderTick(){
    List<Integer> users = this.getUsers();
    String tick = "[";
    for (int i = 1;i <= users.size();i++){
      tick +=i;
      if (i < users.size()){
        tick +=",";
      }

    }
    tick +="]";
    return tick;
  }
  public String generateUserSliderLabel(){
    List<Integer> users = this.getUsers();
    String label = "[";
    int i = 0;
    for (Integer nb:users){
      label +=nb.toString()+" users";
      i++;
      if (i != users.size()){
        label +=",";
      }
    }
    label +="]";
    return label;
  }
}
