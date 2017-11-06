package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bg.WebInitializer;
import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;
import com.bg.util.Validator;


@Component
@MultipartConfig
@RequestMapping(value = "upload")
public class UploadController {
	@Autowired
	PostDao pd;
	
	/**
	 * Returning the upload page
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String upload(HttpSession s){
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		return "upload";
	}
	
	/**
	 * Getting info from upload page and after some validations, uploaded file is
	 * saved either as local uploaded or as a YouTube link
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String uploading(@RequestParam("failche") MultipartFile file, HttpServletRequest req,
			 HttpSession s, Model m){
		if(Validator.notLogged(s)) {
			return "forward:/";
		}
		
		User u = (User) s.getAttribute("user");
		String description = req.getParameter("description");
		//full YouTube link provided by the user
		String youtube = req.getParameter("youtube");
		//Getting only the id of the YouTube link
		String linkId = youtube.substring(youtube.lastIndexOf("=")+1);
		//Splitting provided tags by space
		String[] tags = req.getParameter("tags").split(" ");
		//Local directory where the files will be saved
		String filePath =WebInitializer.LOCATION 
						 +File.separator+ "users"
						 +File.separator + u.getUsername()
						 +File.separator + "postPics";
		
		if(file.isEmpty() && (youtube.trim().isEmpty() || youtube == null)) {
			m.addAttribute("error", "You must upload a pic or video");
			return "upload";
		}
		if(!file.isEmpty() && (!youtube.trim().isEmpty())) {
			m.addAttribute("error", "Can't upload two files");
			return "upload";
		}
		if(description == null || description.trim().isEmpty()) {
			m.addAttribute("error", "You must write a description");
			return "upload";
		}
		if(tags == null || req.getParameter("tags").trim().isEmpty()) {
			m.addAttribute("error", "You must write at least one tag");
			return "upload";
		}
		
		try {
			Post post = null;
			//Save a local uploaded file
			if(!file.isEmpty()) {
				
				//Getting the extension of the file
				MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
				MimeType type = allTypes.forName(file.getContentType());
				String ext = type.getExtension(); 
				
				String[] allowedExt = new String[] {".mp4", ".jpg", "jpeg", ".png", ".bmp",
													".gif", ".tiff" };
				//Check if uploaded file's ext is allowed
				boolean isAllowed = false;
				
				for (String string : allowedExt) {
					if(ext.contains(string)) {
						isAllowed = true;
					}
				}
				
				if(!isAllowed) {
					m.addAttribute("error", "You must upload a pic or video from one of the given extensions");
					return "upload";
				}
				
				//Making directories if doesn't exist
			    File folders = new File( filePath );
			    folders.mkdirs();
			    
				
			    //TODO: make posts names unique and remove random
				File f = new File( filePath 
								 + File.separator
								 + u.getUsername() 
								 + new Random().nextInt(2_000_000_000) + ext);
				
				file.transferTo(f);
				
				boolean isVideo = false;
				
				if(ext.contains(".mp4")) {
					isVideo = true;
				}
				
			    post = new Post(description, f.getName(), LocalDateTime.now(), isVideo, false, u);
			    
			//upload a YouTube video
			}else {
				if(youtube.contains("https://www.youtube.com/watch?") && linkId.length() == 11) {
					String fullLink = "https://www.youtube.com/embed/" + linkId;
					post = new Post(description, fullLink, LocalDateTime.now(), true, true, u);
					
				}else {
					m.addAttribute("error", "Please copy the link from a youtube video. "
											+ "Videos from collections not allowed.");
					return "upload";
				}
			}
			
				pd.insertInTransaction(post, tags);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MimeTypeException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m.addAttribute("success", "You have successfully uploaded your file");
		return "upload";
	}
}
