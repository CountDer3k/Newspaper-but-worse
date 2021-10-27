package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService{

//	private static UserService INSTANCE;
	
    private PostRepository postRepository;
    

    @Autowired
    public PostService(PostRepository userRepo) {
    	this.postRepository = userRepo;
    }
    
    public boolean isValidPost(PostDto postDto) {
    	
    	if(postDto.getTitle().equals("") || postDto.getContent().equals("") || postDto.getAccess().equals(""))
    		return false;
    	return true;
    }
    
    public PostModel getPostByID(String id) {
    	PostModel post = new PostModel();

    	post = postRepository.getArticleByID(id);
    	
    	return post;
    }
    
    public PostArticleModel getPostWithAuthorByID(String id) {
    	PostArticleModel pam = postRepository.getArticleWithAuthorByID(id);
    	return pam;
    }
    
    public List<PostArticleModel> getAllPosts(){
    	List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
    	
    	posts = postRepository.getAllPosts();
    	
    	return posts;
    }
    
    public PostModel addNewPost(PostDto postDto) {
    	PostModel post = new PostModel();
    	ArticleModel article = new ArticleModel();
    	
    	article.setTitle(postDto.getTitle());
    	article.setContent(postDto.getContent());
    	article.setAccess(postDto.getAccess());
    	
    	post.setUserId(1);
    	post.setCreateDate(new Date(0));
    	post.setArticle(article);
    	
    	return postRepository.savePost(post);
    }
    
}
