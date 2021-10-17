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
	public ModelAndView showRegistrationForm(WebRequest request, Model model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    return new ModelAndView("user/registration");
	}
	
	@PostMapping("/user/registration")
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") UserDto userDto,
	  HttpServletRequest request,
	  Errors errors) {
	    
	    User registered = userService.registerNewUserAccount(userDto);
	
	    return new ModelAndView("/", "user", userDto);
	}

}
