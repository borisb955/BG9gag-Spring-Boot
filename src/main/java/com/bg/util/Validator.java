package com.bg.util;

import javax.servlet.http.HttpSession;

import com.bg.model.User;

public class Validator {
	
	public static boolean notLogged(HttpSession s) {
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		User sessionUser = (User) s.getAttribute("user");
		
		if(s.isNew() || !logged || sessionUser == null) {
			return true;
		}
		return false;
	}
}
