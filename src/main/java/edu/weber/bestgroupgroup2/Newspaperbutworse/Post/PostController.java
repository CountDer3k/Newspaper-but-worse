package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {
	
	PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
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
