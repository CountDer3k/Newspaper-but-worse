package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Models.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Models.PostModel;

@Controller
public class HomeController {
	@Value("${spring.application.name}")
	String name;

	@GetMapping("/")
	public String home(Model model){

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

		model.addAttribute("posts", postList);
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
