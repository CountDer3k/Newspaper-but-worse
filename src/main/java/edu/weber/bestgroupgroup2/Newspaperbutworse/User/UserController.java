package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.Logger;
import edu.weber.bestgroupgroup2.Newspaperbutworse.HomeController;

@Controller
public class UserController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
			//(Logger) LoggerFactory.getILoggerFactory();
			//LoggerFactory.getLogger(Example.class);
	
	UserService userService;

	@GetMapping("/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		logger.info("Example Log");
		
	    logger.info("Example log from {}", UserController.class.getSimpleName());
		
		
		return "user/registration";
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
