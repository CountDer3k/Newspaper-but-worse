package edu.weber.bestgroupgroup2.Newspaperbutworse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;
import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//private NamedParameterJdbcTemplate jdbcTemplate;
	private final ApplicationContext applicationContext;
	private JwtTokenProvider jwtTokenProvider;
	//private UserService userService;
	
	@Autowired
	public SecurityConfig(ApplicationContext applicationContext, @Lazy JwtTokenProvider jwtTokenProvider) {
//			,@Lazy UserService userService) {
      this.applicationContext = applicationContext;
      this.jwtTokenProvider = jwtTokenProvider;
//      this.userService = userService;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return applicationContext.getBean(UserService.class);
	}
	
	@Bean
	@Log
	public AuthenticationSuccessHandler successHandler(){
	    return new NewsAuthenticationSuccessHandler(jwtTokenProvider);
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
	    http
	    	.csrf().disable()
	      	.sessionManagement()
	      	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      	.and()
	      		.addFilterBefore(new JwtFilter(applicationContext.getBean(JwtTokenProvider.class)), UsernamePasswordAuthenticationFilter.class)
	      		.authorizeRequests()
	      		.antMatchers("/user/login").permitAll()
	      		.antMatchers("/user/registration").permitAll()
	      		.antMatchers(HttpMethod.GET, "/articles/**").permitAll()
//	      		.antMatchers(HttpMethod.POST, "/articles/**").authenticated()
	      		//?? Delete this after testing
	      		.antMatchers("/API/**").permitAll()
	      		.antMatchers("/authors/**").hasAnyAuthority("AUTHOR")
	      		.antMatchers("/").permitAll()
	      		.antMatchers("/error").permitAll()
	      		.antMatchers("/user/list").hasAuthority("ADMIN")
	      		.antMatchers("/user/edit/**").hasAuthority("ADMIN")
//	      		.antMatchers("/random").permitAll()
	      		//More?
//	      .antMatchers("/**").permitAll()
	      		.anyRequest().authenticated()
	      	.and()
	      		.exceptionHandling().accessDeniedPage("/user/login")
	    	.and()
	    		.logout()
	    		.logoutUrl("/user/logout")
	    		.logoutSuccessUrl("/")
	    		.invalidateHttpSession(true)
	    		.deleteCookies("sessionId")
	    	.and()
	    		.formLogin()
	    		.usernameParameter("username")
	    		.passwordParameter("password")
	    		.loginPage("/user/login")
	    		.loginProcessingUrl("/user/login")
	    		.defaultSuccessUrl("/")
	    		.successHandler(successHandler());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
	}
	
//	@Override
//	@Log
//	public void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth
//			.inMemoryAuthentication()
//			.withUser("reader").password(encoder().encode("password")).roles("READER")
//			.and()
//			.withUser("author").password(encoder().encode("author")).roles("UTHOR")
//			.and()
//			.withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
//	}

	@Bean//(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
