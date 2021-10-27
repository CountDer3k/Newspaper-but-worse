package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@Controller
public class HomeController {
	@Value("${spring.application.name}")
	String name;
	PostService postService;
	
	@Autowired
	public HomeController(PostService postService) {
		this.postService = postService;
	}
	

	@GetMapping("/")
	public String home(Model model){
		
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		posts = postService.getAllPosts();
		
		if(posts.size() == 0) {
			model.addAttribute("isEmpty", true);
			model.addAttribute("empty", "No content available to display...");
		}else {
			model.addAttribute("isEmpty", false);
			model.addAttribute("posts", posts);
			model.addAttribute("name", name);
		}
		
		
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
