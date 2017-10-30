package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bg.SpringWebConfig;
import com.bg.WebInitializer;
import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;
import com.bg.util.Validator;


@Component
@MultipartConfig
@RequestMapping(value = "upload")
public class PicController {
	@Autowired
	PostDao pd;
	
	@RequestMapping(method=RequestMethod.GET)
	public String kachiSnimka(HttpSession s){
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		return "upload";
	}
	

	@RequestMapping(method=RequestMethod.POST)
	public String zapishiSnimka(@RequestParam("failche") MultipartFile file, HttpServletRequest req,
			 HttpSession s){
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User u = (User) s.getAttribute("user");
		String description = req.getParameter("description");
	    String tag1 = req.getParameter("tag1");
		String tag2 = req.getParameter("tag2");
		String tag3 = req.getParameter("tag3");
		String filePath =WebInitializer.LOCATION 
						 +File.separator+ "users"
						 +File.separator + u.getUsername()
						 +File.separator + "postPics";
		
		try {

			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType type = allTypes.forName(file.getContentType());
			String ext = type.getExtension(); // .whatever
			

		    File folders = new File( filePath );
		    folders.mkdirs();
		    
		    System.out.println(folders.getAbsolutePath());
			
		    //TODO: make posts names unique and remove random
			File f = new File( filePath 
							 + File.separator
							 + u.getUsername() 
							 + new Random().nextInt(2_000_000_000) + ext);
			
			file.transferTo(f);
			
		    Post post = new Post(description , f.getName() , LocalDateTime.now(), u);
		    
		    System.out.println("Absolute path -> " + f.getAbsolutePath());
		    
		    
			ArrayList<String> tags = new ArrayList<>();
			tags.add(tag1);
			tags.add(tag2);
			tags.add(tag3);
			
			pd.insertInTransaction(post, tags);
		    
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MimeTypeException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return "upload";
	}
}
