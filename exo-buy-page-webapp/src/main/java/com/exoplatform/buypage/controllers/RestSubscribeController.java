package com.exoplatform.buypage.controllers;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.AddonDTO;
import com.exoplatform.buypage.model.DTO.ItemOrderWrapper;
import com.exoplatform.buypage.model.DTO.PlanDTO;
import com.exoplatform.buypage.model.SubscriptionCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anhvt on 03/03/15.
 */
@Controller
@RequestMapping(value = "/Subscribe")
public class RestSubscribeController {
  Logger log = LoggerFactory.getLogger(RestSubscribeController.class);
  @Inject
  IService gatewayService;

  @RequestMapping(value = "/submit",method = RequestMethod.POST,consumes = {"application/json"},produces = {"application/json"})
  @ResponseBody
  public Map<String,String> showBillFromClient(HttpSession httpSession, @RequestBody SubscriptionCustomer subscriptionCustomer){
    Map<String,String> result =  gatewayService.subscribe("","",subscriptionCustomer);
    if (null != result && "ok".equals(result.get("msg"))){
      httpSession.setAttribute("transactionId",result.get("transactionId"));
    }
    return result;
  }
}
