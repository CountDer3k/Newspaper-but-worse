package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;
import java.util.ArrayList;
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
    	List<PostArticleModel> posts = getAllPosts(50, 1);     	
    	return posts;
    }
    
    // Pagination method
    @Log
    public List<PostArticleModel> getAllPosts(int entriesAmount, int pageNum){
    	List<PostArticleModel> posts = postRepository.getAllPosts();
    	int postsCount = posts.size();
    	
    	int start = (entriesAmount * pageNum)-entriesAmount;
    	int end = (entriesAmount + start); 
    	
    	
    	//Edge case, requesting too high of a page number, or negative numbers return a blank set
    	if(start > postsCount || pageNum <= 0 || entriesAmount <= 0) {
    		return new ArrayList<PostArticleModel>();
    	}
    	//Edge case, not enough entries.
    	else if(end > postsCount) {
    		end = postsCount;
    	}
    	
    	return posts.subList(start, end);
    }
    
    @Log
    public List<PostArticleModel> getAllPostsForUserWithId(String authorId){
        // Pagination included
    	List<PostArticleModel> posts = getAllPostsForUserWithId(authorId, 50, 1);
        return posts;
    }
    
    
    // Pagination method
    @Log
    public List<PostArticleModel> getAllPostsForUserWithId(String authorId, int entriesAmount, int pageNum){
    	List<PostArticleModel> posts = postRepository.getAllPostsForUserWithId(authorId);
    	int postsCount = posts.size();
    	
    	int start = (entriesAmount * pageNum)-entriesAmount;
    	int end = (entriesAmount + start); 
    	
    	
    	//Edge case, requesting too high of a page number, or negative numbers return a blank set
    	if(start > postsCount || pageNum <= 0 || entriesAmount <= 0) {
    		return new ArrayList<PostArticleModel>();
    	}
    	//Edge case, not enough entries.
    	else if(end > postsCount) {
    		end = postsCount;
    	}
    	
    	return posts.subList(start, end);
    }
    

    @Log
    public List<Comment> getCommentsFromArticle(int articleID){
    	List<Comment> comments = postRepository.getCommentsFromArticle(articleID);
    	
    	return comments;
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
    public Comment addNewComment(CommentDto commentDto, int userID) {
    	Comment comment = new Comment();
    	
    	comment.setContent(commentDto.getContent());
    	comment.setParentId(commentDto.getParentId());
    	
    	return postRepository.saveComment(comment, userID);
    }
    
    @Log
    public ArticleModel editPost(PostDto postDto, int postID) {
        ArticleModel article = new ArticleModel();
        
        article.setTitle(postDto.getTitle()); 
        article.setContent(postDto.getContent());
        article.setAccess(postDto.getAccess());
        article.setPostId(postID);
    
        return postRepository.editArticle(article);
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
