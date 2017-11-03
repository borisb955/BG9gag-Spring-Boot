package com.bg.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.PostTagDao;
import com.bg.util.Validator;

@Controller
@RequestMapping(value="/posts")
public class CollectionController {
	
	@Autowired
	PostDao pd;
	@Autowired
	PostTagDao ptd;
	
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
	
	@RequestMapping(value = "/tag/tagName={tagName}", method = RequestMethod.GET)
	public String tag(Model m, HttpServletRequest req, 
			@PathVariable("tagName") String tagName) {
		

		ArrayList<Post> posts = new ArrayList<>();
		try {
			posts = ptd.getPostsForTag(tagName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.addAttribute("posts", posts);
		
		return "tagPosts";
	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String search(HttpServletRequest req, Model m) {
		String searchedWord = req.getParameter("search");
		
		try {
			ArrayList<Post> posts = pd.searchWordInDesc(searchedWord);
			m.addAttribute("posts", posts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "searchByDesc";
	}
	
	@RequestMapping(value = "hot", method = RequestMethod.GET)
	public String hot(Model m) {
		
		HashSet<Post> posts = null;
		try {
			posts = pd.getAllPosts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TreeSet<Post> sortedPosts = new TreeSet<>((p1, p2) -> {
		       if (p1.getPoints() < p2.getPoints()) {
		            return -1;
		        } else {
		            return 1;
		        } 
		}) ;
		
		for (Post post : posts) {
			sortedPosts.add(post);
		}
		
		m.addAttribute("posts", sortedPosts);
		
		return "hotPosts";
	}
}
