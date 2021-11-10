package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/* Registration */
	@GetMapping("/user/registration")
	public ModelAndView showRegistrationForm() {
		ModelAndView modelAndView = new ModelAndView("user/registration");
		modelAndView.getModelMap().addAttribute("user", new UserDto());
	    return modelAndView;
	}
	
	@PostMapping("/user/registration")
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
	public ModelAndView showLoginForm() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.getModelMap().addAttribute("user", new UserDto());
		return modelAndView;
	}
}
