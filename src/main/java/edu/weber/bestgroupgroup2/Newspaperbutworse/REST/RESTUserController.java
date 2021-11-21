package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
