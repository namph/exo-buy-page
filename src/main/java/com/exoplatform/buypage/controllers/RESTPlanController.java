package com.exoplatform.buypage.controllers;

import com.braintreegateway.Plan;
import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.PlanDTO;
import com.exoplatform.buypage.model.DTO.PlanTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.*;

/**
 * Plan controller handler.
 *
 */
@Controller
@RequestMapping(value = "/Plan")
public class RESTPlanController {

  private Logger log = LoggerFactory.getLogger(RESTPlanController.class);
  @Inject
  IService gatewayService;

  /**
   * Return all active plans.
   *
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/s", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public Collection<Plan> getActivePlans() throws Exception {
    Collection<Plan> plans = gatewayService.getActivePlans();
    return plans;
  }

  @RequestMapping(value = "/getActives", method = RequestMethod.GET)
  public ModelAndView getActives(){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("plan");
    Collection<Plan> plans = gatewayService.getActivePlans();
    PlanDTO planDTO = null;
    PlanTypeDTO planTypeDTO = null;
    String prefixPlanType = "";
    Map<String,PlanTypeDTO> planTypeDTOMap = new HashMap<String, PlanTypeDTO>();
    for (Plan plan:plans){
      planDTO = new PlanDTO(plan.getId(),plan.getName(),plan.getDescription());
      planDTO.setPrice(plan.getPrice());
      prefixPlanType = planDTO.getPlanType();
      if (!planTypeDTOMap.containsKey(prefixPlanType)){
        planTypeDTO = new PlanTypeDTO(planDTO.getId(),planDTO.getName(),planDTO.getDescription());
        planTypeDTO.setPrice(planDTO.getPrice());
        planTypeDTO.setDefaultNbUser(planDTO.getOptionUser());
        planTypeDTOMap.put(prefixPlanType,planTypeDTO);
      }
      if (planTypeDTOMap.containsKey(prefixPlanType)  ){
        PlanTypeDTO currentPlanTypeDTO = planTypeDTOMap.get(prefixPlanType);
        if (currentPlanTypeDTO.getMaxNbUser() < planDTO.getOptionUser()){
          currentPlanTypeDTO.setMaxNbUser(planDTO.getOptionUser());
        }
        currentPlanTypeDTO.getPlanDTOs().add(planDTO);
        if (!"".equals(planDTO.getActive())){
          currentPlanTypeDTO.setId(planDTO.getId());
          currentPlanTypeDTO.setName(planDTO.getName());
          currentPlanTypeDTO.setDescription(planDTO.getDescription());
          currentPlanTypeDTO.setDefaultNbUser(planDTO.getOptionUser());
          currentPlanTypeDTO.setActive(planDTO.getActive());
          currentPlanTypeDTO.setPrice(planDTO.getPrice());
        }
        planTypeDTOMap.remove(prefixPlanType);
        planTypeDTOMap.put(prefixPlanType,currentPlanTypeDTO);
      }
    }
    // need to get max number user for each plan type
    mav.addObject("planTypes",planTypeDTOMap);
    return mav;
  }

}
