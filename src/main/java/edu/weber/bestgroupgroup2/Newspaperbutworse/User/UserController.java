package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	UserService userService;
	
	@GetMapping("/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
	    User user = new User();
	    model.addAttribute("user", user);
	    return "registration";
	}
	
	@PostMapping("/user/registration")
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") User user,
	  HttpServletRequest request,
	  Errors errors) {
	    
	    User registered = userService.registerNewUserAccount(user);
	
	    return new ModelAndView("successRegister", "user", user);
	}

}
