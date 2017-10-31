package com.bg.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Profile;
import com.bg.model.ProfileDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;


@Controller
@RequestMapping(value = "/settings")
public class SettingsController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
		binder.registerCustomEditor(Date.class, "dateOfBirth", new CustomDateEditor(sdf, false));
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String settings(Model m, HttpSession s) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String username = sessionUser.getUsername();
		String email = sessionUser.getEmail();
		User u = new User(username, email);
		m.addAttribute("user", u);
		return "accountSettings";
	}
	
	@Autowired
	UserDao ud;
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String saveSettings(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession s) {

		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		if(result.hasErrors()) {
			return "accountSettings";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String newUsername = user.getUsername();
		String newEmail = user.getEmail();
		
		boolean uIsChanged = false;
		boolean eIsChanged = false;
		
		if(newUsername != null && !newUsername.isEmpty()) {
			try {
				if(!ud.userExists(newUsername)) {
					ud.changeUsername(sessionUser.getId(), newUsername);
					uIsChanged = true;
				}else {
					//error pages ->
					// User exists, please select another username
					//email not valid
				}
			} catch (SQLException e) {
				e.getMessage();
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		if(newEmail != null && !newEmail.isEmpty()) {
			try {
				ud.changeEmail(sessionUser.getId(), newEmail);
				eIsChanged = true;
			} catch (SQLException e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(req, resp);
			}
		}
		
		
		if(!uIsChanged) {
			newUsername = sessionUser.getUsername();
		}
		if(!eIsChanged) {
			newEmail = sessionUser.getEmail();
		}
		

		user = new User(sessionUser.getId(), newUsername, sessionUser.getPassword(), newEmail, 
				sessionUser.getProfile(), sessionUser.getLikedPosts(),sessionUser.getLikedComments());
		
		s.removeAttribute("user");
		s.setAttribute("user", user);
		return "forward:/";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.GET)
	public String password(HttpSession s) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		return "passwordSettings";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.POST)
	public String passwordChange(HttpSession s, HttpServletRequest req, Model m) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String password1 = req.getParameter("pass1");
		String password2 = req.getParameter("pass2");
		
		if(!password1.equals(password2)) {
			m.addAttribute("error", "passwords mismatch");
			return "passwordError";
		}
		if(password1.length() < 5 || !password1.matches(".*[A-Za-z].*") || !password1.matches(".*[1-9].*")) {
			m.addAttribute("error", "passwords must be at least 5 characters and must contain at least 1 number"
					+ " and a letter");
			return "passwordError";
		}
		

		try {
			ud.changePassword(sessionUser.getId(), password1);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return "forward:/";
	}
	
	@Autowired
	ProfileDao pd;
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String profileSettings(Model m, HttpSession s) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		Profile p = null;
		if(sessionUser.getProfile() == null) {
			p = new Profile();
		}else {
			p = sessionUser.getProfile();
		}
		m.addAttribute("profile", p);
		
		return "profileSettings";
	}
	
	@Autowired
	ProfileDao profileDao;
	@RequestMapping(value="/profile", method = RequestMethod.POST)
	public String savePrSettings(@Valid @ModelAttribute("profile") Profile p, BindingResult result, 
			HttpSession s) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		if(result.hasErrors()) {
			return "profileSettings";
		}
		
		User userSession = (User) s.getAttribute("user");
		
		if(userSession.getProfile() == null) {
			userSession.setProfile(p);
			try {
				profileDao.insertProfile(p, userSession);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				if(p.getAvatarUrl() != null && !p.getAvatarUrl().isEmpty()) {
					profileDao.changeAvatar(p.getAvatarUrl(), userSession);
					userSession.getProfile().setAvatarUrl(p.getAvatarUrl());
				}
				if(p.getFullName() != null && !p.getFullName().isEmpty()) {
					profileDao.changeFullName(p.getFullName(), userSession);
					userSession.getProfile().setFullName(p.getFullName());
				}
				if(p.getGender() != null && !p.getGender().isEmpty()) {
					profileDao.changeGender(p.getGender(), userSession);
					userSession.getProfile().setGender(p.getGender());
				}
				if(p.getDateOfBirth() != null) {
					profileDao.changeDateOfBirth(p.getDateOfBirth(), userSession);
					userSession.getProfile().setDateOfBirth(p.getDateOfBirth());
				}
				if(p.getInfo() != null && !p.getInfo().isEmpty()) {
					profileDao.changeInfo(p.getInfo(), userSession);
					userSession.getProfile().setInfo(p.getInfo());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		s.removeAttribute("user");
		s.setAttribute("user", userSession);
		return "forward:/";
	}
}
