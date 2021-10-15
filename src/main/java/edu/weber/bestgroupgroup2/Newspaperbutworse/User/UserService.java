package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static UserService INSTANCE;
	//Should be Autowired
    private UserRepository userRepository;
    
    public UserService() {
    	
    }
    
    public User registerNewUserAccount(User user) {
        return user;
        // the rest of the registration operation
    }
    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
