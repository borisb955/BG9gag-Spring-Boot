package com.bg.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.util.Validator;

@Controller
@RequestMapping(value="/posts")
public class CollectionController {
	
	@Autowired
	PostDao pd;
	
	@RequestMapping(value = "fresh", method = RequestMethod.GET)
	public String fresh(Model m) {
		
		HashSet<Post> posts = null;
		try {
			posts = pd.getAllPosts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TreeSet<Post> sortedPosts = new TreeSet<>((p1, p2) -> {
		       if (p2.getDateTime().isBefore(p1.getDateTime())) {
		            return -1;
		        } else if (p2.getDateTime().isAfter(p1.getDateTime())) {
		            return 1;
		        } else {
		            return 0;
		        }  
		}) ;
		
		for (Post post : posts) {
			sortedPosts.add(post);
		}
		
		m.addAttribute("posts", sortedPosts);
		
		return "fresh";
	}
	
	@RequestMapping(value = "/gifs", method = RequestMethod.GET)
	public String gifs(Model m) {
		
		ArrayList<Post> gifs = new ArrayList<>();
		
		try {
			gifs = pd.getAllGifs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m.addAttribute("posts", gifs);
		
		return "gifs";
	}
		
}
