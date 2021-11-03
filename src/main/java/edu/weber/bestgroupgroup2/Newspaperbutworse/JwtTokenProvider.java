package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;
import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenProvider {

	//Time To Live (Currently set to be 1 hour; can be changed)
	private final Duration ttl = Duration.ofHours(1);
	//Might want to look at/rework the secretKey?
	private final byte[] secretKey = "alterego".getBytes();
	private UserService userService;
	
	@Autowired
	public JwtTokenProvider(UserService userService) { this.userService = userService; }
	
	public String createCookieTokenString(Authentication auth) {
		
		Date now = new Date();
		Date expiration = new Date(now.getTime() + ttl.toMillis());
		String jwt = createToken(auth, expiration);
		return AppConstants.JWT_COOKIE_NAME + "=" + jwt + ";Max-Age=" + ttl.toSeconds();
	}
	
	public String createToken(Authentication auth, Date expiration) {
		
		if(auth != null
				&& auth.isAuthenticated()
				&& auth.getPrincipal() instanceof User) {
			User user = (User)auth.getPrincipal();
			return createToken(user, expiration);
		}
		return null;
	}
	
	public String createToken(User user, Date expiration) {
		
		if(user != null) {
			Claims claims = Jwts.claims().setSubject(String.valueOf(user.getUserId()));
			claims.put("perms", user.getPermissions());	//TODO:Implement this method in user
			claims.put("username", user.getUsername());
			claims.put("firstName", user.getFirstName());
			claims.put("lastname", user.getLastName());
			
			Date now = new Date();
			
			Date validity = expiration;
			
			if(validity == null) {
				validity = new Date(now.getTime() + ttl.toMillis());
			}
			
			return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(validity)
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
		}
		
		return null;
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		
		String token = getJwtTokenFromRequest(request);
		if (token != null && validateToken(token)) {
			return getAuthentication(token);
		}
		return null;
	}

	private Authentication getAuthentication(String token) {
		
		String username = getUsername(token);
		if(username != null) {
			UserDetails userDetails = userService.loadUserByUsername(username);
			if(userDetails != null && userDetails.getUsername() != null) {
				return new UsernamePasswordAuthenticationToken(userDetails, "");
			}
		}
		return null;
	}

	private String getUsername(String token) {
		
		Jwt<?, ?> jwt = Jwts.parser().parse(token);
		return jwt.getHeader().get("username").toString();
	}

	private boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			System.out.println("Token was unable to be validated");
			//throw new Run()? Optional
		}
		return false;
	}

	private String getJwtTokenFromRequest(HttpServletRequest request) {
		
		return Arrays.stream(request.getCookies())
				.filter(c -> c.getName().equals(AppConstants.JWT_COOKIE_NAME))
				.findFirst()
				.get()
				.getValue();
	}
}
