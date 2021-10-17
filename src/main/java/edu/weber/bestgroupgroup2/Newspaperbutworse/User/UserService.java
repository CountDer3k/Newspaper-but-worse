package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

//	private static UserService INSTANCE;
	
    private UserRepository userRepository;
    

    @Autowired
    public UserService(UserRepository userRepo) {
    	this.userRepository = userRepo;
    }
    
    public boolean isValidUser(UserDto userDto) {
    	
    	//checks empty strings since these can't be null because of annotations in User
    	if(userDto.getUsername().equals("") || userDto.getEmail().equals("") || userDto.getPassword().equals("") ||
    			!userDto.getPassword().equals(userDto.getMatchingPassword()))
    		return false;
    	
    	return true;
    }
    
    public User registerNewUserAccount(UserDto userDto) {
    	if (emailExists(userDto.getEmail())) {
    		// throw an exception
    	}
    	User user = new User();
    	user.setFirstName(userDto.getFirstName());
    	user.setLastName(userDto.getLastName());
    	user.setEmail(userDto.getEmail());
    	user.setPassword(userDto.getPassword());
    	
    	return userRepository.save(user);
    }
    
    private boolean emailExists(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

}
