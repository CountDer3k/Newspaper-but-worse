package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@RestController
@RequestMapping("API/post")
public class RESTPostController {

	//?? Testing
	private Logger logger = LoggerFactory.getLogger(RESTPostController.class);

	private PostService postService;

	@Autowired
	public RESTPostController(PostService postService) {
		this.postService = postService;
	} 


	@GetMapping("articles")
	@Log
	public ResponseEntity<List<PostArticleModel>> getAllPost(
			@RequestParam(name = "entries", required = false) String entriesAmount,
			@RequestParam(name = "page", required = false) String pageNum){
		List<PostArticleModel> posts;
		
		try {
			int entries = Integer.parseInt(entriesAmount);
			int page = 1;
			if (pageNum != null){
				page = Integer.parseInt(pageNum);
			}
			
			posts = postService.getAllPosts(entries > 0 ? entries : 1, page > 0 ? page : 1);
			return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<PostArticleModel>());
		} catch(Exception e) {
			logger.error("RESTPostController - getAllPostForAuthor() " + e.toString());
		}
		
		posts = postService.getAllPosts();

		if(posts != null)
			return ResponseEntity.ok(posts);
		else
			return ResponseEntity.ok(new ArrayList<PostArticleModel>());
	}
	
	@GetMapping("articles/author")
	@Log
	public ResponseEntity<List<PostArticleModel>> getAllPostForAuthor(
			@RequestParam(name = "authorId", required = true) String authorId,
			@RequestParam(name = "entries", required = false) String entriesAmount,
			@RequestParam(name = "page", required = false) String pageNum){
		
		List<PostArticleModel> posts;

		try {
			int entries = Integer.parseInt(entriesAmount);
			int page = 1;
			if(pageNum != null) {
				page = Integer.parseInt(pageNum);
			}
			
			posts = postService.getAllPostsForUserWithId(authorId, entries > 0 ? entries : 1, page > 0 ? page : 1);
			return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<PostArticleModel>());
		} catch(Exception e) {
			logger.error("RESTPostController - getAllPostForAuthor() " + e.toString());
		}
		

		posts = postService.getAllPostsForUserWithId(authorId);
		
		if(posts != null)
			return ResponseEntity.ok(posts);
		else
			return ResponseEntity.ok(new ArrayList<PostArticleModel>());
	}
	
	
	@GetMapping("{articleId}")
	@Log
	public ResponseEntity<PostModel> getPost(@PathVariable String articleId){
		PostModel p = postService.getPostByID(articleId);

		if(p != null)
			return ResponseEntity.ok(p);
		else
			return ResponseEntity.ok(new PostModel());
	} 

	
	
	//?? Needs to be authenticated to work
	@DeleteMapping("{articleId}")
	public boolean deletePost(@PathVariable String articleId){
		boolean result = postService.deletePost(articleId);
		return result;
	} 



}
