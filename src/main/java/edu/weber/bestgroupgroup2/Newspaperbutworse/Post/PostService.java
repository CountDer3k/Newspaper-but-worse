package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;



@Service
public class PostService{
	
    private PostRepository postRepository;
    private Logger logger = LoggerFactory.getLogger(PostRepository.class);

    private static PostService INSTANCE;
    
    
    public PostService() {
    	
    } 
    
    @Autowired
    public PostService(PostRepository userRepo) {
    	this.postRepository = userRepo;
    }
     
    @Log
    public boolean isValidPost(PostDto postDto) {
    	
    	if(postDto.getTitle().equals("") || postDto.getContent().equals("") || postDto.getAccess().equals(""))
    		return false;
    	return true;
    }
    
    @Log
    public PostModel getPostByID(String id) {
    	PostModel post = postRepository.getArticleByID(id);
    	
    	return post;
    }
    
    @Log
    public PostArticleModel getPostWithAuthorByID(String id) {
    	PostArticleModel pam = postRepository.getArticleWithAuthorByID(id);
    	return pam;
    }
    
    @Log
    public List<PostArticleModel> getAllPosts(){
    	List<PostArticleModel> posts = postRepository.getAllPosts();
    	
    	return posts;
    }
    
    @Log
    public PostModel addNewPost(PostDto postDto, int userID) {
    	PostModel post = new PostModel();
    	ArticleModel article = new ArticleModel();
    	
    	article.setTitle(postDto.getTitle()); 
    	article.setContent(postDto.getContent());
    	article.setAccess(postDto.getAccess());
    	

    	post.setUserId(userID);
    	
    	long millis = System.currentTimeMillis();  
    	post.setCreateDate(new Date(millis));
    	post.setModifiedDate(new Date(millis));
    	post.setArticle(article);
    	
    	return postRepository.savePost(post);
    }
    
    @Log
    public boolean deletePost(String id) {
    	return postRepository.deletePostArticle(id);
    }
    
    
    public static PostService getInstance() {
		if(INSTANCE == null){
			INSTANCE = new PostService();
		}
		return INSTANCE;
	}
    
}
