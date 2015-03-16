package com.exoplatform.buypage.controllers;

import com.braintreegateway.AddOn;
import com.braintreegateway.Discount;
import com.braintreegateway.Plan;
import com.braintreegateway.Transaction;
import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.Addon;
import com.exoplatform.buypage.model.DTO.AddonDTO;
import com.exoplatform.buypage.model.DTO.DiscountDTO;
import com.exoplatform.buypage.model.DTO.PlanDTO;
import com.exoplatform.buypage.model.DTO.TransactionDTO;
import com.exoplatform.buypage.utils.CommonUtils;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public Transaction info(HttpSession httpSession,@PathVariable(value = "id") String transactionId, Model model){

    if (null != transactionId && !"".equals(transactionId)){
      Transaction transaction = gatewayService.getTransaction(transactionId);
      if (null != transaction){
        model.addAttribute("test","spring session");
        return transaction;
      }
    }
    return null;
  }

  @RequestMapping(value = "/success",produces = "text/html")
  public ModelAndView success(HttpSession httpSession){
    PlanDTO planDTO = null;
    String customer_email = null;
    String customer_org = null;
    DiscountDTO discountDTO = null;
    String total = null;
    ModelAndView mav = new ModelAndView();
    mav.setViewName("confirmation");
    if (null != httpSession.getAttribute("transaction")){
      TransactionDTO transactionDTO = (TransactionDTO) httpSession.getAttribute("transaction");
      if (null != transactionDTO) {
        String transactionId = transactionDTO.getId();
        Transaction transaction = gatewayService.getTransaction(transactionId);
        if (null != transaction) {
          // show order detail from client side
          if (transaction.getAmount().compareTo(transactionDTO.getTotalBill()) == 0){
            planDTO = transactionDTO.getPlanDTO();
            customer_email = transactionDTO.getCustomer_email();
            customer_org = transactionDTO.getCustomer_organization();
            discountDTO = transactionDTO.getDiscountDTO();
            total = CommonUtils.convertAmount2String(transactionDTO.getTotalBill());
            List<AddonDTO> addons = transactionDTO.getAddonDTOs();
            List<AddonDTO> addonDTOs = new ArrayList<AddonDTO>();
            List<AddonDTO> serviceDTOs = new ArrayList<AddonDTO>();
            AddonDTO addonDTO = null;
            for (AddonDTO addOn : addons) {
              addonDTO = new AddonDTO(addOn.getId(), addOn.getName(), addOn.getDescription(),addOn.getPrice());
              if (addonDTO.isService())
                serviceDTOs.add(addonDTO);
              else
                addonDTOs.add(addonDTO);
            }
            planDTO.setAddons(addonDTOs);
            planDTO.setServices(serviceDTOs);

          }else{
            // retrieve order detail from BT
            Plan plan = gatewayService.getPlan(transaction.getPlanId());
            List<AddOn> addons = transaction.getAddOns();
            List<Discount> discounts = transaction.getDiscounts();
            customer_email = transaction.getCustomer().getEmail();
            customer_org = transaction.getCustomer().getCompany();
            total = CommonUtils.convertAmount2String(transaction.getAmount());

            planDTO = new PlanDTO(plan.getId(), plan.getName(), plan.getDescription());
            planDTO.setPrice(gatewayService.getPlanPrice(plan));
            planDTO.setPlanCycle(plan.getBillingFrequency());

            List<AddonDTO> addonDTOs = planDTO.getAddons();
            List<AddonDTO> serviceDTOs = planDTO.getServices();
            AddonDTO addonDTO = null;
            for (AddOn addOn : addons) {
              AddOn btAddon = gatewayService.getAddon(addOn.getId());
              if (null != btAddon){
                addonDTO = new AddonDTO(btAddon.getId(), btAddon.getName(), btAddon.getDescription(), btAddon.getAmount());
                if (addonDTO.isService())
                  serviceDTOs.add(addonDTO);
                else
                  addonDTOs.add(addonDTO);
              }
            }
            planDTO.setAddons(addonDTOs);
            planDTO.setServices(serviceDTOs);
            // we know already only one discount submitted
            for (Discount discount : discounts) {
              Discount btDiscount  = gatewayService.getDiscount(discount.getId());
              if (null != btDiscount){
                discountDTO = new DiscountDTO(btDiscount.getId(),btDiscount.getName(),btDiscount.getDescription());
                discountDTO.setAmount(btDiscount.getAmount());
                discountDTO.setAmount(gatewayService.getDiscountAmount(discountDTO, planDTO.getPrice(), planDTO.getPlanCycle(), planDTO.getOptionUser()));
              }
            }
          }
          mav.addObject("transactionId", transactionId);
          mav.addObject("plan", planDTO);
          mav.addObject("customer_email", customer_email);
          mav.addObject("customer_org", customer_org);
          mav.addObject("discount", discountDTO);
          mav.addObject("total", total);
        }
      }

    }
   // httpSession.setAttribute("transaction",null);
    return mav;
  }

}
