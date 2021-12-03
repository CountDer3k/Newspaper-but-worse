package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("API")
public class RESTPostController {

	//?? Testing
	private Logger logger = LoggerFactory.getLogger(RESTPostController.class);

	private PostService postService;

	@Autowired
	public RESTPostController(PostService postService) {
		this.postService = postService;
	} 


	@Operation(summary = "Returns a list of all the articles.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Articles Found", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = PostModel.class)) }),
			@ApiResponse(responseCode = "404", description = "Articles not found", 
		    content = @Content)
	})
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
		} catch(Exception e) {
			logger.error("RESTPostController - getAllPostForAuthor() " + e.toString());
			posts = postService.getAllPosts();
		}
		return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<PostArticleModel>());
	}

	@Operation(summary = "Returns an article for the author id passed in.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Articles Found", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = PostModel.class)) }),
			@ApiResponse(responseCode = "404", description = "Articles not found", 
		    content = @Content)
	})
	@GetMapping("author/articles")
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
			//return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<PostArticleModel>());
		} catch(Exception e) {
			logger.error("RESTPostController - getAllPostForAuthor() " + e.toString());
			posts = postService.getAllPostsForUserWithId(authorId);
		}
		return posts != null ? ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<PostArticleModel>());
	}
	
	//?? Needs to be authenticated to work
	@DeleteMapping("articles/{articleId}")
	@Operation(summary = "Returns true/false based on if the article with the article id passed in was delete successfully.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Article Deleted", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = PostModel.class)) }),
			@ApiResponse(responseCode = "404", description = "Article not deleted", 
		    content = @Content)
	})
	public ResponseEntity<Boolean> deletePost(@PathVariable String articleId){
		boolean result = postService.deletePost(articleId);
		return ResponseEntity.ok(result);
		//return result;
	} 

	@Operation(summary = "Returns an article for the article id passed in.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Article Found", 
					content = { @Content(mediaType = "application/json", 
					schema = @Schema(implementation = PostModel.class)) }),
			@ApiResponse(responseCode = "404", description = "Article not found", 
		    content = @Content)
	})
	@GetMapping("articles/{articleId}")
	@Log
	public ResponseEntity<?> getPost(@PathVariable String articleId){
		PostModel p = postService.getPostByID(articleId);

		if(p != null)
			return ResponseEntity.ok(p);
		else {
			Map<String,String> m = new HashMap<String, String>();
			m.put("Error", "No Article Found");
			return ResponseEntity.ok(m);
		}
	} 




}
