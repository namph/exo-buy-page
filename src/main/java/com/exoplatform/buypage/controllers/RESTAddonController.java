package com.exoplatform.buypage.controllers;


import com.braintreegateway.AddOn;
import com.exoplatform.buypage.gateway.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Collection;

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

}
