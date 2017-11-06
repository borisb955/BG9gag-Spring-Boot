package com.bg.util;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;


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
	

	@ExceptionHandler(value = MySQLSyntaxErrorException.class)
	public String SQLException(Exception e, Model m) {
		
		System.out.println("SQLException : " + e);
		m.addAttribute("error", e);
		return "Exception";
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = IOException.class)
	public String PageNotFound(Exception e, Model m) {
		
		System.out.println("Page not found: " + e);
		m.addAttribute("error", e);
		return "Exception";
	}
}
