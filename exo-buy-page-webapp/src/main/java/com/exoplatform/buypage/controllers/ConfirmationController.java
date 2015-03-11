package com.exoplatform.buypage.controllers;

import com.exoplatform.buypage.gateway.IService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/confirmation")
@SessionAttributes("test")
public class ConfirmationController {

  @Inject
  IService gatewayService;

  @RequestMapping(value = "/success")
  public ModelAndView success(HttpSession httpSession){
    ModelAndView mav = new ModelAndView();
    mav.addObject("transactionId",httpSession.getAttribute("transactionId"));
    mav.setViewName("confirmation");
    return mav;
  }

}
