package com.exoplatform.buypage.controllers;

import com.braintreegateway.Plan;
import com.exoplatform.buypage.gateway.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Plan service handler.
 *
 */
@Controller
@RequestMapping(value = "Plan", produces = "application/json")
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
  @RequestMapping(value = "plan", method = RequestMethod.GET)
  @ResponseBody
  public Collection<Plan> getActivePlans() throws Exception {
    Collection<Plan> plans = gatewayService.getActivePlans();
    return plans;
  }

}
