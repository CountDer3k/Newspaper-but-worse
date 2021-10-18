package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;

public class PostService {

	private PostRepository articleRepository;
	Logger logger = LoggerFactory.getLogger(PostService.class);
	
	@Autowired
	public PostService(PostRepository articleRepo) {
		this.articleRepository = articleRepo;
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
