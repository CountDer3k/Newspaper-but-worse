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

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;


@Controller 
public class PostController {
	
	PostService postService;
	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("articles/articleNum/{articleId}")
	@Log
	public ModelAndView showArticleView(@PathVariable String articleId) {
		ModelAndView modelAndView = new ModelAndView("article/article");
		try {
		// Get article from db		
		PostArticleModel pam = postService.getPostWithAuthorByID(articleId);
		modelAndView.getModelMap().addAttribute("author", pam.getName());
		modelAndView.getModelMap().addAttribute("article", pam.getPost().getArticle());
		modelAndView.getModelMap().addAttribute("articleId", articleId);
		
		return modelAndView;
		} catch(Exception e) {
			logger.error(e.toString());
			return null;
		}
	}
	
	
	@GetMapping("/articles/articleForm")
	@Log
	public ModelAndView showRegistrationForm(WebRequest request) {
	    PostDto postDto = new PostDto();
	    ModelAndView modelAndView = new ModelAndView("article/articleForm");
	    modelAndView.getModelMap().addAttribute("post", postDto);
	    return modelAndView;
	}
	
	
	
	@GetMapping("/articles/delete/{articleId}")
	@Log
	public String deleteArticle(@PathVariable String articleId) {
		
		postService.deletePost(articleId);
		
		return "index";
	}
	
	@PostMapping("/articles/articleForm")
	@Log
	public ModelAndView registerUserAccount(
	  @Validated @ModelAttribute("user") PostDto postDto,
	  BindingResult bindResult,
	  HttpServletRequest request,
	  Errors errors) {
		
		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		//?? Get this from logged in user
		int userID = 1;
		
	    if(postService.isValidPost(postDto)) {
	    	ModelAndView modelAndView = new ModelAndView("redirect:/");
	    	PostModel addedPost = postService.addNewPost(postDto, userID);
        	modelAndView.getModelMap().addAttribute("post", postDto);
	    	return modelAndView;
	    }

	    return new ModelAndView("user/registration", "msg", "Registration Failed!");
	}

}
