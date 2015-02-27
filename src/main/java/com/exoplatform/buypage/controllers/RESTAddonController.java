package com.exoplatform.buypage.controllers;


import com.braintreegateway.AddOn;
import com.braintreegateway.Plan;
import com.exoplatform.buypage.gateway.IService;
import com.exoplatform.buypage.model.DTO.AddonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "Addons")
public class RESTAddonController {
  private static final Logger log = LoggerFactory.getLogger(RESTAddonController.class);
  @Inject
  IService gatewayService;

  @RequestMapping(value = "/s", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public Collection<AddOn> getActivePlans() throws Exception {
    Collection<AddOn> addons = gatewayService.getActiveAddons();
    return addons;
  }

  @RequestMapping(value = "/getActives/{planId}", method = RequestMethod.GET)
  public ModelAndView getActives(@PathVariable(value = "planId") String planId){
    ModelAndView mav = new ModelAndView();
    mav.setViewName("addons");
    Collection<AddOn> addons = gatewayService.getActiveAddons();
    List<AddonDTO> addonDTOs = new ArrayList<AddonDTO>();
    List<AddonDTO> serviceDTOs = new ArrayList<AddonDTO>();
    AddonDTO addonDTO = null;
    for (AddOn addOn:addons){
      addonDTO = new AddonDTO(addOn.getId(),addOn.getName(),addOn.getDescription(),addOn.getAmount());
      if (addonDTO.isService())
        serviceDTOs.add(addonDTO);
      else
        addonDTOs.add(addonDTO);
    }
    mav.addObject("addons",addonDTOs);
    mav.addObject("services",serviceDTOs);
    return mav;
  }

}
