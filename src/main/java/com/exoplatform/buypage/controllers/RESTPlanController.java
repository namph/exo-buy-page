package com.exoplatform.buypage.controllers;

import com.braintreegateway.Plan;
import com.exoplatform.buypage.gateway.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Collection;

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
/*  @RequestMapping(value = "getActives", method = RequestMethod.GET)
  @ResponseBody
  public Collection<Plan> getActivePlans() throws Exception {
    Collection<Plan> plans = gatewayService.getActivePlans();
    return plans;
  }*/

/*
  @RequestMapping(value = "/getActives", method = RequestMethod.GET)
  public ModelAndView getActives(){
    Collection<Plan> plans = gatewayService.getActivePlans();
    ModelAndView mav = new ModelAndView();
    mav.setViewName("plan");
    mav.addObject("plans",plans);
    mav.getModelMap().addAttribute("msg","hello ta");
    return mav;
  }
*/

  @RequestMapping(value = "/test",method = RequestMethod.GET)
  public String test(ModelMap modelMap){
    modelMap.addAttribute("msg","toto");
    return "test";
  }

}
