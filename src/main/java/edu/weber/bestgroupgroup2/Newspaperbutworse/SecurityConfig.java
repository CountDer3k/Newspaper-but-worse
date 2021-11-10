package edu.weber.bestgroupgroup2.Newspaperbutworse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//private NamedParameterJdbcTemplate jdbcTemplate;
	private final ApplicationContext applicationContext;
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public SecurityConfig(ApplicationContext applicationContext, @Lazy JwtTokenProvider jwtTokenProvider) {
      this.applicationContext = applicationContext;
      this.jwtTokenProvider = jwtTokenProvider;
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
	      		.antMatchers("/articles/**").permitAll()
	      		.antMatchers("/").permitAll()
	      		.antMatchers("/random").permitAll()
	      		//More?
//	      .antMatchers("/**").permitAll()
	      		.anyRequest().authenticated()
	      	.and()
	      		.exceptionHandling().accessDeniedPage("/user/login")
	    	.and()
	    		.formLogin()
	    		.usernameParameter("username")
	    		.passwordParameter("password")
	    		.loginPage("/user/login")
	    		.loginProcessingUrl("/random")
	    		.defaultSuccessUrl("/")
	    		.successHandler(successHandler());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").anyRequest();
	}

	@Bean//(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
