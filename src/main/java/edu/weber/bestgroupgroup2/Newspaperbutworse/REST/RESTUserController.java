package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@RestController
@RequestMapping("API/")
public class RESTUserController {
	
	private Logger logger = LoggerFactory.getLogger(RESTUserController.class);
	
	private UserService userService;
	
	@Autowired
	public RESTUserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("users")
	@Log
	public ResponseEntity<List<User>> getAllUsers(
			@RequestParam(name = "entries", required = false) String entriesAmount,
			@RequestParam(name = "page", required = false) String pageNum){
		List<User> users;
		
		try {
			int entries = Integer.parseInt(entriesAmount);
			int page = 1;
			if (pageNum != null){
				page = Integer.parseInt(pageNum);
			}
			
			users = userService.getAllUsers(entries > 0 ? entries : 1, page > 0 ? page : 1);
		} catch(Exception e) {
			logger.error("RESTPostController - getAllPostForAuthor() " + e.toString());
			users = userService.getAllUsers();
		}
		return users != null ? ResponseEntity.ok(users) : ResponseEntity.ok(new ArrayList<User>());
	}
	
	@GetMapping("users/{userId}")
	@Log
	public ResponseEntity<Object> getPost(@PathVariable String userId){
		User u = userService.getUserByID(Integer.parseInt(userId));

		if(u != null)
			return ResponseEntity.ok(u);
		else {
			Map<String,String> m = new HashMap<String, String>();
			m.put("Error", "No User Found");
			return ResponseEntity.ok(m);
		}
	}
	
}
