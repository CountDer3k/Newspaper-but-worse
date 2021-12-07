package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class NewsAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private JwtTokenProvider provider;
	
	@Autowired
	public NewsAuthenticationSuccessHandler(JwtTokenProvider provider) {this.provider = provider;}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Cookie userCookie = provider.createCookieTokenString(authentication);
		response.addCookie(userCookie);
		response.sendRedirect("/");
		
	}

}
