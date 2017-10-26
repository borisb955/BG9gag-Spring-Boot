package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/opa")
public class LoginController {
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "hivesi";
	}
}
