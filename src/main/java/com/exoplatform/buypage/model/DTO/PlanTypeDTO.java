package com.exoplatform.buypage.model.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanTypeDTO extends DTO {

  private List<PlanDTO> planDTOs;
  private String active;
  private int defaultNbUser;
  private int maxNbUser;
  private int planCycle;
  public PlanTypeDTO(String id, String name, String description) {
    super(id, name, description);
    this.setPlanDTOs(new ArrayList<PlanDTO>());
    this.setActive("");
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
}
