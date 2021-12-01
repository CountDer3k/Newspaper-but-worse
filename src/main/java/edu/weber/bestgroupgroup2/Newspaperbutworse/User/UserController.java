package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Post.PostArticleModel;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Controller
public class UserController {
	
	UserService userService;
	private Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/* Registration */
	@GetMapping("/user/registration")
	@Log
	public ModelAndView showRegistrationForm() {
		ModelAndView modelAndView = new ModelAndView("user/registration");
		modelAndView.getModelMap().addAttribute("user", new UserDto());
	    return modelAndView;
	}
	
	@PostMapping("/user/registration")
	@Log
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") @Validated UserDto userDto,
	  BindingResult bindResult,
	  HttpServletRequest request,
	  Errors errors) {
		
		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		
	    if(userService.isValidUser(userDto)) {
	    	ModelAndView modelAndView = new ModelAndView("redirect:/");
	    	userService.registerNewUserAccount(userDto);
	    	modelAndView.getModelMap().addAttribute("msg", "Registration Confirmed!");
	    	modelAndView.getModelMap().addAttribute("user", userDto);
	    	return modelAndView;
	    }
	    
	    return new ModelAndView("user/registration", "msg", "Registration Failed!");
	}
	
	/* Login */
	@GetMapping("/user/login")
	@Log
	public ModelAndView showLoginForm() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.getModelMap().addAttribute("user", new UserDto());
		return modelAndView;
	}
	
	/* List */
	@GetMapping("user/list")
	@Log
	public ModelAndView showUserList() {
		ModelAndView modelAndView = new ModelAndView("user/userList");
		
		try {
			List<User> users = new ArrayList<User>();
			users = userService.getAllUsers();
			
			// If no posts are available show the empty content div
			if(users.size() == 0 || users.equals(null)) {
				modelAndView.getModelMap().addAttribute("isEmpty", true);
				modelAndView.getModelMap().addAttribute("empty", "No content available to display...");
			}
			else {
				modelAndView.getModelMap().addAttribute("isEmpty", false);
				modelAndView.getModelMap().addAttribute("users", users);
			}
		}
		catch(Exception e) {
			logger.error("" + e.toString());
			return null;
		}
	
		return modelAndView;
	}
	
	/* User */
	@GetMapping("user/{username}")
	@Log
	public ModelAndView showUser(@PathVariable String username) {
		ModelAndView modelAndView = new ModelAndView("user/user");
		try {
			// Get user from db		
			User user = (User) userService.loadUserByUsername(username);
			modelAndView.getModelMap().addAttribute("user", user);
		} 
		catch(Exception e) {
				logger.error(e.toString());
		}
		
		return modelAndView;
	}
	
	/* User Form */
	@GetMapping("user/edit/{username}")
	@Log
	public ModelAndView showUserForm(@PathVariable String username) {
		ModelAndView modelAndView = new ModelAndView("user/userForm");
		try {
			User user = (User) userService.loadUserByUsername(username);
			modelAndView.getModelMap().addAttribute("user", user);
		} 
		catch(Exception e) {
				logger.error(e.toString());
		}
	    return modelAndView;
	}
	
	@PostMapping("user/edit/{username}")
	@Log
	public ModelAndView editUser(
			  @ModelAttribute("user") @Validated User user,
			  BindingResult bindResult,
			  HttpServletRequest request,
			  Errors errors,
			  @PathVariable String username) {
		
		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}
		
    	ModelAndView modelAndView = new ModelAndView("redirect:user/list");
    	userService.editUser(user);
    	modelAndView.getModelMap().addAttribute("msg", "Registration Confirmed!");
    	modelAndView.getModelMap().addAttribute("user", user);
    	return modelAndView;
	}
	

}
