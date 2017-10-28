package com.bg.util;

import java.sql.SQLException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bg.model.UserDao;

@Component
public class EmailValidator implements ConstraintValidator<isValidEmail, String>{

	@Override
	public void initialize(isValidEmail isValidEmail) {
		
	}
	
	@Autowired
	UserDao ud;
	@Override
	public boolean isValid(String email, ConstraintValidatorContext ctx) {

		try {
			if(ud.isValidEmailAddress(email) && !ud.emailExists(email)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
