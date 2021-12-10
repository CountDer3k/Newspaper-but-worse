package edu.weber.bestgroupgroup2.Newspaperbutworse;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
	
	public JwtFilter(JwtTokenProvider jwtTokenProvider) { this.jwtTokenProvider = jwtTokenProvider; }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		String s = request.getHeader("Authorization");
//		System.out.println(s);
		SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(request));
		filterChain.doFilter(request, response);
		
	}
}
