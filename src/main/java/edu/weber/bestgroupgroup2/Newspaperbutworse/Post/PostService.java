package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService{

	private Logger logger = LoggerFactory.getLogger(PostService.class);
	private PostRepository postRepository;

	
	//public PostService() {}

	@Autowired
	public PostService(PostRepository postRepo) {
		this.postRepository = postRepo;
	}

	public boolean isValidPost(PostDto postDto) {

		//checks empty strings since these can't be null because of annotations in User
		if(postDto.getTitle().equals("") || postDto.getContent().equals(""))
			return false;
		return true;
	}


	public boolean create_new_post_article(PostDto postDto) {

		try {
			ArticleModel article = new ArticleModel();
			PostModel post = new PostModel();

			article.setTitle(postDto.getTitle());
			article.setContent(postDto.getContent());
			article.setAccess(postDto.getAccess());

			post.setArticle(article);
			//?? make this take an int value over a date or change it on the db
			post.setCreateDate(new Date(0));
			//?? Get value of the current logged in user
			post.setUserId(0);
			
			// POST to database
			postRepository.POST_post(post);

		} catch(Exception e)
		{
			logger.error("PostService - create_new_post_article(): " + e.toString());
			return false;
		}

		return true;
	}

//	public boolean POST_post() {
//		return false;
//	}
//
//	public boolean POST_Article() {
//		return false;
//	}
//
//	public boolean POST_Comment() {
//		return false;
//	}



	public List<PostModel> getPosts() {
		try {
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
		
		return postList;
		} catch(Exception e) {
			logger.error("Something here is null" +e.toString());
			return null;
		}
//		try {
//			//return articleRepository.getAllPosts();
//			
//		} catch(Exception e) {
//			logger.error(e.toString());
//		}
//		return null;
	}

}
