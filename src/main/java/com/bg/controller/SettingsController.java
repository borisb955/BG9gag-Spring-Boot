package com.bg.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bg.model.Comment;
import com.bg.model.Post;
import com.bg.WebInitializer;
import com.bg.model.Profile;
import com.bg.model.ProfileDao;
import com.bg.model.UpvoteDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.model.User.ChangeAccount;
import com.bg.util.Validator;


@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

	@Autowired
	UpvoteDao upvoteDao;
	@Autowired
	ProfileDao pd;
	@Autowired
	UserDao ud;

	/**
	 * Passing ModelAttribute to a form with session user's username and email.
	 * Use in saveSettings(...)
	 */
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
	
	
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String saveSettings( @ModelAttribute("user") User user, BindingResult result, 
			HttpSession s, Model m) {


		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String newUsername = user.getUsername();
		String newEmail = user.getEmail();
		
		try {
			if(!sessionUser.getUsername().equals(newUsername)) {
				if(!ud.userExists(newUsername) && !newUsername.trim().isEmpty()) {
					ud.changeUsername(sessionUser.getId(), newUsername);
					File oldFile = new File(WebInitializer.LOCATION 
											+File.separator+ "users"
											+File.separator + sessionUser.getUsername());
					File newFile = new File(WebInitializer.LOCATION 
										 	+File.separator+ "users"
										 	+File.separator + newUsername);
					if(oldFile.exists()) {
						oldFile.renameTo(newFile);
					}
					sessionUser.setUsername(newUsername);
				}else {
					m.addAttribute("error", "User exists or user is empty");
					return "accountSettings";
				}
			}
			if(!sessionUser.getEmail().equals(newEmail)) {
				if(ud.isValidEmailAddress(newEmail) && !ud.emailExists(newEmail)) {
					ud.changeEmail(sessionUser.getId(), newEmail);
					sessionUser.setEmail(newEmail);
				}else {
					m.addAttribute("error", "Email is not valid, or already exists");
					return "accountSettings";
				}
			}
		}catch (SQLException e) {
			e.getMessage();
		}
		
		s.removeAttribute("user");
		s.setAttribute("user", sessionUser);
		
//		boolean uIsChanged = false;
//		boolean eIsChanged = false;
//		
//		if(newUsername != null && !newUsername.isEmpty()) {
//			try {
//				if(!ud.userExists(newUsername)) {
//					ud.changeUsername(sessionUser.getId(), newUsername);
//					uIsChanged = true;
//				}else {
//					//error pages ->
//					// User exists, please select another username
//					//email not valid
//				}
//			} catch (SQLException e) {
//				e.getMessage();
//			}
//		}
//		
//		if(newEmail != null && !newEmail.isEmpty()) {
//			try {
//				ud.changeEmail(sessionUser.getId(), newEmail);
//				eIsChanged = true;
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		if(!uIsChanged) {
//			newUsername = sessionUser.getUsername();
//		}
//		if(!eIsChanged) {
//			newEmail = sessionUser.getEmail();
//		}
//		
//
//		user = new User(sessionUser.getId(), newUsername, sessionUser.getPassword(), newEmail, 
//				sessionUser.getProfile(), sessionUser.getLikedPosts(),sessionUser.getLikedComments());
//		
//		s.removeAttribute("user");
//		s.setAttribute("user", user);
	
		return "forward:/";
	}
	
	/**
	 * Returning a page for changing password
	 */
	@RequestMapping(value="/password", method = RequestMethod.GET)
	public String password(HttpSession s) {
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		return "passwordSettings";
	}
	
	/**
	 * After some validations changing the password
	 */
	@RequestMapping(value="/password", method = RequestMethod.POST)
	public String passwordChange(HttpSession s, HttpServletRequest req, Model m) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User sessionUser = (User) s.getAttribute("user");
		
		String password1 = req.getParameter("pass1");
		String password2 = req.getParameter("pass2");
		
		//validations
		if(!password1.equals(password2)) {
			m.addAttribute("error", "passwords mismatch");
			return "passwordSettings";
		}
		if(password1.length() < 5 || !password1.matches(".*[A-Za-z].*") || !password1.matches(".*[1-9].*")) {
			m.addAttribute("error", "passwords must be at least 5 characters and must contain at least 1 number"
					+ " and a letter");
			return "passwordSettings";
		}
		
		//changing pass
		try {
			ud.changePassword(sessionUser.getId(), password1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//WelcomeController
		return "forward:/";
	}
	
	/**
	 * Passing ModelAttribute to a form with session user's profile
	 */
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
	
	/**
	 * Getting information from form and after some validations
	 * setting or changing profile's fields
	 */
	@RequestMapping(value="/profile", method = RequestMethod.POST)
	public String savePrSettings(@RequestParam("failche") MultipartFile file, 
			@Valid @ModelAttribute("profile") Profile p, BindingResult result, 
			HttpSession s, HttpServletRequest req, Model m) {
		
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		if(result.hasErrors()) {
			return "profileSettings";
		}
		
		User userSession = (User) s.getAttribute("user");
		
		//Path for avatar pic
		String filePath =WebInitializer.LOCATION 
				 +File.separator+ "users"
				 +File.separator + userSession.getUsername()
				 +File.separator + "avatar";
	    

	    if(!file.isEmpty()) {
		    try {
			    File f = new File(filePath
    					+File.separator + "avatar.jpg");
			    
		    	//Make folders if there is a file and directory doesn't exist
			    File folders = new File( filePath );
			    folders.mkdirs();
		    	
			    //getting the file's extension
				MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
				MimeType type = allTypes.forName(file.getContentType());
				String ext = type.getExtension(); 
				
				//validating file's ext
				String[] allowedExt = new String[] {".jpg", "jpeg", ".png", ".bmp", ".gif", ".tiff" };
				boolean isAllowed = false;
				
				for (String string : allowedExt) {
					if(ext.contains(string)) {
						isAllowed = true;
					}
				}
				
				if(!isAllowed) {
					m.addAttribute("error", "You must upload a pic");
					return "profileSettings";
				}
				
		    	
				file.transferTo(f);
			} catch (IllegalStateException | IOException e1) {
				e1.printStackTrace();
			} catch (MimeTypeException e2) {
				e2.printStackTrace();
			}
	    }

		
		//Making new profile in DB if user modifies profile's info for the first time
		if(userSession.getProfile() == null) {
			userSession.setProfile(p);
			try {
				pd.insertProfile(p, userSession);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//Some validations before inserting in DB
		}else {
			try {
				if(!file.isEmpty()) {
					pd.changeAvatar("avatar.jpg", userSession);
					userSession.getProfile().setAvatarUrl("avatar.jpg");
				}
				if(p.getFullName() != null && !p.getFullName().trim().isEmpty()) {
					pd.changeFullName(p.getFullName(), userSession);
					userSession.getProfile().setFullName(p.getFullName());
				}
				if(p.getGender() != null && !p.getGender().trim().isEmpty()) {
					pd.changeGender(p.getGender(), userSession);
					userSession.getProfile().setGender(p.getGender());
				}
				if(p.getDateOfBirth() != null) {
					pd.changeDateOfBirth(p.getDateOfBirth(), userSession);
					userSession.getProfile().setDateOfBirth(p.getDateOfBirth());
				}
				if(p.getInfo() != null && !p.getInfo().trim().isEmpty()) {
					pd.changeInfo(p.getInfo(), userSession);
					userSession.getProfile().setInfo(p.getInfo());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		s.removeAttribute("user");
		s.setAttribute("user", userSession);
		return "redirect:profile";
	}
}
