package edu.weber.bestgroupgroup2.Newspaperbutworse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@Value("${spring.application.name}")
    String name;
	
	@GetMapping("/")
	public String home(Model model){
		model.addAttribute("name", name);
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}

}
