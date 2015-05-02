package com.lsdt.LittleSproutsScheduler.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lsdt.LittleSproutsScheduler.model.User;
import com.lsdt.LittleSproutsScheduler.model.UserLogin;
import com.lsdt.LittleSproutsScheduler.service.UserService;

@Controller
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// ------------------------------------------------------------ Sign up --------------------------------------------------
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @RequestParam String signup, @RequestParam String password, @RequestParam String passwordCheck) { 
		
		if(signup.equals("cancel")){
			return "redirect:/";
		}else{
			if(result.hasErrors()) {
				return "signup";
			} else if(userService.findByUserName(user.getUsername())) {
				model.addAttribute("message", "User Name exists. Try another user name");
				return "signup";
			} else if(!password.equals(passwordCheck)){
				model.addAttribute("message", "Passwords do not match.");
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
	}
	
	// ------------------------------------------------------------ login--------------------------------------------------

	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	  public String login(Model model) {          
	      UserLogin userLogin = new UserLogin();     
	      model.addAttribute("userLogin", userLogin);
	      return "login";
	  }

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid @ModelAttribute("userLogin") UserLogin userLogin, BindingResult result, Model model, @RequestParam String login) {
		if(login.equals("cancel")){
			return "redirect:/";
		}else{
			if (result.hasErrors()) {
				return "login";
			} else {
				boolean found = userService.findByLogin(userLogin.getUserName(), userLogin.getPassword());
				if (found) {  
					User user = userService.findAndGetAttributes(userLogin.getUserName());
					model.addAttribute("User", user.getUsername());
					if(user.getType() == 'M')
						return "redirect:mdashboard.html";
					else if(user.getType() == 'T')
						return "redirect:tdashboard.html";
					else
						return "redirect:pdashboard.html";
				}else {                
					return "failure";
				}
			}
		}
	}
	
	// ------------------------------------------------------------ Moderator --------------------------------------------------

	@RequestMapping(value="/mdashboard", method=RequestMethod.GET)
	public String mdashboard(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "mdashboard";
	}
	
	@RequestMapping(value="/mrequests", method=RequestMethod.GET)
	public String mrequests(Model model) {

		return "mrequests";
	}
	
	@RequestMapping(value="/maccounts.html", method=RequestMethod.GET)
	public String maccounts(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "maccounts";
	}
	
	@RequestMapping(value = "/springPaginationDataTables.html", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTables(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<User> usersList = createPaginationData(pageDisplayLength);
		
		//Here is server side pagination logic. Based on the page number you could make call 
		//to the data base create new list and send back to the client. For demo I am shuffling 
		//the same list to show data randomly
		//if (pageNumber == 1) {
			//Collections.shuffle(personsList);
		//}else if (pageNumber == 2) {
			//Collections.shuffle(personsList);
		//}else {
			//Collections.shuffle(personsList);
		//}
		
		//Search functionality: Returns filtered list based on search parameter
		usersList = getListBasedOnSearchParameter(searchParameter,usersList);
		
		
		UserJsonObject userJsonObject = new UserJsonObject();
		//Set Total display record
		userJsonObject.setiTotalDisplayRecords(500);
		//Set Total record
		userJsonObject.setiTotalRecords(500);
		userJsonObject.setAaData(usersList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(userJsonObject);
	
		return json2;
    }
	
	private List<User> getListBasedOnSearchParameter(String searchParameter,List<User> usersList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<User> usersListForSearch = new ArrayList<User>();
			searchParameter = searchParameter.toUpperCase();
			for (User user : usersList) {
				if (user.getUsername().toUpperCase().indexOf(searchParameter)!= -1 || user.getEmail().toUpperCase().indexOf(searchParameter)!= -1
						|| user.getName_first().toUpperCase().indexOf(searchParameter)!= -1 || user.getName_last().toUpperCase().indexOf(searchParameter)!= -1
						|| user.getPhone().toUpperCase().indexOf(searchParameter)!= -1) {
					usersListForSearch.add(user);					
				}
				
			}
			usersList = usersListForSearch;
			usersListForSearch = null;
		}
		return usersList;
	}
	
	private List<User> createPaginationData(Integer pageDisplayLength) {
		List<User> usersList = new ArrayList<User>();
		//usersList = userService.getUsers();
		for(int i =0; i < pageDisplayLength; i++) {
		User person2 = new User();
        person2.setName_first("bob");
        person2.setEmail("mail@mail");
        person2.setName_last("last name :)");
        person2.setType('T');
        person2.setPhone("999999999");

        usersList.add(person2); }
		return usersList;
	}
	
	// ------------------------------------------------------------ Teacher --------------------------------------------------
	
	@RequestMapping(value="/tdashboard", method=RequestMethod.GET)
	public String tdashboard(Model model) {
		
		return "tdashboard";
	}
	
	@RequestMapping(value="/tavailability", method=RequestMethod.GET)
	public String taccounts(Model model) {
		
		return "tavailability";
	}
	
	@RequestMapping(value="/trequests", method=RequestMethod.GET)
	public String trequests(Model model) {

		return "trequests";
	}


	// ------------------------------------------------------------ Parent --------------------------------------------------

	@RequestMapping(value="/pdashboard", method=RequestMethod.GET)
	public String pdashboard(Model model) {
		
		return "pdashboard";
	}
	
	@RequestMapping(value="/prequests", method=RequestMethod.GET)
	public String prequests(Model model) {
		
		return "prequests";
	}
	
	
	// ------------------------------------------------------------ Common Dashboard --------------------------------------------------

	@RequestMapping(value= {"/mdashboard", "/tdashboard", "/pdashboard"}, method=RequestMethod.POST)
	public String logout(Model model, @RequestParam String action) {
			return "redirect:/";
	}
}


