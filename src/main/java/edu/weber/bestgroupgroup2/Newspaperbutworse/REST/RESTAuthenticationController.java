package edu.weber.bestgroupgroup2.Newspaperbutworse.REST;



import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.weber.bestgroupgroup2.Newspaperbutworse.JwtTokenProvider;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@RestController
@RequestMapping("API/")
public class RESTAuthenticationController {

	private Logger logger = LoggerFactory.getLogger(RESTAuthenticationController.class);
	private JwtTokenProvider jot;
	
	@Autowired
	public RESTAuthenticationController(JwtTokenProvider jot) {
		this.jot = jot;
	}
	
	@GetMapping("tokens")
	@Log
	public ResponseEntity<String> createNewToken(@RequestHeader("Authorization") String authHeader ) throws UnsupportedEncodingException{

		String authentication = authHeader.split(" ")[1];
		String decodedString = new String(Base64.getDecoder().decode(authentication.getBytes("UTF-8")));
		String[] userPassword = decodedString.split(":");
		String username = userPassword[0];
		String password = userPassword[1];
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		
		final Duration ttl = Duration.ofMinutes(30);
		Date now = new Date();
		Date expiration = new Date(now.getTime() + ttl.toMillis());
		
		String token = jot.createToken(user, expiration);
		
		
		return ResponseEntity.ok(token);
	}
}
