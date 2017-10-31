package com.bg.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

	@RequestMapping(value="/like", method=RequestMethod.POST)
	@ResponseBody
	public void likePost(HttpServletResponse resp){
		resp.setStatus(200);
		
	}
	
	@RequestMapping(value="/unlike", method=RequestMethod.POST)
	@ResponseBody
	public void unlikePost(HttpServletResponse resp){
		resp.setStatus(200);
		
	}
	
}
