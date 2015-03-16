package com.exoplatform.buypage.controllers;

import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import com.exoplatform.buypage.model.DTO.ItemOrderWrapper;
import com.exoplatform.buypage.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/Bill")
public class RestBillController {

  private Logger log = LoggerFactory.getLogger(RestBillController.class);
  @Inject
  IService gatewayService;

  @RequestMapping(value = "/showFromClient",method = RequestMethod.POST,consumes = {"application/json"},produces = "text/html")
  public ModelAndView showBillFromClient(@RequestBody ItemOrderWrapper itemOrderWrapper){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("bill");
    if (itemOrderWrapper.getPlan() != null){
      mav.addObject("plan",itemOrderWrapper.getPlan());
      mav.addObject("addons",itemOrderWrapper.getAddons());
      mav.addObject("services",itemOrderWrapper.getServices());
      BigDecimal orderAmount = itemOrderWrapper.getTotal();
      DiscountDTO discountDTO = itemOrderWrapper.getDiscount();
      if (null != discountDTO){
        BigDecimal discountAmount = gatewayService.getDiscountAmount(discountDTO,orderAmount,itemOrderWrapper.getPlan().getPlanCycle(),itemOrderWrapper.getPlan().getOptionUser());
        discountDTO.setAmount(discountAmount);
        mav.addObject("discount",discountDTO);
        orderAmount = orderAmount.subtract(discountAmount);
      }
      mav.addObject("totalBill",orderAmount.floatValue());
      mav.addObject("total", CommonUtils.convertAmount2String(orderAmount));
    }else{
      mav.addObject("error","service unavailable");
    }
    return mav;
  }

}
