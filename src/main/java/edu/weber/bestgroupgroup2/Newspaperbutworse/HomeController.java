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
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView("index");
		List<PostModel> postList = new ArrayList<PostModel>();

		ArticleModel article1 = new ArticleModel("Slytherin Quidditch Through The Ages","This is the first article");
		ArticleModel article2 = new ArticleModel("Hogwarts, A History","Hogwarts: A History, also known as Hogwarts, A History, is a book concerning Hogwarts School of Witchcraft and Wizardry and its history that was written by Bathilda Bagshot[1]. It was Hermione Granger's favourite book and she often referred to this book on many things concerning Hogwarts School of Witchcraft and Wizardry. Three of the things that are frequently brought up are the Great Hall's enchanted ceiling that shows the weather outside, the fact that you cannot apparate or disapparate on Hogwarts grounds and the fact that electronic devices do not work within the grounds.\n"
				+ "\n"
				+ "Incredible PC game bundle, from $10\n"
				+ "\n"
				+ "BUY FROM FANATICAL\n"
				+ "The book was also very popular in Harry Potter's second year at Hogwarts, when every copy was checked out of the library due to the reopening of the Chamber of Secrets. However, Hermione shows slight frustration with the book when scathingly renaming the book in her fourth year because it does not mention the use of house-elves at Hogwarts, even going so far as to suggest a couple of alternative titles for it: A Revised History of Hogwarts and A Highly Biased and Selective History of Hogwarts Which Glosses Over the Nastier Aspects of the School.");

		PostModel pm1 = new PostModel(3, 10, article1);
		PostModel pm2 = new PostModel(7, 11, article2);

		postList.add(pm1);
		postList.add(pm2);

		modelAndView.getModelMap().addAttribute("posts", postList);
		modelAndView.getModelMap().addAttribute("name", name);
		
		return modelAndView;
	}
	
}
