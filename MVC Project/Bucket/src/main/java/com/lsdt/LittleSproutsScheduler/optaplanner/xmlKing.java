package com.lsdt.LittleSproutsScheduler.optaplanner;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lsdt.LittleSproutsScheduler.model.Child;
import com.lsdt.LittleSproutsScheduler.model.User;

public class xmlKing 
{
	//arrays that hold the total number of children present per each group per quarter hour per day
	//we have 6:00am-6:00pm for a total of 48 quarter hours
	double[][] babySproutsPresent = new double[7][48];
	double[][] miniSproutsPresent = new double[7][48];
	double[][] peeweeSproutsPresent = new double[7][48];
	double[][] mightySproutsPresent = new double[7][48];
	double[][] halfpintSproutsPresent = new double[7][48];
	double[][] juniorSproutsPresent = new double[7][48];
	double[][] seniorSproutsPresent = new double[7][48];
	
	//arrays that hold the total number of teachers needed per each group per quarter hour per day
	//we have 6:00am-6:00pm for a total of 48 quarter hours
	int[][] babySproutTeachersNeeded = new int[7][48];
	int[][] miniSproutTeachersNeeded = new int[7][48];
	int[][] peeweeSproutTeachersNeeded = new int[7][48];
	int[][] mightySproutTeachersNeeded = new int[7][48];
	int[][] halfpintSproutTeachersNeeded = new int[7][48];
	int[][] juniorSproutTeachersNeeded = new int[7][48];
	int[][] seniorSproutTeachersNeeded = new int[7][48];
	int[][] totalSproutTeachersNeeded = new int[7][48];
	
	//arrays that hold the shifts needed for each day of the week
	ArrayList<String> mondayShifts = new ArrayList<String>();
	ArrayList<String> tuesdayShifts = new ArrayList<String>();
	ArrayList<String> wednesdayShifts = new ArrayList<String>();
	ArrayList<String> thursdayShifts = new ArrayList<String>();
	ArrayList<String> fridayShifts = new ArrayList<String>();
	
	//Global XML writing stuff
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	Element schedulingPeriod;
	
	
	
	
	//the children stored in the database
	//TODO: replace with actual data
	Child[] children = new Child[100];
	ArrayList<User> users = new ArrayList<User>();
	
	
	
	
	
	//An object for generating an Optaplanner xml contraint file
	public xmlKing()
	{
		
	}
	
	//calculates the total number of teachers needed per quarter hour
	public void calculateNeededTeachers()
	{
		//iterate through the days of the week
		for(int i = 0; i <=7; i++)
			//iterate through the quarter hours of the day
			for(int j = 0; j <= 48; j++)
				//iterate through the children
				//TODO: process the real children
				for(Child c: children)
				{
					//if the child is scheduled for this day and quarter hour
						//add 1 to the appropriate childrenPresent[][] bucket
					//else
						//continue
				}
		
		//divide the childrenPresent arrays by the appropriate weight ratio
		//this will yield the number of teachers needed per group per quarter hour per day
		for(int i = 0; i <= 7; i++)
			for(int j = 0; j <= 48; j++)
			{
				babySproutTeachersNeeded[i][j] = (int) Math.ceil((babySproutsPresent[i][j])/0.25);
				miniSproutTeachersNeeded[i][j] = (int) Math.ceil((miniSproutsPresent[i][j])/0.167);
				peeweeSproutTeachersNeeded[i][j] = (int) Math.ceil((peeweeSproutsPresent[i][j])/0.125);
				mightySproutTeachersNeeded[i][j] = (int) Math.ceil((mightySproutsPresent[i][j])/0.1);
				halfpintSproutTeachersNeeded[i][j] = (int) Math.ceil((halfpintSproutsPresent[i][j])/0.077);
				juniorSproutTeachersNeeded[i][j] = (int) Math.ceil((juniorSproutsPresent[i][j])/0.059);
				seniorSproutTeachersNeeded[i][j] = (int) Math.ceil((seniorSproutsPresent[i][j])/0.056);
			}
		
		//sum up all the group array to get the total number of teachers needed per quarter hour per day
		for(int i = 0; i <= 7; i++)
			for(int j = 0; j <= 48; j++)
				totalSproutTeachersNeeded[i][j] = babySproutTeachersNeeded[i][j] + miniSproutTeachersNeeded[i][j] + peeweeSproutTeachersNeeded[i][j]
						+ mightySproutTeachersNeeded[i][j] + halfpintSproutTeachersNeeded[i][j] + juniorSproutTeachersNeeded[i][j] + seniorSproutTeachersNeeded[i][j];
	}
	
	//creates shifts that meet the total number of teachers required per quarter hour
	public void createNeededShifts()
	{
		//some temp arrays for holding shift data
		ArrayList[] tempShifts = new ArrayList[5];
		ArrayList<String> tempMondayShifts = new ArrayList<String>();
		tempShifts[0] = tempMondayShifts;
		ArrayList<String> tempTuesdayShifts = new ArrayList<String>();
		tempShifts[1] = tempTuesdayShifts;
		ArrayList<String> tempWednesdayShifts = new ArrayList<String>();
		tempShifts[2] = tempWednesdayShifts;
		ArrayList<String> tempThursdayShifts = new ArrayList<String>();
		tempShifts[3] = tempThursdayShifts;
		ArrayList<String> tempFridayShifts = new ArrayList<String>();
		tempShifts[4] = tempFridayShifts;
		
		
		
		//first make all shifts as long as possible
		for(int i = 0; i <= 7; i ++)
			for(int j = 0; j <= 48; j++)
			{
				//do we need more teachers starting at this bucket?
				if(totalSproutTeachersNeeded[i][j] > 0)
				{
					//start a shift for the required number of teachers
					for(int b = 0; b < totalSproutTeachersNeeded[i][j]; b++)
					{
						boolean done = false;
						int counter = 0;
						int startTime = j;
						int endTime = j+1;
						
						while(!done)
						{
							counter++;
							
							//is the teacher still needed at the next hour?
							if(totalSproutTeachersNeeded[i][j+counter] > 0)
							{
								//"schedule" them
								totalSproutTeachersNeeded[i][j+counter] =- 1;
							}
							//the teacher is not needed, end the shift
							else
							{
								endTime = j + counter;
								done = true;
							}
							
							//has the shift lasted 9 hours?
							if(counter >= 36)
							{
								endTime = j + counter;
								done = true;
							}
						}
						
						//store the shift
						Integer st = startTime;
						Integer et = endTime;
						String shift = st.toString() + "-" + et.toString();
						tempShifts[i].add(shift);
					}
				}
				else
					continue;
			}
		
		//next balance the very short shifts with the long shifts
		for(int dayIndex = 0; dayIndex < 5; dayIndex++)
		{
			//look at all the shifts for this day, are any shorter than 2 hours?
			for(int fixIndex = 0; fixIndex < tempShifts[dayIndex].size(); fixIndex++)
			{
				//parse out the start and end time
				String shift = (String) tempShifts[dayIndex].get(fixIndex);
				int delimIndex = shift.indexOf('-');
				int startTimeFix = Integer.parseInt(shift.substring(0, delimIndex));
				int endTimeFix = Integer.parseInt(shift.substring(delimIndex));
				//check if the shift is shorter than 2 hours
				if((endTimeFix-startTimeFix) < 8)
				{
					//get the amount of time needed to make it 2 hours
					int quarterHoursNeeded = 8 - (endTimeFix-startTimeFix);
					
					//check if there is a shift adjacent that can lose the quarterHoursNeeded and still be longer than 2 hours
					for(int stealIndex = 0; stealIndex < tempShifts[dayIndex].size(); stealIndex++)
					{
						//skip the shift we are working on
						if(stealIndex == fixIndex)
							continue;
						
						//parse out the start and end time
						String shiftSteal = (String) tempShifts[dayIndex].get(stealIndex);
						int delimIndexSteal = shift.indexOf('-');
						int startTimeSteal = Integer.parseInt(shift.substring(0, delimIndexSteal));
						int endTimeSteal = Integer.parseInt(shift.substring(delimIndexSteal));
						
						//can we steal the quarterHoursNeeded?
						if((endTimeSteal - startTimeSteal) < (8 + quarterHoursNeeded))
						{
							//are we stealing hours from the end of the shift?
							if(endTimeSteal == startTimeFix)
							{
								//steal the quarterHoursNeeded
								int newTimeIndex = endTimeSteal - quarterHoursNeeded;
								
								//remove the old shifts
								tempShifts[dayIndex].remove(stealIndex);
								tempShifts[dayIndex].remove(fixIndex);
								
								//add the updated shifts
								Integer startTimeStealInt = startTimeSteal;
								Integer newTimeIndexInt = newTimeIndex;
								String newSteal = startTimeStealInt.toString() + "-" + newTimeIndexInt.toString();
								tempShifts[dayIndex].add(newSteal);
								
								Integer endTimeFixInt = endTimeFix;
								String newFix = newTimeIndexInt.toString() + "-" + endTimeFixInt.toString();
								tempShifts[dayIndex].add(newFix);
							}
							
							//are we stealing hours from the start of the shift?
							if(startTimeSteal == endTimeFix)
							{
								//steal the quarterHoursNeeded
								int newTimeIndex = startTimeSteal + quarterHoursNeeded;
								
								//remove the old shifts
								tempShifts[dayIndex].remove(stealIndex);
								tempShifts[dayIndex].remove(fixIndex);
								
								//add the updated shifts
								Integer endTimeStealInt = endTimeSteal;
								Integer newTimeIndexInt = newTimeIndex;
								String newSteal = newTimeIndexInt.toString() + "-" + endTimeStealInt.toString();
								tempShifts[dayIndex].add(newSteal);
								
								Integer startTimeFixInt = startTimeFix;
								String newFix = startTimeFixInt.toString() + "-" + newTimeIndexInt.toString();
								tempShifts[dayIndex].add(newFix);
							}
						}
						//we cannot steal the quarterHoursNeeded, try the next shift
						else
							continue;
						
					}
					
				}
				//the shift is longer than 2 hours (no worries)
				else
					continue;
			}
		}
		
		//store them shifts
		mondayShifts.addAll(tempShifts[0]);
		tuesdayShifts.addAll(tempShifts[0]);
		wednesdayShifts.addAll(tempShifts[0]);
		thursdayShifts.addAll(tempShifts[0]);
		fridayShifts.addAll(tempShifts[0]);
		
	}
	
	//finds the shifts that each employee is unable to work
	public void setUnavailableShifts()
	{
		
	}
	
	//creates the xml document
	//inputs header data and the start-end date xml
	public void generateStartingXML()
	{
		try
		{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			//create the SchedulingPeriod block
			schedulingPeriod = doc.createElement("SchedulingPeriod");
			doc.appendChild(schedulingPeriod);
			//add attributes
			schedulingPeriod.setAttribute("ID", "TOY1");
			//add the start/end date child
			Element startDate = doc.createElement("StartDate");
			Element endDate = doc.createElement("EndDate");
			startDate.appendChild(doc.createTextNode("2010-01-01"));
			endDate.appendChild(doc.createTextNode("2010-01-07"));
			schedulingPeriod.appendChild(startDate);
			schedulingPeriod.appendChild(endDate);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	
	}
	
	//inputs the skills xml block
	public void generateSkillsXML()
	{
		Element skills = doc.createElement("Skills");
		Element skill = doc.createElement("Skill");
		skill.appendChild(doc.createTextNode("Teacher"));
		skills.appendChild(skill);
		schedulingPeriod.appendChild(skills);
		
	}
	
	//inputs the shift xml block
	public void generateShiftXML()
	{
		//add the ShiftType tags
		Element shiftType = doc.createElement("ShiftTypes");
		schedulingPeriod.appendChild(shiftType);
		
		//TODO: remove test data
		//test data
		mondayShifts.add("10-20");
		tuesdayShifts.add("2-25");
		wednesdayShifts.add("30-40");
		thursdayShifts.add("27-45");
		fridayShifts.add("0-29");
		
		//add monday shifts
		for(int i = 0; i < mondayShifts.size(); i++)
		{
			//get the shift id
			//monday = 1 for first digit
			//if ID = 119, then mondayShifts[19]
			Integer shiftID = 100 + i;
			System.out.println("Shift ID: " + shiftID);
			
			//create the shift xml
			Element shift = doc.createElement("Shift");
			shift.setAttribute("ID", shiftID.toString());
			
			//get the start index and end index
			String shiftString = mondayShifts.get(i);
			int delimIndex = shiftString.indexOf("-");
			Integer startIndex = Integer.valueOf(shiftString.substring(0, delimIndex));
			Integer endIndex = Integer.valueOf(shiftString.substring(delimIndex+1));
			
			//calculate the start and end times
			Integer startHour = 6 + Math.floorDiv(startIndex, 4);
			Integer startMinute = 15*(startIndex%4);
			Integer endHour = 6 + Math.floorDiv(endIndex,4);
			Integer endMinute = 15*(endIndex%4);
			String startTime = startHour.toString() + ":" + startMinute.toString() + ":00";
			String endTime = endHour.toString() + ":" + endMinute.toString() + ":00";
			
			//check that the start time has correct leading zeros
			if(startTime.length() <= 6)
				startTime = "0" + startHour.toString() + ":0" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":") != 2)
				startTime = "0" + startHour.toString() + ":" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":", 3) != 5)
				startTime = startHour.toString() + ":0" + startMinute.toString() + ":00";
			
			//check that the end time has correct leading zeros
			if(endTime.length() <= 6)
				endTime = "0" + endHour.toString() + ":0" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":") != 2)
				endTime = "0" + endHour.toString() + ":" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":", 3) != 5)
				endTime = endHour.toString() + ":0" + endMinute.toString() + ":00";
			
			//add the xml
			Element startTimeElement = doc.createElement("StartTime");
			Element endTimeElement = doc.createElement("EndTime");
			startTimeElement.appendChild(doc.createTextNode(startTime));
			endTimeElement.appendChild(doc.createTextNode(endTime));
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode("Monday"));
			Element skills = doc.createElement("Skills");
			Element skill = doc.createElement("Skill");
			skill.appendChild(doc.createTextNode("Teacher"));
			shift.appendChild(startTimeElement);
			shift.appendChild(endTimeElement);
			shift.appendChild(description);
			skills.appendChild(skill);
			shift.appendChild(skills);
			shiftType.appendChild(shift);
		}
		
		//add tuesday shifts
		for(int i = 0; i < tuesdayShifts.size(); i++)
		{
			//get the shift id
			//tuesday = 2 for first digit
			//if ID = 208, then tuesdayShifts[8]
			Integer shiftID = 200 + i;
			System.out.println("Shift ID: " + shiftID);

			//create the shift xml
			Element shift = doc.createElement("Shift");
			shift.setAttribute("ID", shiftID.toString());

			//get the start index and end index
			String shiftString = tuesdayShifts.get(i);
			int delimIndex = shiftString.indexOf("-");
			Integer startIndex = Integer.valueOf(shiftString.substring(0, delimIndex));
			Integer endIndex = Integer.valueOf(shiftString.substring(delimIndex+1));

			//calculate the start and end times
			Integer startHour = 6 + Math.floorDiv(startIndex, 4);
			Integer startMinute = 15*(startIndex%4);
			Integer endHour = 6 + Math.floorDiv(endIndex,4);
			Integer endMinute = 15*(endIndex%4);
			String startTime = startHour.toString() + ":" + startMinute.toString() + ":00";
			String endTime = endHour.toString() + ":" + endMinute.toString() + ":00";

			//check that the start time has correct leading zeros
			if(startTime.length() <= 6)
				startTime = "0" + startHour.toString() + ":0" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":") != 2)
				startTime = "0" + startHour.toString() + ":" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":", 3) != 5)
				startTime = startHour.toString() + ":0" + startMinute.toString() + ":00";

			//check that the end time has correct leading zeros
			if(endTime.length() <= 6)
				endTime = "0" + endHour.toString() + ":0" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":") != 2)
				endTime = "0" + endHour.toString() + ":" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":", 3) != 5)
				endTime = endHour.toString() + ":0" + endMinute.toString() + ":00";

			//add the xml
			Element startTimeElement = doc.createElement("StartTime");
			Element endTimeElement = doc.createElement("EndTime");
			startTimeElement.appendChild(doc.createTextNode(startTime));
			endTimeElement.appendChild(doc.createTextNode(endTime));
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode("Tuesday"));
			Element skills = doc.createElement("Skills");
			Element skill = doc.createElement("Skill");
			skill.appendChild(doc.createTextNode("Teacher"));
			shift.appendChild(startTimeElement);
			shift.appendChild(endTimeElement);
			shift.appendChild(description);
			skills.appendChild(skill);
			shift.appendChild(skills);
			shiftType.appendChild(shift);
		}
		
		//add wednesday shifts
		for(int i = 0; i < wednesdayShifts.size(); i++)
		{
			//get the shift id
			//wednesday = 3 for first digit
			//if ID = 312, then wednesdayShifts[12]
			Integer shiftID = 300 + i;
			System.out.println("Shift ID: " + shiftID);

			//create the shift xml
			Element shift = doc.createElement("Shift");
			shift.setAttribute("ID", shiftID.toString());

			//get the start index and end index
			String shiftString = wednesdayShifts.get(i);
			int delimIndex = shiftString.indexOf("-");
			Integer startIndex = Integer.valueOf(shiftString.substring(0, delimIndex));
			Integer endIndex = Integer.valueOf(shiftString.substring(delimIndex+1));

			//calculate the start and end times
			Integer startHour = 6 + Math.floorDiv(startIndex, 4);
			Integer startMinute = 15*(startIndex%4);
			Integer endHour = 6 + Math.floorDiv(endIndex,4);
			Integer endMinute = 15*(endIndex%4);
			String startTime = startHour.toString() + ":" + startMinute.toString() + ":00";
			String endTime = endHour.toString() + ":" + endMinute.toString() + ":00";

			//check that the start time has correct leading zeros
			if(startTime.length() <= 6)
				startTime = "0" + startHour.toString() + ":0" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":") != 2)
				startTime = "0" + startHour.toString() + ":" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":", 3) != 5)
				startTime = startHour.toString() + ":0" + startMinute.toString() + ":00";

			//check that the end time has correct leading zeros
			if(endTime.length() <= 6)
				endTime = "0" + endHour.toString() + ":0" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":") != 2)
				endTime = "0" + endHour.toString() + ":" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":", 3) != 5)
				endTime = endHour.toString() + ":0" + endMinute.toString() + ":00";

			//add the xml
			Element startTimeElement = doc.createElement("StartTime");
			Element endTimeElement = doc.createElement("EndTime");
			startTimeElement.appendChild(doc.createTextNode(startTime));
			endTimeElement.appendChild(doc.createTextNode(endTime));
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode("Wednesday"));
			Element skills = doc.createElement("Skills");
			Element skill = doc.createElement("Skill");
			skill.appendChild(doc.createTextNode("Teacher"));
			shift.appendChild(startTimeElement);
			shift.appendChild(endTimeElement);
			shift.appendChild(description);
			skills.appendChild(skill);
			shift.appendChild(skills);
			shiftType.appendChild(shift);
		}

		//add thursday shifts
		for(int i = 0; i < thursdayShifts.size(); i++)
		{
			//get the shift id
			//thursday = 4 for first digit
			//if ID = 400, then thursdayShifts[0]
			Integer shiftID = 400 + i;
			System.out.println("Shift ID: " + shiftID);

			//create the shift xml
			Element shift = doc.createElement("Shift");
			shift.setAttribute("ID", shiftID.toString());

			//get the start index and end index
			String shiftString = thursdayShifts.get(i);
			int delimIndex = shiftString.indexOf("-");
			Integer startIndex = Integer.valueOf(shiftString.substring(0, delimIndex));
			Integer endIndex = Integer.valueOf(shiftString.substring(delimIndex+1));

			//calculate the start and end times
			Integer startHour = 6 + Math.floorDiv(startIndex, 4);
			Integer startMinute = 15*(startIndex%4);
			Integer endHour = 6 + Math.floorDiv(endIndex,4);
			Integer endMinute = 15*(endIndex%4);
			String startTime = startHour.toString() + ":" + startMinute.toString() + ":00";
			String endTime = endHour.toString() + ":" + endMinute.toString() + ":00";

			//check that the start time has correct leading zeros
			if(startTime.length() <= 6)
				startTime = "0" + startHour.toString() + ":0" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":") != 2)
				startTime = "0" + startHour.toString() + ":" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":", 3) != 5)
				startTime = startHour.toString() + ":0" + startMinute.toString() + ":00";

			//check that the end time has correct leading zeros
			if(endTime.length() <= 6)
				endTime = "0" + endHour.toString() + ":0" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":") != 2)
				endTime = "0" + endHour.toString() + ":" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":", 3) != 5)
				endTime = endHour.toString() + ":0" + endMinute.toString() + ":00";

			//add the xml
			Element startTimeElement = doc.createElement("StartTime");
			Element endTimeElement = doc.createElement("EndTime");
			startTimeElement.appendChild(doc.createTextNode(startTime));
			endTimeElement.appendChild(doc.createTextNode(endTime));
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode("Thursday"));
			Element skills = doc.createElement("Skills");
			Element skill = doc.createElement("Skill");
			skill.appendChild(doc.createTextNode("Teacher"));
			shift.appendChild(startTimeElement);
			shift.appendChild(endTimeElement);
			shift.appendChild(description);
			skills.appendChild(skill);
			shift.appendChild(skills);
			shiftType.appendChild(shift);
		}

		//add friday shifts
		for(int i = 0; i < fridayShifts.size(); i++)
		{
			//get the shift id
			//friday = 5 for first digit
			//if ID = 517, then fridayShifts[17]
			Integer shiftID = 500 + i;
			System.out.println("Shift ID: " + shiftID);

			//create the shift xml
			Element shift = doc.createElement("Shift");
			shift.setAttribute("ID", shiftID.toString());

			//get the start index and end index
			String shiftString = fridayShifts.get(i);
			int delimIndex = shiftString.indexOf("-");
			Integer startIndex = Integer.valueOf(shiftString.substring(0, delimIndex));
			Integer endIndex = Integer.valueOf(shiftString.substring(delimIndex+1));

			//calculate the start and end times
			Integer startHour = 6 + Math.floorDiv(startIndex, 4);
			Integer startMinute = 15*(startIndex%4);
			Integer endHour = 6 + Math.floorDiv(endIndex,4);
			Integer endMinute = 15*(endIndex%4);
			String startTime = startHour.toString() + ":" + startMinute.toString() + ":00";
			String endTime = endHour.toString() + ":" + endMinute.toString() + ":00";

			//check that the start time has correct leading zeros
			if(startTime.length() <= 6)
				startTime = "0" + startHour.toString() + ":0" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":") != 2)
				startTime = "0" + startHour.toString() + ":" + startMinute.toString() + ":00";
			else if(startTime.indexOf(":", 3) != 5)
				startTime = startHour.toString() + ":0" + startMinute.toString() + ":00";

			//check that the end time has correct leading zeros
			if(endTime.length() <= 6)
				endTime = "0" + endHour.toString() + ":0" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":") != 2)
				endTime = "0" + endHour.toString() + ":" + endMinute.toString() + ":00";
			else if(endTime.indexOf(":", 3) != 5)
				endTime = endHour.toString() + ":0" + endMinute.toString() + ":00";

			//add the xml
			Element startTimeElement = doc.createElement("StartTime");
			Element endTimeElement = doc.createElement("EndTime");
			startTimeElement.appendChild(doc.createTextNode(startTime));
			endTimeElement.appendChild(doc.createTextNode(endTime));
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode("Friday"));
			Element skills = doc.createElement("Skills");
			Element skill = doc.createElement("Skill");
			skill.appendChild(doc.createTextNode("Teacher"));
			shift.appendChild(startTimeElement);
			shift.appendChild(endTimeElement);
			shift.appendChild(description);
			skills.appendChild(skill);
			shift.appendChild(skills);
			shiftType.appendChild(shift);
		}
		
		//add our "null" shift
		//create the shift xml
		Element shift = doc.createElement("Shift");
		shift.setAttribute("ID", "600");

		Element startTimeElement = doc.createElement("StartTime");
		Element endTimeElement = doc.createElement("EndTime");
		startTimeElement.appendChild(doc.createTextNode("06:00:00"));
		endTimeElement.appendChild(doc.createTextNode("07:00:00"));
		Element description = doc.createElement("Description");
		description.appendChild(doc.createTextNode("Saturday"));
		Element skills = doc.createElement("Skills");
		Element skill = doc.createElement("Skill");
		skill.appendChild(doc.createTextNode("Teacher"));
		shift.appendChild(startTimeElement);
		shift.appendChild(endTimeElement);
		shift.appendChild(description);
		skills.appendChild(skill);
		shift.appendChild(skills);
		shiftType.appendChild(shift);
		
	}
	
	//inputs the patterns xml block
	//these patterns are all unwanted patterns for the contract block
	//each pattern element needs more than 1 shift added to it, otherwise Optaplanner will crash
	//AKA no single shift patterns
	//A nice workaround is adding what is essentially a null shift after the single shift
	public void generateUnwantedPatternsXML()
	{
		Element patterns = doc.createElement("Patterns");
		schedulingPeriod.appendChild(patterns);
		
		//generate patterns for all monday shifts
		for(int i = 0; i < mondayShifts.size(); i++)
		{
			Integer id = 100 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			pattern.setAttribute("weight", "-1");
			
			
			Element patternEntries = doc.createElement("PatternEntries");
			Element patternEntry1 = doc.createElement("PatternEntry");
			patternEntry1.setAttribute("index", "0");
			Element shiftType = doc.createElement("ShiftType");
			Element day = doc.createElement("Day");
			shiftType.appendChild(doc.createTextNode(id.toString()));
			day.appendChild(doc.createTextNode("Any"));
			patternEntry1.appendChild(shiftType);
			patternEntry1.appendChild(day);
			patternEntries.appendChild(patternEntry1);
			
			Element patternEntry2 = doc.createElement("PatternEntry");
			patternEntry2.setAttribute("index", "1");
			Element shiftType2 = doc.createElement("ShiftType");
			Element day2 = doc.createElement("Day");
			shiftType2.appendChild(doc.createTextNode("600"));
			day2.appendChild(doc.createTextNode("Any"));
			patternEntry2.appendChild(shiftType2);
			patternEntry2.appendChild(day2);
			patternEntries.appendChild(patternEntry2);
			
			pattern.appendChild(patternEntries);
			patterns.appendChild(pattern);
		}
		
		//generate patterns for all tuesday shifts
		for(int i = 0; i < tuesdayShifts.size(); i++)
		{
			Integer id = 200 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			pattern.setAttribute("weight", "-1");


			Element patternEntries = doc.createElement("PatternEntries");
			Element patternEntry1 = doc.createElement("PatternEntry");
			patternEntry1.setAttribute("index", "0");
			Element shiftType = doc.createElement("ShiftType");
			Element day = doc.createElement("Day");
			shiftType.appendChild(doc.createTextNode(id.toString()));
			day.appendChild(doc.createTextNode("Any"));
			patternEntry1.appendChild(shiftType);
			patternEntry1.appendChild(day);
			patternEntries.appendChild(patternEntry1);
			
			Element patternEntry2 = doc.createElement("PatternEntry");
			patternEntry2.setAttribute("index", "1");
			Element shiftType2 = doc.createElement("ShiftType");
			Element day2 = doc.createElement("Day");
			shiftType2.appendChild(doc.createTextNode("600"));
			day2.appendChild(doc.createTextNode("Any"));
			patternEntry2.appendChild(shiftType2);
			patternEntry2.appendChild(day2);
			patternEntries.appendChild(patternEntry2);
			
			pattern.appendChild(patternEntries);
			patterns.appendChild(pattern);
		}

		//generate patterns for all wednesday shifts
		for(int i = 0; i < wednesdayShifts.size(); i++)
		{
			Integer id = 300 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			pattern.setAttribute("weight", "-1");


			Element patternEntries = doc.createElement("PatternEntries");
			Element patternEntry1 = doc.createElement("PatternEntry");
			patternEntry1.setAttribute("index", "0");
			Element shiftType = doc.createElement("ShiftType");
			Element day = doc.createElement("Day");
			shiftType.appendChild(doc.createTextNode(id.toString()));
			day.appendChild(doc.createTextNode("Any"));
			patternEntry1.appendChild(shiftType);
			patternEntry1.appendChild(day);
			patternEntries.appendChild(patternEntry1);
			
			Element patternEntry2 = doc.createElement("PatternEntry");
			patternEntry2.setAttribute("index", "1");
			Element shiftType2 = doc.createElement("ShiftType");
			Element day2 = doc.createElement("Day");
			shiftType2.appendChild(doc.createTextNode("600"));
			day2.appendChild(doc.createTextNode("Any"));
			patternEntry2.appendChild(shiftType2);
			patternEntry2.appendChild(day2);
			patternEntries.appendChild(patternEntry2);
			
			pattern.appendChild(patternEntries);
			patterns.appendChild(pattern);
		}

		//generate patterns for all thursday shifts
		for(int i = 0; i < thursdayShifts.size(); i++)
		{
			Integer id = 400 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			pattern.setAttribute("weight", "-1");


			Element patternEntries = doc.createElement("PatternEntries");
			Element patternEntry1 = doc.createElement("PatternEntry");
			patternEntry1.setAttribute("index", "0");
			Element shiftType = doc.createElement("ShiftType");
			Element day = doc.createElement("Day");
			shiftType.appendChild(doc.createTextNode(id.toString()));
			day.appendChild(doc.createTextNode("Any"));
			patternEntry1.appendChild(shiftType);
			patternEntry1.appendChild(day);
			patternEntries.appendChild(patternEntry1);
			
			Element patternEntry2 = doc.createElement("PatternEntry");
			patternEntry2.setAttribute("index", "1");
			Element shiftType2 = doc.createElement("ShiftType");
			Element day2 = doc.createElement("Day");
			shiftType2.appendChild(doc.createTextNode("600"));
			day2.appendChild(doc.createTextNode("Any"));
			patternEntry2.appendChild(shiftType2);
			patternEntry2.appendChild(day2);
			patternEntries.appendChild(patternEntry2);
			
			pattern.appendChild(patternEntries);
			patterns.appendChild(pattern);
		}

		//generate patterns for all friday shifts
		for(int i = 0; i < fridayShifts.size(); i++)
		{
			Integer id = 500 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			//TODO: is the weight of -1 correct?
			pattern.setAttribute("weight", "-1");


			Element patternEntries = doc.createElement("PatternEntries");
			Element patternEntry1 = doc.createElement("PatternEntry");
			patternEntry1.setAttribute("index", "0");
			Element shiftType = doc.createElement("ShiftType");
			Element day = doc.createElement("Day");
			shiftType.appendChild(doc.createTextNode(id.toString()));
			day.appendChild(doc.createTextNode("Any"));
			patternEntry1.appendChild(shiftType);
			patternEntry1.appendChild(day);
			patternEntries.appendChild(patternEntry1);
			
			Element patternEntry2 = doc.createElement("PatternEntry");
			patternEntry2.setAttribute("index", "1");
			Element shiftType2 = doc.createElement("ShiftType");
			Element day2 = doc.createElement("Day");
			shiftType2.appendChild(doc.createTextNode("600"));
			day2.appendChild(doc.createTextNode("Any"));
			patternEntry2.appendChild(shiftType2);
			patternEntry2.appendChild(day2);
			patternEntries.appendChild(patternEntry2);
			
			pattern.appendChild(patternEntries);
			patterns.appendChild(pattern);
		}
	}
	
	//inputs the contract block xml
	//each employee gets their own contract that uses their unwanted patterns
	public void generateEmployeeContractsXML()
	{
		//TODO: replace test data
		User testUser0 = new User('T', "whatev", "whatev", "whatev", "Bobby", "Fischer", "whatev");
		User testUser1 = new User('T', "whatev", "whatev", "whatev", "Jo", "Schmo", "whatev");
		User testUser2 = new User('T', "whatev", "whatev", "whatev", "Danny", "Greene", "whatev");
		User testUser3 = new User('T', "whatev", "whatev", "whatev", "Gandalf", "The White", "whatev");
		User testUser4 = new User('T', "whatev", "whatev", "whatev", "Bilbo", "Baggins", "whatev");
		users.add(testUser0);
		users.add(testUser1);
		users.add(testUser2);
		users.add(testUser3);
		users.add(testUser4);
		
		
		Element contracts = doc.createElement("Contracts");
		schedulingPeriod.appendChild(contracts);
		
		//make a unique contract for each employee
		//this is probably overkill as some contracts could be reused
		//I don't care though
		for(int i = 0; i < users.size(); i++)
		{
			Integer intI = i;
			User u = users.get(i);
			Element contract = doc.createElement("Contract");
			//TODO: replace static id with employee ID
			contract.setAttribute("ID", intI.toString());
			
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode(u.getName_first() + " " + u.getName_last() + "'s Contract"));
			contract.appendChild(description);
			
			Element SingleAssignmentPerDay = doc.createElement("SingleAssignmentPerDay");
			SingleAssignmentPerDay.setAttribute("weight", "1");
			SingleAssignmentPerDay.appendChild(doc.createTextNode("true"));
			contract.appendChild(SingleAssignmentPerDay);
			
			//TODO: are these arguements correct?
			Element MaxNumAssignments = doc.createElement("MaxNumAssignments");
			MaxNumAssignments.setAttribute("on", "1");
			MaxNumAssignments.setAttribute("weight", "1");
			MaxNumAssignments.appendChild(doc.createTextNode("10"));
			contract.appendChild(MaxNumAssignments);
			
			Element MinNumAssignments = doc.createElement("MinNumAssignments");
			MinNumAssignments.setAttribute("on", "1");
			MinNumAssignments.setAttribute("weight", "1");
			MinNumAssignments.appendChild(doc.createTextNode("3"));
			contract.appendChild(MinNumAssignments);
			
			Element MaxConsecutiveWorkingDays = doc.createElement("MaxConsecutiveWorkingDays");
			MaxConsecutiveWorkingDays.setAttribute("on", "1");
			MaxConsecutiveWorkingDays.setAttribute("weight", "1");
			MaxConsecutiveWorkingDays.appendChild(doc.createTextNode("5"));
			contract.appendChild(MaxConsecutiveWorkingDays);
			
			Element MinConsecutiveWorkingDays = doc.createElement("MinConsecutiveWorkingDays");
			MinConsecutiveWorkingDays.setAttribute("on", "1");
			MinConsecutiveWorkingDays.setAttribute("weight", "1");
			MinConsecutiveWorkingDays.appendChild(doc.createTextNode("1"));
			contract.appendChild(MinConsecutiveWorkingDays);
			
			Element MaxConsecutiveFreeDays = doc.createElement("MaxConsecutiveFreeDays");
			MaxConsecutiveFreeDays.setAttribute("on", "1");
			MaxConsecutiveFreeDays.setAttribute("weight", "1");
			MaxConsecutiveFreeDays.appendChild(doc.createTextNode("5"));
			contract.appendChild(MaxConsecutiveFreeDays);
			
			Element MinConsecutiveFreeDays = doc.createElement("MinConsecutiveFreeDays");
			MinConsecutiveFreeDays.setAttribute("on", "1");
			MinConsecutiveFreeDays.setAttribute("weight", "1");
			MinConsecutiveFreeDays.appendChild(doc.createTextNode("1"));
			contract.appendChild(MinConsecutiveFreeDays);
			
			Element MaxConsecutiveWorkingWeekends = doc.createElement("MaxConsecutiveWorkingWeekends");
			MaxConsecutiveWorkingWeekends.setAttribute("on", "0");
			MaxConsecutiveWorkingWeekends.setAttribute("weight", "0");
			MaxConsecutiveWorkingWeekends.appendChild(doc.createTextNode("0"));
			contract.appendChild(MaxConsecutiveWorkingWeekends);
			
			Element MinConsecutiveWorkingWeekends = doc.createElement("MinConsecutiveWorkingWeekends");
			MinConsecutiveWorkingWeekends.setAttribute("on", "0");
			MinConsecutiveWorkingWeekends.setAttribute("weight", "0");
			MinConsecutiveWorkingWeekends.appendChild(doc.createTextNode("0"));
			contract.appendChild(MinConsecutiveWorkingWeekends);
			
			Element MaxWorkingWeekendsInFourWeeks = doc.createElement("MaxWorkingWeekendsInFourWeeks");
			MaxWorkingWeekendsInFourWeeks.setAttribute("on", "0");
			MaxWorkingWeekendsInFourWeeks.setAttribute("weight", "0");
			MaxWorkingWeekendsInFourWeeks.appendChild(doc.createTextNode("0"));
			contract.appendChild(MaxWorkingWeekendsInFourWeeks);
			
			Element WeekendDefinition = doc.createElement("WeekendDefinition");
			WeekendDefinition.appendChild(doc.createTextNode("SaturdaySunday"));
			contract.appendChild(WeekendDefinition);
			
			Element CompleteWeekends = doc.createElement("CompleteWeekends");
			CompleteWeekends.setAttribute("weight", "1");
			CompleteWeekends.appendChild(doc.createTextNode("true"));
			contract.appendChild(CompleteWeekends);
			
			Element IdenticalShiftTypesDuringWeekend = doc.createElement("IdenticalShiftTypesDuringWeekend");
			IdenticalShiftTypesDuringWeekend.setAttribute("weight", "1");
			IdenticalShiftTypesDuringWeekend.appendChild(doc.createTextNode("true"));
			contract.appendChild(IdenticalShiftTypesDuringWeekend);
			
			Element NoNightShiftBeforeFreeWeekend = doc.createElement("NoNightShiftBeforeFreeWeekend");
			NoNightShiftBeforeFreeWeekend.setAttribute("weight", "0");
			NoNightShiftBeforeFreeWeekend.appendChild(doc.createTextNode("false"));
			contract.appendChild(NoNightShiftBeforeFreeWeekend);
			
			Element AlternativeSkillCategory = doc.createElement("AlternativeSkillCategory");
			AlternativeSkillCategory.setAttribute("weight", "0");
			AlternativeSkillCategory.appendChild(doc.createTextNode("false"));
			contract.appendChild(AlternativeSkillCategory);
			
			Element UnwantedPatterns = doc.createElement("UnwantedPatterns");
			//TODO: fill up the UnwantedPatterns with the correct patterns
			Element pattern = doc.createElement("Pattern");
			pattern.appendChild(doc.createTextNode("100"));
			UnwantedPatterns.appendChild(pattern);
			contract.appendChild(UnwantedPatterns);
			
			contracts.appendChild(contract);		
		}
	}
	
	//inputs employee xml block
	public void generateEmployeeXML()
	{
		Element Employees = doc.createElement("Employees");
		schedulingPeriod.appendChild(Employees);
		
		//TODO: use the employee id as eventually employee id = contract id
		for(int i = 0; i < users.size(); i++)
		{
			Integer IntI = i;
			User u = users.get(i);
			Element Employee = doc.createElement("Employee");
			Employee.setAttribute("ID", IntI.toString());
			Employees.appendChild(Employee);
			
			Element ContractID = doc.createElement("ContractID");
			ContractID.appendChild(doc.createTextNode(IntI.toString()));
			Employee.appendChild(ContractID);
			
			Element Name = doc.createElement("Name");
			Name.appendChild(doc.createTextNode(u.getName_first() + " " + u.getName_last()));
			Employee.appendChild(Name);
			
			Element Skills = doc.createElement("Skills");
			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Skills.appendChild(Skill);
			Employee.appendChild(Skills);	
		}
	}
	
	//inputs the cover requirements xml block
	public void generateCoverRequirementsXML()
	{
		Element CoverRequirements = doc.createElement("CoverRequirements");
		schedulingPeriod.appendChild(CoverRequirements);
		
		//add all covers for monday
		//we only need one cover per shift because we might have:
		//shift124 = shift106 but we still store them distinctly
		//this is a waste of resources
		//but I don't care
		Element DayOfWeekCoverMonday = doc.createElement("DayOfWeekCover");
		Element DayMonday = doc.createElement("Day");
		DayMonday.appendChild(doc.createTextNode("Monday"));
		DayOfWeekCoverMonday.appendChild(DayMonday);
		CoverRequirements.appendChild(DayOfWeekCoverMonday);
		for(int i = 0; i < mondayShifts.size(); i++)
		{
			Element Cover = doc.createElement("Cover");
			Integer shiftID = 100+i;
			
			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Cover.appendChild(Skill);
			
			Element Shift = doc.createElement("Shift");
			Shift.appendChild(doc.createTextNode(shiftID.toString()));
			Cover.appendChild(Shift);
			
			Element Preferred = doc.createElement("Preferred");
			Preferred.appendChild(doc.createTextNode("1"));
			Cover.appendChild(Preferred);
			
			DayOfWeekCoverMonday.appendChild(Cover);
		}
		
		//add all covers for tuesday
		//we only need one cover per shift because we might have:
		//shift124 = shift106 but we still store them distinctly
		//this is a waste of resources
		//but I don't care
		Element DayOfWeekCoverTuesday = doc.createElement("DayOfWeekCover");
		Element DayTuesday = doc.createElement("Day");
		DayTuesday.appendChild(doc.createTextNode("Tuesday"));
		DayOfWeekCoverTuesday.appendChild(DayTuesday);
		CoverRequirements.appendChild(DayOfWeekCoverTuesday);
		for(int i = 0; i < tuesdayShifts.size(); i++)
		{
			Element Cover = doc.createElement("Cover");
			Integer shiftID = 200+i;

			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Cover.appendChild(Skill);

			Element Shift = doc.createElement("Shift");
			Shift.appendChild(doc.createTextNode(shiftID.toString()));
			Cover.appendChild(Shift);

			Element Preferred = doc.createElement("Preferred");
			Preferred.appendChild(doc.createTextNode("1"));
			Cover.appendChild(Preferred);

			DayOfWeekCoverTuesday.appendChild(Cover);
		}
		
		//add all covers for wednesday
		//we only need one cover per shift because we might have:
		//shift124 = shift106 but we still store them distinctly
		//this is a waste of resources
		//but I don't care
		Element DayOfWeekCoverWednesday = doc.createElement("DayOfWeekCover");
		Element DayWednesday = doc.createElement("Day");
		DayWednesday.appendChild(doc.createTextNode("Wednesday"));
		DayOfWeekCoverWednesday.appendChild(DayWednesday);
		CoverRequirements.appendChild(DayOfWeekCoverWednesday);
		for(int i = 0; i < wednesdayShifts.size(); i++)
		{
			Element Cover = doc.createElement("Cover");
			Integer shiftID = 300+i;

			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Cover.appendChild(Skill);

			Element Shift = doc.createElement("Shift");
			Shift.appendChild(doc.createTextNode(shiftID.toString()));
			Cover.appendChild(Shift);

			Element Preferred = doc.createElement("Preferred");
			Preferred.appendChild(doc.createTextNode("1"));
			Cover.appendChild(Preferred);

			DayOfWeekCoverWednesday.appendChild(Cover);
		}
		
		//add all covers for thursday
		//we only need one cover per shift because we might have:
		//shift124 = shift106 but we still store them distinctly
		//this is a waste of resources
		//but I don't care
		Element DayOfWeekCoverThursday = doc.createElement("DayOfWeekCover");
		Element DayThursday = doc.createElement("Day");
		DayThursday.appendChild(doc.createTextNode("Thursday"));
		DayOfWeekCoverThursday.appendChild(DayThursday);
		CoverRequirements.appendChild(DayOfWeekCoverThursday);
		for(int i = 0; i < thursdayShifts.size(); i++)
		{
			Element Cover = doc.createElement("Cover");
			Integer shiftID = 400+i;

			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Cover.appendChild(Skill);

			Element Shift = doc.createElement("Shift");
			Shift.appendChild(doc.createTextNode(shiftID.toString()));
			Cover.appendChild(Shift);

			Element Preferred = doc.createElement("Preferred");
			Preferred.appendChild(doc.createTextNode("1"));
			Cover.appendChild(Preferred);

			DayOfWeekCoverThursday.appendChild(Cover);
		}
		
		//add all covers for friday
		//we only need one cover per shift because we might have:
		//shift124 = shift106 but we still store them distinctly
		//this is a waste of resources
		//but I don't care
		Element DayOfWeekCoverFriday = doc.createElement("DayOfWeekCover");
		Element DayFriday = doc.createElement("Day");
		DayFriday.appendChild(doc.createTextNode("Friday"));
		DayOfWeekCoverFriday.appendChild(DayFriday);
		CoverRequirements.appendChild(DayOfWeekCoverFriday);
		for(int i = 0; i < fridayShifts.size(); i++)
		{
			Element Cover = doc.createElement("Cover");
			Integer shiftID = 500+i;

			Element Skill = doc.createElement("Skill");
			Skill.appendChild(doc.createTextNode("Teacher"));
			Cover.appendChild(Skill);

			Element Shift = doc.createElement("Shift");
			Shift.appendChild(doc.createTextNode(shiftID.toString()));
			Cover.appendChild(Shift);

			Element Preferred = doc.createElement("Preferred");
			Preferred.appendChild(doc.createTextNode("1"));
			Cover.appendChild(Preferred);

			DayOfWeekCoverFriday.appendChild(Cover);
		}
	}
	
	//inputs closing xml tags
	//finishes writing to the file
	//verifies the xml content
	public void finalizeXML()
	{
		try
		{
			//write the XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\file.xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
