package com.exoplatform.buypage.controllers;

import com.braintreegateway.Discount;
import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping(value = "/Discount")
public class RESTDiscountController {
  private Logger log = LoggerFactory.getLogger(RESTDiscountController.class);
  @Inject
  IService gatewayService;

  @RequestMapping(value = "/get/{discountId}",method = RequestMethod.GET,produces = "application/json")
  @ResponseBody
  public DiscountDTO getDiscount(@PathVariable(value = "discountId") String discountId){
    Discount discount = gatewayService.getDiscount(discountId);
    DiscountDTO discountDTO = null;
    if (discount != null){
      discountDTO = new DiscountDTO(discount.getId(),discount.getName(),discount.getDescription());
    }
    return discountDTO;
  }

}
