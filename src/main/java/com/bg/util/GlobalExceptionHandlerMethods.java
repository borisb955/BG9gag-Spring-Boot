package com.bg.util;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ControllerAdvice
public class GlobalExceptionHandlerMethods {
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String GlobalExceptionMethod(Exception e, Model m) {
		
		System.out.println("Unknown exception occured: " + e);
		m.addAttribute("error", e);
		return "Exception";
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NotFound.class)
	public String PageNotFound(Exception e, Model m) {
		
		System.out.println("SQLException Occured: " + e);
		return "Exception";
	}
}
