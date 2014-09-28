package com.hiwaytaxi.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaxiBasic {
	private Logger logger = Logger.getLogger(TaxiBasic.class);
	String message = "Welcome to Spring MVC!";
	 
	@RequestMapping("/SendMessage")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in Taxi Basic controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		logger.info("This is message from logger" + message);
		return mv;
	}
}
