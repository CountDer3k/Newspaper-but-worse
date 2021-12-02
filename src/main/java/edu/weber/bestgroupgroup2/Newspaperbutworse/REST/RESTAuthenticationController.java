package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@RestController
@RequestMapping("API/")
public class RESTAuthenticationController {

	
	@GetMapping("tokens")
	@Log
	public void createNewToken() {
		
	}
}
