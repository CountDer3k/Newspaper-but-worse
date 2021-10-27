package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@Controller
public class PostController {
	
	PostService postService;
	UserService userService;
	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	
	@Autowired
	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}
	
	@GetMapping("articles/articleNum/{articleId}")
	public String showArticleView(@PathVariable String articleId, Model model) {
		
		// Get article from db
		PostModel post = postService.getPostByID(articleId);
		ArticleModel article = post.getArticle();
		// Get User from post & db
		User user = userService.getUserByID(post.getUserId());
		String authorName = user.getFirstName() + " " + user.getLastName();	
		
		// add article to model attributes
		model.addAttribute("article", article);
		model.addAttribute("author", authorName);
		model.addAttribute("articleId", articleId);
		
		
		return "article/article";
	}
	
	@GetMapping("/articles/articleForm")
	public String showRegistrationForm(WebRequest request, Model model) {
	    PostDto postDto = new PostDto();
	    model.addAttribute("post", postDto);
	    return "article/articleForm";
	}
	
	@PostMapping("/articles/articleForm")
	public String registerUserAccount(
	  @Validated @ModelAttribute("user") PostDto postDto,
	  BindingResult bindResult,
	  HttpServletRequest request,
	  Model model,
	  Errors errors) {
		if(bindResult.hasErrors()) {
			return "error";
		}
		
		
	    if(postService.isValidPost(postDto)) {
	    	PostModel addedPost = postService.addNewPost(postDto);
        	model.addAttribute("post", postDto);
	    	return "redirect:/";
	    }
	    
	    model.addAttribute("msg", "Adding new post Failed!");
	    return "article/artcileForm";
	}

}
