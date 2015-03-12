package com.exoplatform.buypage.controllers;

import com.braintreegateway.AddOn;
import com.braintreegateway.Discount;
import com.braintreegateway.Plan;
import com.braintreegateway.Transaction;
import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.AddonDTO;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import com.exoplatform.buypage.model.DTO.PlanDTO;
import com.exoplatform.buypage.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/confirmation")
@SessionAttributes("test")
public class ConfirmationController {

  @Inject
  IService gatewayService;
  @RequestMapping(value = "/s/{id}",method = RequestMethod.GET,produces = "application/json")
  @ResponseBody
  public Transaction info(HttpSession httpSession,@PathVariable(value = "id") String transactionId){

    if (null != transactionId && !"".equals(transactionId)){
      Transaction transaction = gatewayService.getTransaction(transactionId);
      if (null != transaction){
        Plan plan = gatewayService.getPlan(transaction.getPlanId());
      }
    }
    return null;
  }
  @RequestMapping(value = "/success")
  public ModelAndView success(HttpSession httpSession){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("confirmation");
    String transactionId = (String)httpSession.getAttribute("transactionId");
    if (null != transactionId && !"".equals(transactionId)){
      Transaction transaction = gatewayService.getTransaction(transactionId);
      if (null != transaction){
        Plan plan = gatewayService.getPlan(transaction.getPlanId());
        List<AddOn> addons = transaction.getAddOns();
        List<Discount> discounts = transaction.getDiscounts();
        String customer_email = transaction.getCustomer().getEmail();
        String customer_org = transaction.getCustomer().getCompany();
        String total = CommonUtils.convertAmount2String(transaction.getAmount());

        PlanDTO planDTO = new PlanDTO(plan.getId(),plan.getName(),plan.getDescription());
        planDTO.setPrice(gatewayService.getPlanPrice(plan));
        planDTO.setPlanCycle(plan.getBillingFrequency());

        List<AddonDTO> addonDTOs = planDTO.getAddons();
        List<AddonDTO> serviceDTOs = planDTO.getServices();
        AddonDTO addonDTO = null;
        for (AddOn addOn:addons){
          addonDTO = new AddonDTO(addOn.getId(),addOn.getName(),addOn.getDescription(),addOn.getAmount());
          if (addonDTO.isService())
            serviceDTOs.add(addonDTO);
          else
            addonDTOs.add(addonDTO);
        }
        planDTO.setAddons(addonDTOs);
        planDTO.setServices(serviceDTOs);
        DiscountDTO discountDTO = null;
        // we know already only one discount submitted
        for (Discount discount:discounts){
         discountDTO = new DiscountDTO(discount.getId(),discount.getName(),discount.getDescription());
         discountDTO.setAmount(gatewayService.getDiscountAmount(discountDTO,planDTO.getPrice(),planDTO.getPlanCycle(),planDTO.getOptionUser()));
        }
        mav.addObject("transactionId",transactionId);
        mav.addObject("plan",planDTO);
        mav.addObject("customer_email",customer_email);
        mav.addObject("customer_org",customer_org);
        mav.addObject("discount",discountDTO);
        mav.addObject("total",total);
      }
    }
    return mav;
  }

}
