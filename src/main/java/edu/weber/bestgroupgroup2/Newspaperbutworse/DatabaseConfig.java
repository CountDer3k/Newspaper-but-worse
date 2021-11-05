package edu.weber.bestgroupgroup2.Newspaperbutworse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.UserService;

@Configuration
public class DatabaseConfig{
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	private final ApplicationContext applicationContext;
	
	@Autowired
	public DatabaseConfig(NamedParameterJdbcTemplate jdbcTemplate, 
			ApplicationContext applicationContext) {
		this.jdbcTemplate = jdbcTemplate;
		this.applicationContext = applicationContext;
	}
	
	
	
}
