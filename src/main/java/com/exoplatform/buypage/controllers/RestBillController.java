package com.exoplatform.buypage.controllers;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.ItemOrderDTO;
import com.exoplatform.buypage.model.DTO.ItemOrderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/Bill")
public class RestBillController {

  private Logger log = LoggerFactory.getLogger(RestBillController.class);
  @Inject
  IService gatewayService;

  @RequestMapping(value = "/showFromClient",method = RequestMethod.POST,consumes = {"application/json"})
  public ModelAndView showBillFromClient(@RequestBody ItemOrderWrapper itemOrderWrapper){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("bill");
    mav.addObject("plan",itemOrderWrapper.getPlan());
    mav.addObject("addons",itemOrderWrapper.getAddons());
    mav.addObject("services",itemOrderWrapper.getServices());
    mav.addObject("discountId",itemOrderWrapper.getDiscountId());
    mav.addObject("total",itemOrderWrapper.getTotal());
    return mav;
  }

}
