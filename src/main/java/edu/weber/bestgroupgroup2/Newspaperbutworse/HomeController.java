package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
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
	public ModelAndView home(){
		
		ModelAndView modelAndView = new ModelAndView("index");
		
		try {
			// Get all posts from the db
			List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
			posts = postService.getAllPosts();
			
			// If no posts are available show the empty content div
			if(posts.size() == 0) {
				modelAndView.getModelMap().addAttribute("isEmpty", true);
				modelAndView.getModelMap().addAttribute("empty", "No content available to display...");
			}else {
				modelAndView.getModelMap().addAttribute("isEmpty", false);
				modelAndView.getModelMap().addAttribute("posts", posts);
				modelAndView.getModelMap().addAttribute("name", name);
			}
			return modelAndView;
			} catch(Exception e) {
				logger.error("" + e.toString());
				return new ModelAndView("/error");
			}
	}
	
}
