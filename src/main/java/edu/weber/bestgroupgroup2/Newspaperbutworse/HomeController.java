package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;

@Controller
public class HomeController {
	@Value("${spring.application.name}")
	String name;

	PostService postService;
	Logger logger = LoggerFactory.getLogger(PostService.class);

	@GetMapping("/")
	public String home(Model model){

		List<PostModel> pml = null;
		// ?? This is removed until posts are in the db
		try {
			pml = postService.getPosts();
		} catch(Exception e) {
			logger.error(e.getStackTrace().toString() + " " +e.toString() + " " + e.getLocalizedMessage());
		}
		model.addAttribute("posts", pml);
		model.addAttribute("name", name);
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	//	@GetMapping("/error")
	//	public String error(Model model) {
	//		return "error";
	//	}

}
