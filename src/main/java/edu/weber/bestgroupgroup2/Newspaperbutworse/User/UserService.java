package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
    	this.userRepository = userRepo;
    	this.passwordEncoder = passwordEncoder;
    }
    
    @Log
    public boolean isValidUser(UserDto userDto) {
    	
    	//checks empty strings since these can't be null because of annotations in User
    	if(userDto.getUsername().equals("") || userDto.getEmail().equals("") || userDto.getPassword().equals("") ||
    			!userDto.getPassword().equals(userDto.getMatchingPassword()))
    		return false;
    	
    	return true;
    }
    
    @Log
    public User getUserByID(int id) {
    	User user = new User();
    	
    	user = userRepository.getUserByID(id);
    	
    	return user;
    }
    
    @Log
    public User registerNewUserAccount(UserDto userDto) {
    	
    	User user = new User();
    	user.setUsername(userDto.getUsername());
    	user.setFirstName(userDto.getFirstName());
    	user.setLastName(userDto.getLastName());
    	user.setEmail(userDto.getEmail());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	user.setRoles(userDto.getRoles());
    	
    	return userRepository.save(user);
    }
    

	@Override
	@Log
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
		
		return user;
	}

	@Log
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		userList = userRepository.getAllUsers();
		return userList;
	}

	public User editUser(User user) {
		User oldUser = (User) loadUserByUsername(user.getUsername());
		user.setUserId(oldUser.getUserId());
		return userRepository.updateUser(user);
		
	}

}
