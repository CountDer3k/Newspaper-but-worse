package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Controller
public class HomeController {
	@Value("${spring.application.name}")
	String name;
	PostService postService;
	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	
	@Autowired
	public HomeController(PostService postService) {
		this.postService = postService;
	}
	

	@GetMapping("/")
	@Log
	public String home(Model model){
		
		try {
		// Get all posts from the db
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		posts = postService.getAllPosts();
		
		// If no posts are available show the empty content div
		if(posts.size() == 0) {
			model.addAttribute("isEmpty", true);
			model.addAttribute("empty", "No content available to display...");
		}else {
			model.addAttribute("isEmpty", false);
			model.addAttribute("posts", posts);
			model.addAttribute("name", name);
		}
		return "index";
		} catch(Exception e) {
			logger.error("" + e.toString());
			return null;
		}
		
	}
}
