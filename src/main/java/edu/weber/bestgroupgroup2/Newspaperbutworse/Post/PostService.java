package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;

@Service
public class PostService implements Serializable{

	private PostRepository articleRepository;
	private Logger logger = LoggerFactory.getLogger(PostService.class);



	private PostRepository postRepository;

	
	public PostService() {}

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

	public boolean POST_post() {
		return false;
	}

	public boolean POST_Article() {
		return false;
	}

	public boolean POST_Comment() {
		return false;
	}



	public List<PostModel> getPosts() {
		try {
			return articleRepository.getAllPosts();
		} catch(Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

}
