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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class PostController {

	PostService service;
	Logger logger = LoggerFactory.getLogger(PostService.class);

	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}


	@GetMapping("/article/articleNum/{articleID}")
	public String showViewArticle(WebRequest request, Model model) {
		//PostDto userDto = new PostDto();
		//model.addAttribute("post", userDto);
		return "article/viewArticle";
	}


	@GetMapping("/article/createArticle")
	public String showCreate_A_Post(WebRequest request, Model model) {
		PostDto postDto = new PostDto();
		model.addAttribute("post", postDto);
		return "article/createArticle";
		//return "article/createArticle";
	}


	@PostMapping("/article/createArticle")
	public String create_New_post(
			@Validated @ModelAttribute("post") PostDto postDto,
			//BindingResult bindResult,
			//HttpServletRequest request,
			Model model
			//,Errors errors
			) {
		logger.debug("Post Mapping");
		try {
			if(service.isValidPost(postDto)) {
				boolean didCreate = service.create_new_post_article(postDto);
				logger.debug("Post success: "+didCreate);
				model.addAttribute("post", postDto);
				return "/";
			}
			else {
				model.addAttribute("msg", "All fields must be filled");
				return "/";
			}
		} catch(Exception e) {
			model.addAttribute("trace", e.toString());
			return "/";
		}
	}

//	@PostMapping("/article/creatArticle")
//	public String create_A_Post(
//			@Validated @ModelAttribute("post") PostDto postDto,
//			BindingResult bindResult,
//			HttpServletRequest request,
//			Model model,
//			Errors errors) {
//		try {
//			if(bindResult.hasErrors()) {
//				System.out.print("error found");
//				return "error";
//			}else {
//				System.out.println("No errors");
//				if(service.isValidPost(postDto)) {
//					//boolean didCreate = 
//					service.create_new_post_article(postDto);
//					//model.addAttribute("msg", "Registration Confirmed!");
//					model.addAttribute("post", postDto);
//					//return "login";
//					return "redirect:/";
//				}
//
//				model.addAttribute("msg", "Article Creation Failed!");
//				//return "index";
//				return "redirect:/";
//			}
//		} catch(Exception e) {
//			return "error";
//		}
//	}











	/// OLD CODE


	//	@GetMapping("/article/articleNum/{articleId}")
	//	public String article(@PathVariable String articleId, Model model) {
	//
	//
	//		//?? Grab post from id and pass them into post
	//		PostModel post = new PostModel(-1);
	//
	//		//Testing
	//		ArticleModel article = new ArticleModel();
	//		article.setTitle("Hogwarts, A History");
	//		article.setContent("Hogwarts: A History, also known as Hogwarts, A History, is a book concerning Hogwarts School of Witchcraft and Wizardry and its history that was written by Bathilda Bagshot[1]. It was Hermione Granger's favourite book and she often referred to this book on many things concerning Hogwarts School of Witchcraft and Wizardry. Three of the things that are frequently brought up are the Great Hall's enchanted ceiling that shows the weather outside, the fact that you cannot apparate or disapparate on Hogwarts grounds and the fact that electronic devices do not work within the grounds.\n"
	//				+ "Incredible PC game bundle, from $10\n"
	//				+ "BUY FROM FANATICAL\n"
	//				+ "\n"
	//				+ "The book was also very popular in Harry Potter's second year at Hogwarts, when every copy was checked out of the library due to the reopening of the Chamber of Secrets. However, Hermione shows slight frustration with the book when scathingly renaming the book in her fourth year because it does not mention the use of house-elves at Hogwarts, even going so far as to suggest a couple of alternative titles for it: A Revised History of Hogwarts and A Highly Biased and Selective History of Hogwarts Which Glosses Over the Nastier Aspects of the School.");
	//
	//		post.setId(1);
	//		post.setUserId(10);
	//		post.setArticle(article);
	//
	//		model.addAttribute("post", post);
	//		model.addAttribute("articleId", articleId);
	//
	//		return "article/viewArticle";
	//	}	

}



