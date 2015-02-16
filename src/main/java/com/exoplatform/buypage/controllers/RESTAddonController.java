package com.exoplatform.buypage.controllers;


import com.exoplatform.buypage.gateway.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping(value = "addons", produces = "application/json")
public class RESTAddonController {
  private static final Logger log = LoggerFactory.getLogger(RESTAddonController.class);
  @Inject
  IService gatewayService;
}
