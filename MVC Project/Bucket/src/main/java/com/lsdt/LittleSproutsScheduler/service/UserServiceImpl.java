package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsdt.LittleSproutsScheduler.model.User;
import com.lsdt.LittleSproutsScheduler.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	//Heather's code
	public void delete(User user){
		 this.userRepository.delete(user);

	}

	public boolean findByLogin(String username, String password) {
		User stud = userRepository.findByUserName(username);

	      if(stud != null && stud.getPassword().equals(password)) {
	          return true;
	      } 

	      return false;    
	}

	public boolean findByUserName(String userName) {
		 User stud = userRepository.findByUserName(userName);

	     if(stud != null) {
	    	 return true;
	     }

	     return false;
	}

	public User findAndGetAttributes(String username) {
		User stud = userRepository.findByUserName(username);
		return stud;
	}

	public List<User> getUsers() {
		List<User> stud = userRepository.getAllUsers();
		return stud;
	}


}
