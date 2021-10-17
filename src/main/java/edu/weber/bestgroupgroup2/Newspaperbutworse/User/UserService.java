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
    
    public boolean isValidUser(User user) {
    	
    	//checks empty strings since these can't be null because of annotations in User
    	if(user.getUsername().equals("") || user.getEmail().equals("") || user.getPassword().equals("") ||
    			!user.getPassword().equals(user.getMatchingPassword()))
    		return false;
    	
    	return true;
    }
    
    public User registerNewUserAccount(UserDto userDto) {
      //TODO: the rest of the registration operation
    	User user = new User();
      return user;
    }
    
    private boolean emailExist(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

}
