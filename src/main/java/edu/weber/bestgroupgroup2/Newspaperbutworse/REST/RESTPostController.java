package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.ArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostRepository;
import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostService;

@RestController
@RequestMapping("post")
public class RESTPostController {

	//?? Testing
	private Logger logger = LoggerFactory.getLogger(RESTPostController.class);
	
	private PostService postService;
	
	@Autowired
	public RESTPostController(PostService postService) {
		this.postService = postService;
	} 
	
	
	@GetMapping("article/{articleId}")
	public ResponseEntity<PostModel> getPost(@PathVariable String articleId){
		PostModel p = postService.getPostByID(articleId);
		
		if(p != null)
			return ResponseEntity.ok(p);
		else
			return ResponseEntity.ok(new PostModel());
			//return (ResponseEntity<PostModel>) ResponseEntity.notFound();
	} 
	
	
	
}
