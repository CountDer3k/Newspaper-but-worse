package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    return "user/registration";
	}
	
	@PostMapping("/user/registration")
	public String registerUserAccount(
	  @Validated @ModelAttribute("user") UserDto userDto,
	  BindingResult bindResult,
	  HttpServletRequest request,
	  Model model,
	  Errors errors) {
		if(bindResult.hasErrors()) {
			return "error";
		}
		
	    if(userService.isValidUser(userDto)) {
	    	User registered = userService.registerNewUserAccount(userDto);
	    	//model.addAttribute("msg", "Registration Confirmed!");
        	model.addAttribute("user", userDto);
	    	return "/";
	    }
	    
	    model.addAttribute("msg", "Registration Failed!");
	    return "user/userFailed";
	}

}
