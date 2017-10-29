package com.bg.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bg.model.Post;
import com.bg.model.PostDao;
import com.bg.model.User;
import com.bg.model.UserDao;
import com.bg.util.Validator;

@Controller
public class PostController {
	@Autowired
	UserDao userDao;
	@Autowired
	PostDao postDao;

	private static String URL = "D:" + File.separator + "postPics" + File.separator;

	@ResponseBody
	@RequestMapping(value = "/postpic", method = RequestMethod.GET)
	public void showPostWithComment(HttpSession s, Model model, HttpServletResponse response,
			HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String pictureUrl = request.getParameter("pictureUrl");
		String fullURL = URL + userName + File.separator + pictureUrl;
		System.out.println(fullURL);

		if (pictureUrl == null || pictureUrl.isEmpty()) {
			pictureUrl = "D:/postPics/defaultPic.png";
		}

		File myFile = new File(fullURL);
		try {
			OutputStream out = response.getOutputStream();
			Path path = myFile.toPath();
			Files.copy(path, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/postWithComments/postId={postId}/userId={userId}", method = RequestMethod.GET)
	public String showPostWithComment(@PathVariable("postId") int postId, @PathVariable("userId") int userId, HttpSession s, Model model) {
		try {
			User user = userDao.getUserById(userId);
			Post post = postDao.getPost(postId, user);
			model.addAttribute("userPostPage", user);
			model.addAttribute("postPostPage", post);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Validator.notLogged(s)) {
			return "notLoggedPostPage";
		}
		return "loggedPostPage";

	}

}
