package com.lsdt.LittleSproutsScheduler.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lsdt.LittleSproutsScheduler.json.AvailabilityJsonObject;
import com.lsdt.LittleSproutsScheduler.json.RequestJsonObject;
import com.lsdt.LittleSproutsScheduler.json.ScheduleJsonObject;
import com.lsdt.LittleSproutsScheduler.json.UserJsonObject;
import com.lsdt.LittleSproutsScheduler.model.Availability;
import com.lsdt.LittleSproutsScheduler.model.Request;
import com.lsdt.LittleSproutsScheduler.model.Schedule;
import com.lsdt.LittleSproutsScheduler.model.User;
import com.lsdt.LittleSproutsScheduler.model.UserLogin;
import com.lsdt.LittleSproutsScheduler.service.AvailabilityService;
import com.lsdt.LittleSproutsScheduler.service.ScheduleService;
import com.lsdt.LittleSproutsScheduler.service.UserService;
import com.lsdt.LittleSproutsScheduler.service.RequestService;

@Controller
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private AvailabilityService availabilityService;
	
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
					model.addAttribute("user", user.getUsername());
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
		
		return "mdashboard";
	}
	
	@RequestMapping(value = "/mdashboard", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String mScheduleDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<Schedule> schedulesList = mCreateSchedulePaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		schedulesList = mGetScheduleListBasedOnSearchParameter(searchParameter,schedulesList);
		
		ScheduleJsonObject scheduleJsonObject = new ScheduleJsonObject();
		
		//Set Total display record
		scheduleJsonObject.setiTotalDisplayRecords(schedulesList.size());
		
		//Set Total record
		scheduleJsonObject.setiTotalRecords(schedulesList.size());
		scheduleJsonObject.setAaData(schedulesList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(scheduleJsonObject);
	
		return json2;
    }
	
	private List<Schedule> mGetScheduleListBasedOnSearchParameter(String searchParameter,List<Schedule> schedulesList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<Schedule> schedulesListForSearch = new ArrayList<Schedule>();
			searchParameter = searchParameter.toUpperCase();
			for (Schedule schedule : schedulesList) {
				if (schedule.getTime_start().indexOf(searchParameter)!= -1 || schedule.getTime_end().toUpperCase().indexOf(searchParameter)!= -1) {
					schedulesListForSearch.add(schedule);					
				}
				
			}
			schedulesList = schedulesListForSearch;
			schedulesListForSearch = null;
		}
		return schedulesList;
	}
	
	private List<Schedule> mCreateSchedulePaginationData(Integer pageDisplayLength) {
		List<Schedule> schedulesList = new ArrayList<Schedule>();
		schedulesList = scheduleService.getSchedules();

		return schedulesList;
	}
	
	@RequestMapping(value="/mrequests", method=RequestMethod.GET)
	public String mrequests(Model model) {

		return "mrequests";
	}
	
	@RequestMapping(value = "/mrequests", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String mRequestDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<Request> requestsList = mCreateRequestPaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		requestsList = mGetRequestListBasedOnSearchParameter(searchParameter,requestsList);
		
		RequestJsonObject requestJsonObject = new RequestJsonObject();
		
		//Set Total display record
		requestJsonObject.setiTotalDisplayRecords(requestsList.size());
		
		//Set Total record
		requestJsonObject.setiTotalRecords(requestsList.size());
		requestJsonObject.setAaData(requestsList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(requestJsonObject);
	
		return json2;
    }
	
	private List<Request> mGetRequestListBasedOnSearchParameter(String searchParameter,List<Request> requestsList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<Request> requestsListForSearch = new ArrayList<Request>();
			searchParameter = searchParameter.toUpperCase();
			for (Request request : requestsList) {
				if (request.getMonhours().toUpperCase().indexOf(searchParameter)!= -1 || request.getTuehours().toUpperCase().indexOf(searchParameter)!= -1
						|| request.getWedhours().toUpperCase().indexOf(searchParameter)!= -1 || request.getThuhours().toUpperCase().indexOf(searchParameter)!= -1
						|| request.getFrihours().toUpperCase().indexOf(searchParameter)!= -1 || request.getRequest_note().toUpperCase().indexOf(searchParameter)!= -1) {
					requestsListForSearch.add(request);					
				}
				
			}
			requestsList = requestsListForSearch;
			requestsListForSearch = null;
		}
		return requestsList;
	}
	
	private List<Request> mCreateRequestPaginationData(Integer pageDisplayLength) {
		List<Request> requestsList = new ArrayList<Request>();
		requestsList = requestService.getRequests();

		return requestsList;
	}
	
	@RequestMapping(value="/maccounts", method=RequestMethod.GET)
	public String maccounts(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "maccounts";
	}
	
	@RequestMapping(value = "/maccounts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String accountDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<User> usersList = mCreateAccountPaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		usersList = mGetAccountListBasedOnSearchParameter(searchParameter,usersList);
		
		UserJsonObject userJsonObject = new UserJsonObject();
		
		//Set Total display record
		userJsonObject.setiTotalDisplayRecords(usersList.size());
		
		//Set Total record
		userJsonObject.setiTotalRecords(usersList.size());
		userJsonObject.setAaData(usersList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(userJsonObject);
	
		return json2;
    }
	
	private List<User> mGetAccountListBasedOnSearchParameter(String searchParameter,List<User> usersList) {
		
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
	
	private List<User> mCreateAccountPaginationData(Integer pageDisplayLength) {
		List<User> usersList = new ArrayList<User>();
		usersList = userService.getUsers();

		return usersList;
	}
	
	// ------------------------------------------------------------ Teacher --------------------------------------------------
	
	@RequestMapping(value="/tdashboard", method=RequestMethod.GET)
	public String tdashboard(Model model) {
		
		return "tdashboard";
	}
	
	@RequestMapping(value = "/tdashboard", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String tScheduleDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<Schedule> schedulesList = tCreateSchedulePaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		schedulesList = tGetScheduleListBasedOnSearchParameter(searchParameter,schedulesList);
		
		ScheduleJsonObject scheduleJsonObject = new ScheduleJsonObject();
		
		//Set Total display record
		scheduleJsonObject.setiTotalDisplayRecords(schedulesList.size());
		
		//Set Total record
		scheduleJsonObject.setiTotalRecords(schedulesList.size());
		scheduleJsonObject.setAaData(schedulesList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(scheduleJsonObject);
	
		return json2;
    }
	
	private List<Schedule> tGetScheduleListBasedOnSearchParameter(String searchParameter,List<Schedule> schedulesList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<Schedule> schedulesListForSearch = new ArrayList<Schedule>();
			searchParameter = searchParameter.toUpperCase();
			for (Schedule schedule : schedulesList) {
				if (schedule.getTime_start().indexOf(searchParameter)!= -1 || schedule.getTime_end().toUpperCase().indexOf(searchParameter)!= -1) {
					schedulesListForSearch.add(schedule);					
				}
				
			}
			schedulesList = schedulesListForSearch;
			schedulesListForSearch = null;
		}
		return schedulesList;
	}
	
	private List<Schedule> tCreateSchedulePaginationData(Integer pageDisplayLength) {
		List<Schedule> schedulesList = new ArrayList<Schedule>();
		schedulesList = scheduleService.getSchedules();

		return schedulesList;
	}
	
	@RequestMapping(value="/tavailability", method=RequestMethod.GET)
	public String tavailability(Model model) {
		
		return "tavailability";
	}
	
	@RequestMapping(value = "/tavailability", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String tAvailabilityDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<Availability> availabilitiesList = tCreateAvailabilityPaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		availabilitiesList = tGetAvailabilityListBasedOnSearchParameter(searchParameter,availabilitiesList);
		
		AvailabilityJsonObject availabilityJsonObject = new AvailabilityJsonObject();
		
		//Set Total display record
		availabilityJsonObject.setiTotalDisplayRecords(availabilitiesList.size());
		
		//Set Total record
		availabilityJsonObject.setiTotalRecords(availabilitiesList.size());
		
		//Convert binary to readable time
		availabilitiesList = tDataConvertBinaryToTime(availabilitiesList);
		
		availabilityJsonObject.setAaData(availabilitiesList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(availabilityJsonObject);
	
		return json2;
    }
	
	private List<Availability> tGetAvailabilityListBasedOnSearchParameter(String searchParameter,List<Availability> availabilitiesList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<Availability> availabilitiesListForSearch = new ArrayList<Availability>();
			searchParameter = searchParameter.toUpperCase();
			for (Availability availability : availabilitiesList) {
				if (availability.getMonhours().indexOf(searchParameter)!= -1 || availability.getTuehours().toUpperCase().indexOf(searchParameter)!= -1) {
					availabilitiesListForSearch.add(availability);					
				}
				
			}
			availabilitiesList = availabilitiesListForSearch;
			availabilitiesListForSearch = null;
		}
		return availabilitiesList;
	}
	
	private List<Availability> tCreateAvailabilityPaginationData(Integer pageDisplayLength) {
		List<Availability> availabilitesList = new ArrayList<Availability>();
		availabilitesList = availabilityService.getAvailabilities();

		return availabilitesList;
	}
	
	private List<Availability> tDataConvertBinaryToTime(List<Availability> availabilitiesList){
		for (Availability availability : availabilitiesList) {
			availability.setMonhours(convertBinaryToTime(availability.getMonhours()));
			availability.setTuehours(convertBinaryToTime(availability.getTuehours()));
			availability.setWedhours(convertBinaryToTime(availability.getWedhours()));
			availability.setThuhours(convertBinaryToTime(availability.getThuhours()));
			availability.setFrihours(convertBinaryToTime(availability.getFrihours()));
		}
		return availabilitiesList;
	}
	
	private String convertBinaryToTime(String str){
		//6:00 start time; 15min increments
		boolean shift = false;
		boolean second = false;
		int start1 = -1;
		int end1 = -1;
		int start2 = -1;
		int end2 = -1;
		int mins = 0;
		String temp = ""; 
		
		// Step through the string
		for(int i=0;i<50;i++){
			if(i==49) {
				if(shift==true){
					if(second==true){
						end2 = 50;
						break;
					}
					end1 = 50;
					break;
				}
			}
			// Found beginning of a shift
			if(str.charAt(i)=='1' && shift==false){
				// We're on shift now
				shift=true;
				// First shift of the day
				if(second==false){
					start1=i;
				}
				// Second shift of the day
				else 
					start2=i;
			}
			// Not on a shift
			else if(str.charAt(i)=='0'){
				// We were just on a shift
				if(shift==true){
					// Second shift of the day
					if(second==true){
						end2=i;
						// No more to see here
						break;
					}
					// First shift of the day
					end1=i;
					second=true;
				}
				shift=false;
			}
		}
		if(start1 >= 0){
			// Translate the first shift 
			start1 += 24;
			mins = start1 % 4;
			start1 = (start1-mins)/4;
			if(start1 > 12)
				start1 -= 12;
			if(start1 < 10)
				temp+="0";
			temp+=(Integer.toString(start1));
			temp+=(translate(mins));
			end1 += 24;
			mins = end1 % 4;
			end1 = (end1-mins)/4;
			if(end1 > 12)
				end1 -= 12;
			if(end1 < 10)
				temp+="0";
			temp+=(Integer.toString(end1));
			temp+=(translate(mins));
			
			// If there's a second shift
			if(start2 > 0){
				start2 += 24;
				mins = start2 % 4;
				start2 = (start2-mins)/4;
				if(start2 > 12)
					start2 -= 12;
				if(start2 < 10)
					temp+="0";
				temp+=(Integer.toString(start2));
				temp+=(translate(mins));
				end2 += 24;
				mins = end2 % 4;
				end2 = (end2-mins)/4;
				if(end2 > 12)
					end2 -= 12;
				if(end2 < 10)
					temp+="0";
				temp+=(Integer.toString(end2));
				temp+=(translate(mins));
			}
			// Format the times
			for(int i=0;i<temp.length()/4-1;i++){
				temp = temp.substring(0,5*i+2)+":"+temp.substring(5*i+2);
			}
			temp = temp.substring(0,temp.length()-2)+":"+temp.substring(temp.length()-2);
			temp = temp.substring(0,5)+"-"+temp.substring(5);
			if(start2 > 0)
				temp = temp.substring(0,11)+"\n"+temp.substring(11,16)+"-"+temp.substring(16);
	
			
			String stud = temp;
			return stud;
		}
		return "Not Available";
	}
	
	private String translate(int minutes) {
		if(minutes==0)
			return "00";
		else if(minutes==2)
			return "30";
		else if(minutes==1)
			return "15";
		return "45";
	}
	
	@RequestMapping(value="/trequests", method=RequestMethod.GET)
	public String trequests(Model model) {
		Request request = new Request();     
	    model.addAttribute("request", request);
		return "trequests";
	}
	
	@RequestMapping(value="/trequests", method=RequestMethod.POST)
	public String trequests(@Valid @ModelAttribute("request") Request request, BindingResult result, Model model) {
	
		if (result.hasErrors()) {
			return "trequests";
		} else {
			requestService.save(request);
			return "trequests";
		}
	}
	
	// ------------------------------------------------------------ Parent --------------------------------------------------

	@RequestMapping(value="/pdashboard", method=RequestMethod.GET)
	public String pdashboard(Model model) {
		
		return "pdashboard";
	}
	
	@RequestMapping(value = "/pdashboard", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String pScheduleDataTable(HttpServletRequest  request) throws IOException {
		
    	//Fetch the page number from client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;		
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<Schedule> schedulesList = pCreateSchedulePaginationData(pageDisplayLength);
		
		//Search functionality: Returns filtered list based on search parameter
		schedulesList = pGetScheduleListBasedOnSearchParameter(searchParameter,schedulesList);
		
		ScheduleJsonObject scheduleJsonObject = new ScheduleJsonObject();
		
		//Set Total display record
		scheduleJsonObject.setiTotalDisplayRecords(schedulesList.size());
		
		//Set Total record
		scheduleJsonObject.setiTotalRecords(schedulesList.size());
		scheduleJsonObject.setAaData(schedulesList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(scheduleJsonObject);
	
		return json2;
    }
	
	private List<Schedule> pGetScheduleListBasedOnSearchParameter(String searchParameter,List<Schedule> schedulesList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<Schedule> schedulesListForSearch = new ArrayList<Schedule>();
			searchParameter = searchParameter.toUpperCase();
			for (Schedule schedule : schedulesList) {
				if (schedule.getTime_start().indexOf(searchParameter)!= -1 || schedule.getTime_end().toUpperCase().indexOf(searchParameter)!= -1) {
					schedulesListForSearch.add(schedule);					
				}
				
			}
			schedulesList = schedulesListForSearch;
			schedulesListForSearch = null;
		}
		return schedulesList;
	}
	
	private List<Schedule> pCreateSchedulePaginationData(Integer pageDisplayLength) {
		List<Schedule> schedulesList = new ArrayList<Schedule>();
		schedulesList = scheduleService.getSchedules();

		return schedulesList;
	}
	
	
	@RequestMapping(value="/prequests", method=RequestMethod.GET)
	public String prequests(Model model) {
		Request request = new Request();     
	    model.addAttribute("request", request);
		return "prequests";
	}
	
	@RequestMapping(value="/prequests", method=RequestMethod.POST)
	public String prequests(@Valid @ModelAttribute("request") Request request, BindingResult result, Model model) {
	
		if (result.hasErrors()) {
			return "prequests";
		} else {
			requestService.save(request);
			return "prequests";
		}
	}
	
	
	// ------------------------------------------------------------ Common Dashboard --------------------------------------------------

	@RequestMapping(value= {"/mdashboard", "/tdashboard", "/pdashboard"}, method=RequestMethod.POST)
	public String logout(Model model, @RequestParam String action) {
			
			return "redirect:/";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}


