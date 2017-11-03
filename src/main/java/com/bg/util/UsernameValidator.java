package com.bg.util;

import java.sql.SQLException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bg.model.UserDao;

@Component
public class UsernameValidator implements ConstraintValidator<isValidUsername, String>{

	@Override
	public void initialize(isValidUsername isValidUsername) {
		// TODO Auto-generated method stub
	}
	
	@Autowired
	UserDao ud;
	@Override
	public boolean isValid(String username, ConstraintValidatorContext ctx) {
		try {
			if(ud.userExists(username) || username.trim().isEmpty()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
