package edu.weber.bestgroupgroup2.Newspaperbutworse.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	//Should be Autowired
    private UserRepository userRepository;
    
    public User registerNewUserAccount(UserDto userDto) {
    	User user = new User();
        return user;
        // the rest of the registration operation
    }
    private boolean emailExist(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

}
