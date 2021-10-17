package edu.weber.bestgroupgroup2.Newspaperbutworse.Article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Models.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Models.PostModel;


@Controller
public class ArticleController {

	@GetMapping("/article/{articleId}")
	public String article(@PathVariable String articleId, Model model) {
		
		
		//?? Grab post from id and pass them into post
		PostModel post = new PostModel(-1);
		
		//Testing
		ArticleModel article = new ArticleModel();
		article.setTitle("Hogwarts, A History");
		article.setContent("Hogwarts: A History, also known as Hogwarts, A History, is a book concerning Hogwarts School of Witchcraft and Wizardry and its history that was written by Bathilda Bagshot[1]. It was Hermione Granger's favourite book and she often referred to this book on many things concerning Hogwarts School of Witchcraft and Wizardry. Three of the things that are frequently brought up are the Great Hall's enchanted ceiling that shows the weather outside, the fact that you cannot apparate or disapparate on Hogwarts grounds and the fact that electronic devices do not work within the grounds.\n"
				+ "Incredible PC game bundle, from $10\n"
				+ "BUY FROM FANATICAL\n"
				+ "\n"
				+ "The book was also very popular in Harry Potter's second year at Hogwarts, when every copy was checked out of the library due to the reopening of the Chamber of Secrets. However, Hermione shows slight frustration with the book when scathingly renaming the book in her fourth year because it does not mention the use of house-elves at Hogwarts, even going so far as to suggest a couple of alternative titles for it: A Revised History of Hogwarts and A Highly Biased and Selective History of Hogwarts Which Glosses Over the Nastier Aspects of the School.");

		post.setId(1);
		post.setUserId(10);
		post.setArticle(article);
		
		model.addAttribute("post", post);
		model.addAttribute("articleId", articleId);
		
		return "article/article";
	}	
}