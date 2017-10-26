//package com.example.s8.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping(value = "/vesi")
//public class VesselController {
//
//	@RequestMapping(value = "/hi", method = RequestMethod.GET)
//	public String hiVesi() {
//		return "hivesi";
//	}
//	
//	@RequestMapping(value = "/bye", method = RequestMethod.GET)
//	public String byeVesi() {
//		return "byevesi";
//	}
//	
//	@RequestMapping(value = "/dogs/{dogNumber}", method = RequestMethod.GET)
//	public String getDog(Model m, @PathVariable("dogNumber") Integer dogNum) {
//		m.addAttribute("doggy", dogNum);
//		return "dog";
//	}
//}
