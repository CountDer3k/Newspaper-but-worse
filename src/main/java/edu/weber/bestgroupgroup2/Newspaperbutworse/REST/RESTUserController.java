package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.Role;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserDto;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("API/users")
public class RESTUserController {

	private Logger logger = LoggerFactory.getLogger(RESTUserController.class);

	private UserService userService;

	@Autowired
	public RESTUserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get all users")
	@GetMapping()
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

	@Operation(summary = "Get a articles by its id")
	@GetMapping("{userId}")
	@Log
	public ResponseEntity<Object> getUser(@PathVariable String userId){
		User u = userService.getUserByID(Integer.parseInt(userId));

		if(u != null)
			return ResponseEntity.ok(u);
		else {
			Map<String,String> m = new HashMap<String, String>();
			m.put("Error", "No User Found"); 
			return ResponseEntity.ok(m);
		}
	}

	@Operation(summary = "Add a user \nTakes in arguments in the body in the following order: \nusername, password, firstname, lastname")
	@PostMapping()
	@Log
	public ResponseEntity<Object> loginUser(
			@Validated
			@RequestBody UserDto user){

		User newUser = userService.registerNewUserAccount(user);


		if (newUser != null && newUser.getUserId() != 0) {
			return ResponseEntity.ok(newUser);
		}
		else {
			return ResponseEntity.badRequest().body("Failed to add new user");
		}
	}
}
