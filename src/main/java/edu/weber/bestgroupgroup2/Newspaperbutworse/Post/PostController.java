package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;


@Controller 
public class PostController {

	PostService postService;
	private Logger logger = LoggerFactory.getLogger(PostRepository.class);

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("articles/articleNum/{articleId}")
	@Log
	public ModelAndView showArticleView(@PathVariable String articleId) {
		
		ModelAndView modelAndView = new ModelAndView("article/article");
		try {
		// Get article from db		
		PostArticleModel pam = postService.getPostWithAuthorByID(articleId);
    modelAndView.getModelMap().addAttribute("post", pam.getPost());
		modelAndView.getModelMap().addAttribute("author", pam.getName());
		modelAndView.getModelMap().addAttribute("article", pam.getPost().getArticle());
		modelAndView.getModelMap().addAttribute("articleId", articleId);
		
		// Get comments from db
		List<Comment> comments = new ArrayList<Comment>();
		comments = postService.getCommentsFromArticle(Integer.parseInt(articleId));
		
		CommentDto commentDto = new CommentDto();
		commentDto.setParentId(Integer.parseInt(articleId));
		modelAndView.getModelMap().addAttribute("comment", commentDto);
		modelAndView.getModelMap().addAttribute("comment.parentId", articleId);
		modelAndView.getModelMap().addAttribute("comments", comments);
		
		return modelAndView;
		} catch(Exception e) {
			logger.error(e.toString());
			return new ModelAndView("/error");
		}
	}
	
	@PostMapping("/articles/addComment")
	@Log
	public ModelAndView addComment(
			@Validated @ModelAttribute("comment") CommentDto commentDto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors,
			Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		
		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		ModelAndView modelAndView;
		
		//TODO: Validity of comments
	    if(true) {
	    	//Currently redirects to root instead of same article page, not passing articleId and idk yet how
	    	modelAndView = new ModelAndView("redirect:/articles/articleNum/"+commentDto.getParentId());
	    	Comment addedComment = postService.addNewComment(commentDto, user.getUserId());
        	//modelAndView.getModelMap().addAttribute("comment", commentDto);
	    	//return modelAndView;
	    }

	    return modelAndView;
	}
	
	@GetMapping("/articles/articleForm")
	@Log
	public ModelAndView showRegistrationForm(WebRequest request) {
		PostDto postDto = new PostDto();
		ModelAndView modelAndView = new ModelAndView("article/articleForm");
		modelAndView.getModelMap().addAttribute("post", postDto);
		return modelAndView;
	}

	@GetMapping("articles/editArticle")
	@Log
	public ModelAndView showEditableArticleDirect(@PathVariable String articleId) {
		ModelAndView modelAndView = new ModelAndView("article/article_edit");

		return modelAndView;
	}


	@PostMapping("/articles/articleForm")
	@Log
	public ModelAndView registerUserArticle(
			@Validated PostDto postDto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {

		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		//?? Get this from logged in user
		int userID = 1;

		if(postService.isValidPost(postDto)) {
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			PostModel addedPost = postService.addNewPost(postDto, userID);
			modelAndView.getModelMap().addAttribute("post", postDto);
			return modelAndView;
		}
		//?? WTF is this doing here??
		return new ModelAndView("user/registration", "msg", "Registration Failed!");
	}


	@GetMapping("authors/articleList")
	@Log
	public ModelAndView showAuthorArticles(Principal prin) {

		try {
//			User author = (User)prin;
//			String authorId = "";
//			if(author == null) {
//				logger.info("Author is still null....");
//				authorId = "1";
//			}
//			else {
//				int aId = author.getUserId();
//				authorId =  String.valueOf(aId);
//				logger.info("Author ID: " + authorId);
//			}


			String authorId = "1";
			
			List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
			posts = postService.getAllPostsForUserWithId(authorId);
			ModelAndView modelAndView = new ModelAndView("author/articleList");
			modelAndView.getModelMap().addAttribute("posts", posts);
			return modelAndView;
		} catch(Exception e) {
			logger.error("PostController - showAuthorArticles() " + e.toString());
		}
		return new ModelAndView("/error");
	}

	@GetMapping("articles/articleNum/edit/{articleId}")
	@Log
	public ModelAndView showEditArticleView(@PathVariable String articleId, WebRequest request) {
		ModelAndView modelAndView = new ModelAndView("article/article_edit");
		PostDto postDto = new PostDto();
		try {
			// Get article from db		
			PostArticleModel pam = postService.getPostWithAuthorByID(articleId);

			postDto.setTitle(pam.getPost().getArticle().getTitle());
			postDto.setContent(pam.getPost().getArticle().getContent());
			postDto.setAccess(pam.getPost().getArticle().getAccess());

			modelAndView.getModelMap().addAttribute("postDto", postDto);
			modelAndView.getModelMap().addAttribute("articleId", articleId);

			return modelAndView;
		} catch(Exception e) {
			logger.error(e.toString());
			return new ModelAndView("/error");
		}
	}


	@PostMapping("articles/articleNum/edit/{articleId}")
	@Log
	public ModelAndView updateArticleView(
			@Validated PostDto postDto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors, 
			@PathVariable String articleId) {

		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		//?? Get this from logged in user
		int authorId = 1;

		if(postService.isValidPost(postDto)) {
			// Update articles
			postService.editPost(postDto, Integer.parseInt(articleId));
		}

		// Return list views
		ModelAndView modelAndView = new ModelAndView("author/articleList");
		List<PostArticleModel> posts = new ArrayList<PostArticleModel>();
		posts = postService.getAllPostsForUserWithId(authorId+"");
		modelAndView.getModelMap().addAttribute("posts", posts);
		return modelAndView;
	}


	@GetMapping("/articles/delete/{articleId}")
	@Log
	public String deleteArticle(@PathVariable String articleId) {

		postService.deletePost(articleId);

		return "index";
	}



}
