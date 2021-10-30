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


@Controller
public class PostController {
	
	PostService postService;
	private Logger logger = LoggerFactory.getLogger(PostRepository.class);
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("articles/articleNum/{articleId}")
	public String showArticleView(@PathVariable String articleId, Model model) {
		
		try {
		// Get article from db		
		PostArticleModel pam = postService.getPostWithAuthorByID(articleId);
		
		model.addAttribute("author", pam.getName());
		model.addAttribute("article", pam.getPost().getArticle());
		model.addAttribute("articleId", articleId);
		
		return "article/article";
		} catch(Exception e) {
			logger.error(e.toString());
			return null;
		}
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
		//?? Get this from logged in user
		int userID = 1;
		
	    if(postService.isValidPost(postDto)) {
	    	PostModel addedPost = postService.addNewPost(postDto, userID);
        	model.addAttribute("post", postDto);
	    	return "redirect:/";
	    }
	    
	    model.addAttribute("msg", "Adding new post Failed!");
	    return "article/artcileForm";
	}

}
