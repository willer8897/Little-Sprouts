package com.lsdt.LittleSproutsScheduler.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lsdt.LittleSproutsScheduler.model.User;
import com.lsdt.LittleSproutsScheduler.model.UserLogin;
import com.lsdt.LittleSproutsScheduler.service.UserService;

@Controller
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) { 
		if(result.hasErrors()) {
			return "signup";
		} else if(userService.findByUserName(user.getUsername())) {
			model.addAttribute("message", "User Name exists. Try another user name");
			return "signup";
		} else {
			userService.save(user);
			model.addAttribute("message", "Saved user details");
			if(user.getType() == 'M')
				return "redirect:mdashboard.html";
			else if(user.getType() == 'T')
				return "redirect:tdashboard.html";
			else
				return "redirect:pdashboard.html";
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	  public String login(Model model) {          
	      UserLogin userLogin = new UserLogin();     
	      model.addAttribute("userLogin", userLogin);
	      return "login";
	  }

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid @ModelAttribute("userLogin") UserLogin userLogin, BindingResult result) {
		if (result.hasErrors()) {
			return "login";
		} else {
			boolean found = userService.findByLogin(userLogin.getUserName(), userLogin.getPassword());
			if (found) {  
				User user = new User();
				user = userService.findAndGetAttributes(userLogin.getUserName());
				if(user.getType() == 'M')
					return "mdashboard";
				else if(user.getType() == 'T')
					return "tdashboard";
				else
					return "pdashboard";
			}else {                
				return "failure";
			}
		}

	}
	
}
