package org.lsdt.optaplannerLittleSprouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.io.File;
import java.math.BigInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 











import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class xmlKing 
{
	//arrays that hold the total number of children present per each group per quarter hour per day
	//we have 6:00am-6:00pm for a total of 48 quarter hours
	double[][] babySproutsPresent = new double[5][50];
	double[][] miniSproutsPresent = new double[5][50];
	double[][] peeweeSproutsPresent = new double[5][50];
	double[][] mightySproutsPresent = new double[5][50];
	double[][] halfpintSproutsPresent = new double[5][50];
	double[][] juniorSproutsPresent = new double[5][50];
	double[][] seniorSproutsPresent = new double[5][50];
	
	//arrays that hold the total number of teachers needed per each group per quarter hour per day
	//we have 6:00am-6:00pm for a total of 48 quarter hours
	int[][] babySproutTeachersNeeded = new int[5][50];
	int[][] miniSproutTeachersNeeded = new int[5][50];
	int[][] peeweeSproutTeachersNeeded = new int[5][50];
	int[][] mightySproutTeachersNeeded = new int[5][50];
	int[][] halfpintSproutTeachersNeeded = new int[5][50];
	int[][] juniorSproutTeachersNeeded = new int[5][50];
	int[][] seniorSproutTeachersNeeded = new int[5][50];
	//int[][] totalSproutTeachersNeeded = new int[5][50];
	
	//arrays that hold the shifts needed for each day of the week
	ArrayList<String> mondayShifts = new ArrayList<String>();
	ArrayList<String> mondayShiftsBaby = new ArrayList<String>();
	ArrayList<String> mondayShiftsMini = new ArrayList<String>();
	ArrayList<String> mondayShiftsPeewee = new ArrayList<String>();
	ArrayList<String> mondayShiftsMighty = new ArrayList<String>();
	ArrayList<String> mondayShiftsHalfpint = new ArrayList<String>();
	ArrayList<String> mondayShiftsJunior = new ArrayList<String>();
	ArrayList<String> mondayShiftsSenior = new ArrayList<String>();
	
	ArrayList<String> tuesdayShifts = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsBaby = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsMini = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsPeewee = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsMighty = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsHalfpint = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsJunior = new ArrayList<String>();
	ArrayList<String> tuesdayShiftsSenior = new ArrayList<String>();
	
	ArrayList<String> wednesdayShifts = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsBaby = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsMini = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsPeewee = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsMighty = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsHalfpint = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsJunior = new ArrayList<String>();
	ArrayList<String> wednesdayShiftsSenior = new ArrayList<String>();
	
	ArrayList<String> thursdayShifts = new ArrayList<String>();
	ArrayList<String> thursdayShiftsBaby = new ArrayList<String>();
	ArrayList<String> thursdayShiftsMini = new ArrayList<String>();
	ArrayList<String> thursdayShiftsPeewee = new ArrayList<String>();
	ArrayList<String> thursdayShiftsMighty = new ArrayList<String>();
	ArrayList<String> thursdayShiftsHalfpint = new ArrayList<String>();
	ArrayList<String> thursdayShiftsJunior = new ArrayList<String>();
	ArrayList<String> thursdayShiftsSenior = new ArrayList<String>();
	
	ArrayList<String> fridayShifts = new ArrayList<String>();
	ArrayList<String> fridayShiftsBaby = new ArrayList<String>();
	ArrayList<String> fridayShiftsMini = new ArrayList<String>();
	ArrayList<String> fridayShiftsPeewee = new ArrayList<String>();
	ArrayList<String> fridayShiftsMighty = new ArrayList<String>();
	ArrayList<String> fridayShiftsHalfpint = new ArrayList<String>();
	ArrayList<String> fridayShiftsJunior = new ArrayList<String>();
	ArrayList<String> fridayShiftsSenior = new ArrayList<String>();
	
	HashMap ids2AgeGroup = new HashMap();
	ArrayList<String> mondayShiftDescriptions = new ArrayList<String>();
	ArrayList<String> tuesdayShiftDescriptions = new ArrayList<String>();
	ArrayList<String> weddayShiftDescriptions = new ArrayList<String>();
	ArrayList<String> thursdayShiftDescriptions = new ArrayList<String>();
	ArrayList<String> fridayShiftDescriptions = new ArrayList<String>();
	
	//Global XML writing stuff
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	Element schedulingPeriod;
	String stringStartDate;
	
	//the children stored in the database
	ArrayList<Child> children = new ArrayList<Child>();
	ArrayList<User> teachers = new ArrayList<User>();
	//availabilty starts at 6:00AM
	//it is still stored as 30 minute blocks
	ArrayList<Availability> availability = new ArrayList<Availability>();
	ArrayList<Availability> childAvailability = new ArrayList<Availability>();
	ArrayList<Integer> childIDS = new ArrayList<Integer>();
	ArrayList<Availability> teacherAvailability = new ArrayList<Availability>();
	ArrayList<Integer> teacherIDS = new ArrayList<Integer>();
	
	//array for holding the shifts an employee cannot work
	ArrayList[] unavailableShifts = new ArrayList[1000];
	String weekStartString;
	String weekEndString;
	
	//An object for generating an Optaplanner xml contraint file
	public xmlKing()
	{
		
	}
	
	//get passed the user data
	public void retrieveUserData(ArrayList<User> user)
	{
		teachers.addAll(user);
		
		System.out.println("Successfully passed " + teachers.size() + " teachers to the KING");
	}
	
	//get passed the availability data
	public void retrieveChildAvailabilityData(ArrayList<Availability> avail)
	{
		childAvailability.addAll(avail);
		
		System.out.println("Successfully passed " + childAvailability.size() + " child availability data chunks to the KING");
		
	}
	
	public void retrieveTeacherAvailabilityData(ArrayList<Availability> avail)
	{
		teacherAvailability.addAll(avail);
		
		System.out.println("Successfully passed " + teacherAvailability.size() + " teacher availability data chunks to the KING");
		
	}
	
	//get passed the child data
	public void retrieveChildData(ArrayList<Child> child)
	{
		children.addAll(child);
		
		System.out.println("Successfully passed " + children.size() + " children to the KING");
	}
	
	public ArrayList<String> getMondayShifts()
	{
		return mondayShifts;
	}
	
	public ArrayList<String> getTuesdayShfits()
	{
		return tuesdayShifts;
	}
	
	public ArrayList<String> getWednesdayShfits()
	{
		return wednesdayShifts;
	}
	
	public ArrayList<String> getThursdayShfits()
	{
		return thursdayShifts;
	}
	
	public ArrayList<String> getFridayShfits()
	{
		return fridayShifts;
	}
	
	public void setWeekStart(String s)
	{
		weekStartString = s;
	}
	
	public String getWeekStart()
	{
		return weekStartString;
	}
	
	public HashMap getDescriptionMap()
	{
		return ids2AgeGroup;
	}
	
	public void doItAll(String weekStart)
	{
		weekStartString = weekStart;
		//weekStartString = "2015-04-06";
		
		int delimIndex = weekStart.indexOf("-");
		String dateYear = weekStart.substring(0, delimIndex);
		String newWeekStart = weekStart.substring(delimIndex + 1);
		delimIndex = newWeekStart.indexOf("-");
		String dateMonth = newWeekStart.substring(0, delimIndex);
		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth), Integer.parseInt(dateDay));
		date.add(Calendar.DAY_OF_MONTH, + 7);
		
		int dateMonthInt = date.get(Calendar.MONTH);
		String dateMonthString = new Integer(dateMonthInt).toString();
		if(dateMonthString.length() < 2)
		{
			System.out.println("fixing the date month");
			dateMonthString = "0" + dateMonthString;
		}
		
		int dateDayInt = date.get(Calendar.DAY_OF_MONTH);
		String dateDayString = new Integer(dateDayInt).toString();
		if(dateDayString.length() < 2)
			dateDayString = "0" + dateDayString;
		
		weekEndString = date.get(Calendar.YEAR) + "-" + dateMonthString + "-" + dateDayString;
		
		System.out.println(weekEndString);
		
		//get the most recent availability object for each teacher
		//TODO: retrieve actual availabilities based on weekstart
		//remove all availabilities that are not for the week we want
		/*
		ArrayList<Availability> availabilitiesToRemove = new ArrayList<Availability>();
		for(Availability a : teacherAvailability)
		{
			if(a.getWeekstart().toString().compareTo(weekStart) != 0)
			{
				System.out.println("Removing " + a.getWeekstart().toString() +" as it is not " + weekStart);
				availabilitiesToRemove.add(a);
			}
		}
		teacherAvailability.removeAll(availabilitiesToRemove);
		*/
		//TODO: is this needed?
		/*
		if(!teacherAvailability.isEmpty())
		{
			weekStartString = teacherAvailability.get(0).getWeekstart().toString();
			long weekEndLong = teacherAvailability.get(0).getWeekstart().getTime() + 604800000;
			Date weekEndDate = new Date();
			weekEndDate.setTime(weekEndLong);
			System.out.println("Start date is " + weekStartString);
			System.out.println("End date is " + weekEndDate.toString() );
			
		}
		*/
		
		//get everything read for XML writing
		calculateNeededTeachers();
		
			for(int j = 0; j < 7; j++)
			{
				createNeededShifts(j);
			}
		
		//TODO: add all monday shifts to the same retainer as before
		//TODO: when writing the shifts to xml change description based on age group
		setUnavailableShifts();
		
		//make the XML file
		generateStartingXML();
		generateSkillsXML();
		generateShiftXML();
		generateUnwantedPatternsXML();
		generateEmployeeContractsXML();
		generateEmployeeXML();
		generateCoverRequirementsXML();
		finalizeXML();
		
		
	}
	
	
	//calculates the total number of teachers needed per quarter hour
	//switching things around to proccess one availability object at a time would probably be 
	//faster than proccessing all the availability for each quarter hour at a time
	//however for the size of our dataset it should not be an issue
	public void calculateNeededTeachers()
	{
		//iterate through the children availabilities
		for(int availIndex = 0; availIndex < childAvailability.size(); availIndex++)
		{
			Availability a = childAvailability.get(availIndex);
			//iterate through the days of the week
			for(int i = 0; i < 5; i++)
			{
				String availString;
				
				switch(i)
				{
				case 0:
					availString = a.getMonhours();
					break;

				case 1:
					availString = a.getTuehours();
					break;

				case 2:
					availString = a.getWedhours();
					break;

				case 3:
					availString = a.getThuhours();
					break;

				case 4:
					availString = a.getFrihours();
					break;	
					
				default:
					availString = "ERROR LULZ";
					break;
				}
				
				
				//iterate through the quarter hours of the day
				for(int j = 0; j < 48; j++)
				{
					//System.out.println(availString);
					String hour = availString.substring(j, j+1);
					//if the child is present for the hour add one to the corresponding bucket
					if(hour.compareTo("1") == 0)
					{
						Child childOfInterest = children.get(availIndex);
						
						Date bday = childOfInterest.getBirthday();
						Date currentTime = new Date();
						Date sept1 = new Date();
						sept1.setMonth(8);
						sept1.setDate(1);
						
						
						long difference = bday.getTime() - currentTime.getTime();
						long difference2 = bday.getTime() - sept1.getTime();
						//convert to hours
						difference = (-1)*difference/3600000;	
						difference2 = (-1)*difference2/3600000;
						//convert to days
						difference = difference/24;
						difference2 = difference2/24;
						//convert to years
						double age = difference/365.25;
						double ageAtSept1 = difference2/365.25;
						
						if(age < .67)
							babySproutsPresent[i][j]++;
						else if(age < 1.25)
						{
							miniSproutsPresent[i][j]++;
						}
						else if(age < 2.0)
						{
							peeweeSproutsPresent[i][j]++;
						}
						else if(age < 2.75)
							mightySproutsPresent[i][j]++;
						else if(age < 3.5)
							halfpintSproutsPresent[i][j]++;
						else if(ageAtSept1 < 4.0)
							halfpintSproutsPresent[i][j]++;
						else if(ageAtSept1 < 5.0)
							juniorSproutsPresent[i][j]++;
						else
							seniorSproutsPresent[i][j]++;
						
					}
					else
						continue;
				}
				
			}
		}
		
		//divide the childrenPresent arrays by the appropriate weight ratio
		//this will yield the number of teachers needed per group per quarter hour per day
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 48; j++)
			{
				babySproutTeachersNeeded[i][j] = (int) Math.ceil((babySproutsPresent[i][j])*0.25);
				miniSproutTeachersNeeded[i][j] = (int) Math.ceil((miniSproutsPresent[i][j])*0.167);
				peeweeSproutTeachersNeeded[i][j] = (int) Math.ceil((peeweeSproutsPresent[i][j])*0.125);
				mightySproutTeachersNeeded[i][j] = (int) Math.ceil((mightySproutsPresent[i][j])*0.1);
				halfpintSproutTeachersNeeded[i][j] = (int) Math.ceil((halfpintSproutsPresent[i][j])*0.077);
				juniorSproutTeachersNeeded[i][j] = (int) Math.ceil((juniorSproutsPresent[i][j])*0.059);
				seniorSproutTeachersNeeded[i][j] = (int) Math.ceil((seniorSproutsPresent[i][j])*0.056);
			}
		
		//sum up all the group array to get the total number of teachers needed per quarter hour per day
		/*
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 48; j++)
				totalSproutTeachersNeeded[i][j] = babySproutTeachersNeeded[i][j] + miniSproutTeachersNeeded[i][j] + peeweeSproutTeachersNeeded[i][j]
					+ mightySproutTeachersNeeded[i][j] + halfpintSproutTeachersNeeded[i][j] + juniorSproutTeachersNeeded[i][j] + seniorSproutTeachersNeeded[i][j];
		
		
		for(int i = 0; i < 5;i++)
		{
			System.out.println("Teachers needed for day " + i + ": ");
			for(int j = 0; j < 48; j++)
				System.out.print(totalSproutTeachersNeeded[i][j] + "-");
			System.out.println();
		}
		*/
		
	}
	
	//creates shifts that meet the total number of teachers required per quarter hour
	public void createNeededShifts(int group)
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
		
		int[][] specificTeachersNeeded = new int[5][50];
		
		switch(group)
		{
			case 0:
				specificTeachersNeeded = babySproutTeachersNeeded;
				break;
			case 1:
				specificTeachersNeeded = miniSproutTeachersNeeded;
				break;
			case 2:
				specificTeachersNeeded = peeweeSproutTeachersNeeded;
				break;
			case 3:
				specificTeachersNeeded = mightySproutTeachersNeeded;
				break;
			case 4:
				specificTeachersNeeded = halfpintSproutTeachersNeeded;
				break;
			case 5:
				specificTeachersNeeded = juniorSproutTeachersNeeded;
				break;
			case 6: 
				specificTeachersNeeded = seniorSproutTeachersNeeded;
				break;
		}
		
		
		//first we make some long shifts which end at the end of day
		//we do this because.
		for(int i = 0; i < 5; i++)
		{
			for(int j = 47; j >= 42; j--)
			{
				
				//how many 6 hour shifts may we add?
				int maxNumberToAdd = specificTeachersNeeded[i][j];
				for(int z = j; z >= j-24; z--)
				{
					if(specificTeachersNeeded[i][z] < maxNumberToAdd)
						maxNumberToAdd = specificTeachersNeeded[i][z];
				}
				
				//System.out.println(j);
				
				//if we can add the shift(s) then do it
				//and update the totalSproutTeachersNeeded while you're at it
				if(maxNumberToAdd >= 1)
				{
					Integer startIndex = j-24;
					Integer endIndex = j;
					
					for(int wow = 0; wow < maxNumberToAdd; wow++)
					{
						String shift = startIndex.toString() + "-" + endIndex.toString();
					}

					//System.out.println("Adding " + maxNumberToAdd + " 6 hour shifts from " + startIndex.toString() + " to " + endIndex.toString() + " on day " + i);

					for(int cow = j; cow >= j-24; cow--)
						specificTeachersNeeded[i][cow] -= maxNumberToAdd;
				}
				else
				{
					//System.out.println("Could not add at index " + j + " as totalSproutTeachersNeeded[i][j] = " + totalSproutTeachersNeeded[i][j]);
				}
				
				//System.out.println("Next up is " + i + "-" + (j-1));

			}
		}

		
		
		//then make all shifts as long as possible
		for(int i = 0; i < 5; i ++)
			for(int j = 0; j < 48; j++)
			{
				//do we need more teachers starting at this bucket?
				if(specificTeachersNeeded[i][j] > 0)
				{
					//start a shift for the required number of teachers
					for(int b = 0; b < specificTeachersNeeded[i][j]; b++)
					{
						if(i == 0)
							System.out.println("We need " + specificTeachersNeeded[i][j] + " teachers starting at hour " + j);
						
						boolean done = false;
						int counter = 0;
						int startTime = j;
						int endTime = j;
						
						while(!done)
						{
							counter++;
							
							//is the teacher still needed at the next hour?
							if(j == 47)
								endTime = j;
							else if(specificTeachersNeeded[i][j+counter] > 0)
							{
								//"schedule" them
								specificTeachersNeeded[i][j+counter]--;
							}
							//the teacher is not needed, end the shift
							else
							{
								endTime = j + counter - 1;
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
		
			System.out.println("The shifts for monday group " + group + " before balacing:");
			for(int i = 0; i < tempShifts[0].size(); i++)
				System.out.println(tempShifts[0].get(i));
		
		ArrayList<String> shiftsToAdd = new ArrayList<String>();
		ArrayList<String> shiftsToDestroy = new ArrayList<String>();
		
		//next handle the stubby shifts of the form "x-x"
		for(int dayIndex = 0; dayIndex < 5; dayIndex++)
		{
			for(int fixIndex = 0; fixIndex < tempShifts[dayIndex].size(); fixIndex++)
			{
				//parse out the start and end time
				String stubShift = (String) tempShifts[dayIndex].get(fixIndex);
				int delimIndex = stubShift.indexOf('-');
				Integer startTimeFix = Integer.parseInt(stubShift.substring(0, delimIndex));
				Integer endTimeFix = Integer.parseInt(stubShift.substring(delimIndex+1));
				
				//do we have a stub?
				if(startTimeFix == endTimeFix)
				{
					System.out.println("We have found a stub: " + stubShift);
					
					boolean done = false;
					
					//look for a shift that we can end early
					for(int stealIndex = 0; stealIndex < tempShifts[dayIndex].size(); stealIndex++)
					{
						if(stealIndex == fixIndex || done)
							continue;
						
						System.out.println("We are trying to fix the stub " + stubShift + " at index " + stealIndex);
						
						//parse out the start and end time
						String stealShift = (String) tempShifts[dayIndex].get(stealIndex);
						int stealDelimIndex = stealShift.indexOf('-');
						int startTimeSteal = Integer.parseInt(stealShift.substring(0, stealDelimIndex));
						int endTimeSteal = Integer.parseInt(stealShift.substring(stealDelimIndex+1));
						
						//is the shift able to end early?
						if(((startTimeFix-startTimeSteal) >= 8) && (endTimeSteal - startTimeFix) >= 8)
						{
							//end the shift early
							Integer startTimeStealInteger = startTimeSteal;
							Integer endTimeStealInteger = endTimeSteal;
							Integer startTimeFixInteger = startTimeFix;
							Integer endTimeFixInteger = endTimeFix;
							Integer startTimeFixPlusOneInteger = startTimeFix + 1;
							String newStealShift = startTimeStealInteger.toString() + "-" + startTimeFixPlusOneInteger.toString();
							
							//start a new shift
							String newFixShift = startTimeFixInteger.toString() + "-" + endTimeStealInteger.toString();
							
							//remove the old shifts
							//tempShifts[dayIndex].remove(stealIndex);
							//tempShifts[dayIndex].remove(fixIndex-1);
							shiftsToDestroy.add((String) tempShifts[dayIndex].get(stealIndex));
							shiftsToDestroy.add((String) tempShifts[dayIndex].get(fixIndex));
							
							//add the new shift
							//tempShifts[dayIndex].add(newStealShift);
							//tempShifts[dayIndex].add(newFixShift);
							shiftsToAdd.add(newStealShift);
							shiftsToAdd.add(newFixShift);
							
							if(dayIndex == 0)
								System.out.println("Replaced the shifts " + stealShift + "  " + stubShift + " with " + newStealShift + "  " + newFixShift);
							
							done = true;
						}
						
						if(!done)
						{
							System.out.println("We are unable to fix the stub due to whatever reasons. Dropping it.");
							shiftsToDestroy.add(stubShift);
						}
						
					}
					
					
					
				}
				else
				{
					continue;
				}
			}
			
			for(String st : shiftsToDestroy)
			{
				tempShifts[dayIndex].remove(st);
			}
			
			for(String st : shiftsToAdd)
			{
				tempShifts[dayIndex].add(st);
			}
			
			shiftsToDestroy.clear();
			shiftsToAdd.clear();
		}
		
		
		shiftsToAdd = new ArrayList<String>();
		shiftsToDestroy = new ArrayList<String>();
				
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
				int endTimeFix = Integer.parseInt(shift.substring(delimIndex+1));

				//check if the shift is shorter than 2 hours
				//skip the fragmented shifts of the form x-x as we handle this specifically later on
				if((endTimeFix-startTimeFix) < 8 && (endTimeFix-startTimeFix != 0))
				{
					//if(dayIndex == 0)
						//System.out.println("The shift " + shift + " is going to get balanced as it is length " + (endTimeFix-startTimeFix));
					boolean done = false;
					System.out.println("We are trying to fix the shift " + startTimeFix + "-" + endTimeFix + " on day " + dayIndex + " group " + group);
					
					//get the amount of time needed to make it 2 hours
					int quarterHoursNeeded = 8 - (endTimeFix-startTimeFix);
					
					//check if there is a shift that overlaps that can lose the quarterHoursNeeded and still be longer than 2 hours
					for(int stealIndex = 0; stealIndex < tempShifts[dayIndex].size(); stealIndex++)
					{
						//skip the shift we are working on
						if(stealIndex == fixIndex || done)
							continue;
						
						//parse out the start and end time
						String shiftSteal = (String) tempShifts[dayIndex].get(stealIndex);
						int delimIndexSteal = shiftSteal.indexOf('-');
						int startTimeSteal = Integer.parseInt(shiftSteal.substring(0, delimIndexSteal));
						int endTimeSteal = Integer.parseInt(shiftSteal.substring(delimIndexSteal + 1));
						
						boolean beforeAdjacent = false;
						boolean afterAdjacent = false;
						boolean overlapping = false;
						
						//is the shift overlapping or adjacent?
						/*
						if(!(endTimeSteal == startTimeFix || endTimeFix == startTimeSteal))
						{
							//System.out.println(endTimeSteal + "!=" + startTimeFix + " and " + endTimeFix + "!=" + startTimeSteal);
							continue;
						}
						*/
						if(endTimeSteal+1 == startTimeFix)
						{
							System.out.println("Trying to fix with a beforeadjacent");
							beforeAdjacent = true;
						}
						else if(startTimeSteal == endTimeFix+1)
						{
							System.out.println("Trying to fix with a afteradjacent");
							afterAdjacent = true;
						}
						else if((startTimeSteal < startTimeFix) && (endTimeFix < endTimeSteal))
						{
							System.out.println("Trying to fix with a overlap");
							overlapping = true;
						}
						
						if(!beforeAdjacent && !afterAdjacent && !overlapping)
						{
							System.out.println("Cannot steal from " + startTimeSteal + "-" + endTimeSteal);
							continue;
						}
						
						//can we steal the quarterHoursNeeded?
						if((endTimeSteal - startTimeSteal) >= (8 + quarterHoursNeeded))
						{
							//System.out.println("We have found a valid victim to steal from: " + shiftSteal);
							
							//are we stealing hours from the end of the shift?
							if(beforeAdjacent)
							{
								//steal the quarterHoursNeeded
								int newTimeIndex = endTimeSteal - quarterHoursNeeded;
								
								//remove the old shifts
								shiftsToDestroy.add((String) tempShifts[dayIndex].get(stealIndex));
								shiftsToDestroy.add((String) tempShifts[dayIndex].get(fixIndex));
								//tempShifts[dayIndex].remove(stealIndex);
								//tempShifts[dayIndex].remove(fixIndex-1);
								
								//add the updated shifts
								Integer startTimeStealInt = startTimeSteal;
								Integer newTimeIndexInt = newTimeIndex;
								String newSteal = startTimeStealInt.toString() + "-" + newTimeIndexInt.toString();
								//tempShifts[dayIndex].add(newSteal);
								shiftsToAdd.add(newSteal);
								
								Integer endTimeFixInt = endTimeFix;
								String newFix = newTimeIndexInt.toString() + "-" + endTimeFixInt.toString();
								//tempShifts[dayIndex].add(newFix);
								shiftsToAdd.add(newFix);
								
								System.out.println("We have fixed with a before adjacent");
								
								done = true;
							}
							
							//are we stealing hours from the start of the shift?
							if(afterAdjacent)
							{
								//steal the quarterHoursNeeded
								int newTimeIndex = startTimeSteal + quarterHoursNeeded;
								
								//remove the old shifts
								//tempShifts[dayIndex].remove(stealIndex);
								//tempShifts[dayIndex].remove(fixIndex);
								shiftsToDestroy.add((String) tempShifts[dayIndex].get(stealIndex));
								shiftsToDestroy.add((String) tempShifts[dayIndex].get(fixIndex));
								//add the updated shifts
								Integer endTimeStealInt = endTimeSteal;
								Integer newTimeIndexInt = newTimeIndex;
								String newSteal = newTimeIndexInt.toString() + "-" + endTimeStealInt.toString();
								//tempShifts[dayIndex].add(newSteal);
								shiftsToAdd.add(newSteal);
								
								Integer startTimeFixInt = startTimeFix;
								String newFix = startTimeFixInt.toString() + "-" + newTimeIndexInt.toString();
								//tempShifts[dayIndex].add(newFix);
								shiftsToAdd.add(newFix);
								
								System.out.println("We have fixed with an after adjacent");
								
								done = true;
							}
						}
						//are we trying to steal the hours from an overlapping shift?
						if(overlapping)
						{
							//what window are we looking at?
							int overallStart = startTimeSteal;
							int overallEnd  = endTimeSteal;
							int changeOver = (overallStart + overallEnd) / 2;
							
							shiftsToDestroy.add((String) tempShifts[dayIndex].get(stealIndex));
							shiftsToDestroy.add((String) tempShifts[dayIndex].get(fixIndex));
							
							Integer overallStartInt = overallStart;
							Integer overallEndInt = overallEnd;
							Integer changeOverInt = changeOver;
							
							shiftsToAdd.add(overallStartInt.toString() + "-" + changeOverInt.toString());
							shiftsToAdd.add(changeOverInt.toString() + "-" + overallEndInt.toString());
							
							System.out.println("We have fixed with an overlap");
							
							done = true;
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
			
			//update the shifts for the day we are currently working on
			for(String st : shiftsToDestroy)
			{
				tempShifts[dayIndex].remove(st);
			}
			
			//update the shifts for the day we are currently working on
			for(String st : shiftsToAdd)
			{
				tempShifts[dayIndex].add(st);
			}
			
			shiftsToDestroy.clear();
			shiftsToAdd.clear();
		}
				
		//store them shifts
		
		switch(group)
		{
			case 0:
				mondayShiftsBaby.addAll(tempShifts[0]);
				tuesdayShiftsBaby.addAll(tempShifts[1]);
				wednesdayShiftsBaby.addAll(tempShifts[2]);
				thursdayShiftsBaby.addAll(tempShifts[3]);
				fridayShiftsBaby.addAll(tempShifts[4]);
				break;
			case 1:
				mondayShiftsMini.addAll(tempShifts[0]);
				tuesdayShiftsMini.addAll(tempShifts[1]);
				System.out.println("We ahve made " + tuesdayShiftsMini.size() + " mini shifts");
				wednesdayShiftsMini.addAll(tempShifts[2]);
				thursdayShiftsMini.addAll(tempShifts[3]);
				fridayShiftsMini.addAll(tempShifts[4]);
				break;
			case 2:
				mondayShiftsPeewee.addAll(tempShifts[0]);
				tuesdayShiftsPeewee.addAll(tempShifts[1]);
				System.out.println("We ahve made " + tuesdayShiftsPeewee.size() + " mini shifts");
				wednesdayShiftsPeewee.addAll(tempShifts[2]);
				thursdayShiftsPeewee.addAll(tempShifts[3]);
				fridayShiftsPeewee.addAll(tempShifts[4]);
				break;
			case 3:
				mondayShiftsMighty.addAll(tempShifts[0]);
				tuesdayShiftsMighty.addAll(tempShifts[1]);
				wednesdayShiftsMighty.addAll(tempShifts[2]);
				thursdayShiftsMighty.addAll(tempShifts[3]);
				fridayShiftsMighty.addAll(tempShifts[4]);
				break;
			case 4:
				mondayShiftsHalfpint.addAll(tempShifts[0]);
				tuesdayShiftsHalfpint.addAll(tempShifts[1]);
				wednesdayShiftsHalfpint.addAll(tempShifts[2]);
				thursdayShiftsHalfpint.addAll(tempShifts[3]);
				fridayShiftsHalfpint.addAll(tempShifts[4]);
				break;
			case 5:
				mondayShiftsJunior.addAll(tempShifts[0]);
				tuesdayShiftsJunior.addAll(tempShifts[1]);
				wednesdayShiftsJunior.addAll(tempShifts[2]);
				thursdayShiftsJunior.addAll(tempShifts[3]);
				fridayShiftsJunior.addAll(tempShifts[4]);
				break;
			case 6:
				mondayShiftsSenior.addAll(tempShifts[0]);
				tuesdayShiftsSenior.addAll(tempShifts[1]);
				wednesdayShiftsSenior.addAll(tempShifts[2]);
				thursdayShiftsSenior.addAll(tempShifts[3]);
				fridayShiftsSenior.addAll(tempShifts[4]);
				break;
		}
		
	}
	
	//finds the shifts that each employee is unable to work
	public void setUnavailableShifts()
	{
		//add all shifts of each day to the same container for convienence
		mondayShifts.addAll(mondayShiftsBaby);
		mondayShifts.addAll(mondayShiftsMini);
		mondayShifts.addAll(mondayShiftsPeewee);
		mondayShifts.addAll(mondayShiftsHalfpint);
		mondayShifts.addAll(mondayShiftsMighty);
		mondayShifts.addAll(mondayShiftsJunior);
		mondayShifts.addAll(mondayShiftsSenior);
		
		tuesdayShifts.addAll(tuesdayShiftsBaby);
		tuesdayShifts.addAll(tuesdayShiftsMini);
		tuesdayShifts.addAll(tuesdayShiftsPeewee);
		tuesdayShifts.addAll(tuesdayShiftsHalfpint);
		tuesdayShifts.addAll(tuesdayShiftsMighty);
		tuesdayShifts.addAll(tuesdayShiftsJunior);
		tuesdayShifts.addAll(tuesdayShiftsSenior);

		wednesdayShifts.addAll(wednesdayShiftsBaby);
		wednesdayShifts.addAll(wednesdayShiftsMini);
		wednesdayShifts.addAll(wednesdayShiftsPeewee);
		wednesdayShifts.addAll(wednesdayShiftsHalfpint);
		wednesdayShifts.addAll(wednesdayShiftsMighty);
		wednesdayShifts.addAll(wednesdayShiftsJunior);
		wednesdayShifts.addAll(wednesdayShiftsSenior);
		
		thursdayShifts.addAll(thursdayShiftsBaby);
		thursdayShifts.addAll(thursdayShiftsMini);
		thursdayShifts.addAll(thursdayShiftsPeewee);
		thursdayShifts.addAll(thursdayShiftsHalfpint);
		thursdayShifts.addAll(thursdayShiftsMighty);
		thursdayShifts.addAll(thursdayShiftsJunior);
		thursdayShifts.addAll(thursdayShiftsSenior);
		
		fridayShifts.addAll(fridayShiftsBaby);
		fridayShifts.addAll(fridayShiftsMini);
		fridayShifts.addAll(fridayShiftsPeewee);
		fridayShifts.addAll(fridayShiftsHalfpint);
		fridayShifts.addAll(fridayShiftsMighty);
		fridayShifts.addAll(fridayShiftsJunior);
		fridayShifts.addAll(fridayShiftsSenior);
		
		for(int i = 0; i < 1000; i++)
		{
			unavailableShifts[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i< teacherAvailability.size(); i++)
		{
			//get the current availability object
			Availability avail = teacherAvailability.get(i);
			ArrayList<Integer> shifts = new ArrayList<Integer>();
			//shifts.clear();
				
			//loop through all of the shifts
			//if we can work the shift set a 0, if we cannot set a 1
			String availabilityString = avail.getMonhours();
			for(int j = 0; j < mondayShifts.size(); j++)
			{
				String string = mondayShifts.get(j);
				int delimIndex = string.indexOf("-");
				Integer startIndex = Integer.valueOf(string.substring(0, delimIndex));
				Integer endIndex = Integer.valueOf(string.substring(delimIndex+1));
				
				//check if the user is available for the whole shift
				String shiftString = availabilityString.substring(startIndex, endIndex);
				//if the shift string contains any zeros then the shift cannot be worked
				if(shiftString.indexOf("0") != -1)
				{
					//set the unavailable shift
					//since we are working on the monday shifts we should add 100 to j to get our shift ID
					//unavailableShifts[avail.getId()][100+j] = 1;
					shifts.add(100+j);
				}
				else
				{
					//unavailableShifts[avail.getId()][100+j] = 0;
					//shifts.add(100+j);
				}
			}
			
			availabilityString = avail.getTuehours();
			for(int j = 0; j < tuesdayShifts.size(); j++)
			{
				String string = tuesdayShifts.get(j);
				int delimIndex = string.indexOf("-");
				Integer startIndex = Integer.valueOf(string.substring(0, delimIndex));
				Integer endIndex = Integer.valueOf(string.substring(delimIndex+1));
				
				//check if the user is available for the whole shift
				String shiftString = availabilityString.substring(startIndex,endIndex);
				//if the shift string contains any zeros then the shift cannot be worked
				if(shiftString.indexOf("0") != -1)
				{
					//set the unavailable shift
					//unavailableShifts[avail.getId()][200 + j] = 1;
					shifts.add(200+j);
				}
				else
				{
					//unavailableShifts[avail.getId()][200 + j] = 0;
					//shifts.add(200+j);
				}
			}
			
			availabilityString = avail.getWedhours();
			for(int j = 0; j < wednesdayShifts.size(); j++)
			{
				String string = wednesdayShifts.get(j);
				int delimIndex = string.indexOf("-");
				Integer startIndex = Integer.valueOf(string.substring(0, delimIndex));
				Integer endIndex = Integer.valueOf(string.substring(delimIndex+1));
				
				//check if the user is available for the whole shift
				String shiftString = availabilityString.substring(startIndex,endIndex);
				//if the shift string contains any zeros then the shift cannot be worked
				if(shiftString.indexOf("0") != -1)
				{
					//set the unavailable shift
					//unavailableShifts[avail.getId()][300 + j] = 1;
					shifts.add(300+j);
				}
				else
				{
					//unavailableShifts[avail.getId()][300 + j] = 0;
					//shifts.add(300+j);
				}
			}
			
			availabilityString = avail.getThuhours();
			for(int j = 0; j < thursdayShifts.size(); j++)
			{
				String string = thursdayShifts.get(j);
				int delimIndex = string.indexOf("-");
				Integer startIndex = Integer.valueOf(string.substring(0, delimIndex));
				Integer endIndex = Integer.valueOf(string.substring(delimIndex+1));
				
				//check if the user is available for the whole shift
				String shiftString = availabilityString.substring(startIndex,endIndex);
				//if the shift string contains any zeros then the shift cannot be worked
				if(shiftString.indexOf("0") != -1)
				{
					//set the unavailable shift
					//unavailableShifts[avail.getId()][400 + j] = 1;
					shifts.add(400+j);
				}
				else
				{
					//unavailableShifts[avail.getId()][400 + j] = 0;
					//shifts.add(400+j);
				}
			}
			
			availabilityString = avail.getFrihours();
			for(int j = 0; j < fridayShifts.size(); j++)
			{
				String string = fridayShifts.get(j);
				int delimIndex = string.indexOf("-");
				Integer startIndex = Integer.valueOf(string.substring(0, delimIndex));
				Integer endIndex = Integer.valueOf(string.substring(delimIndex+1));
				
				//check if the user is available for the whole shift
				String shiftString = availabilityString.substring(startIndex,endIndex);
				//if the shift string contains any zeros then the shift cannot be worked
				if(shiftString.indexOf("0") != -1)
				{
					//set the unavailable shift
					//unavailableShifts[avail.getId()][500 + j] = 1;
					shifts.add(500+j);
				}
				else
				{
					//unavailableShifts[avail.getId()][500 + j] = 0;
					//shifts.add(500+j);
				}
			}
			
			//add that sauce to more permanent storage
			if(shifts.isEmpty())
				continue;
			else
			{
				unavailableShifts[avail.getId()].addAll(shifts);
			}
			
			
		}
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
			startDate.appendChild(doc.createTextNode(weekStartString));
			endDate.appendChild(doc.createTextNode(weekEndString));
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
		
		int counter = 0;
		//add monday shifts
		for(int group = 0; group < 7; group++)
		{
			ArrayList<String> shiftsToWrite = new ArrayList<String>();
			String description = "default";
			
			switch(group)
			{
				case 0:
					shiftsToWrite.addAll(mondayShiftsBaby);
					description = "Baby";
					break;
				case 1:
					shiftsToWrite.addAll(mondayShiftsMini);
					description = "Mini";
					break;
				case 2:
					shiftsToWrite.addAll(mondayShiftsPeewee);
					description = "Peewee";
					break;
				case 3:
					shiftsToWrite.addAll(mondayShiftsMighty);
					description = "Mighty";
					break;
				case 4:
					shiftsToWrite.addAll(mondayShiftsHalfpint);
					description = "Halfpint";
					break;
				case 5:
					shiftsToWrite.addAll(mondayShiftsJunior);
					description = "Junior";
					break;
				case 6:
					shiftsToWrite.addAll(mondayShiftsSenior);
					description = "Senior";
					break;
			}
			
			for(int i = 0; i < shiftsToWrite.size(); i++)
			{
				
				//get the shift id
				//monday = 1 for first digit
				//if ID = 119, then mondayShifts[19]
				Integer shiftID = 100 + counter;
				counter++;
				
				System.out.println("Mapping shift id " + shiftID + " to " + description);
				ids2AgeGroup.put(shiftID, description);

				//create the shift xml
				Element shift = doc.createElement("Shift");
				shift.setAttribute("ID", shiftID.toString());

				//get the start index and end index
				String shiftString = shiftsToWrite.get(i);
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
				Element descriptionElement = doc.createElement("Description");
				descriptionElement.appendChild(doc.createTextNode(description));
				Element skills = doc.createElement("Skills");
				Element skill = doc.createElement("Skill");
				skill.appendChild(doc.createTextNode("Teacher"));
				shift.appendChild(startTimeElement);
				shift.appendChild(endTimeElement);
				shift.appendChild(descriptionElement);
				skills.appendChild(skill);
				shift.appendChild(skills);
				shiftType.appendChild(shift);
			}
		}
		
		counter = 0;
		//add tuesday shifts
		for(int group = 0; group < 7; group++)
		{
			ArrayList<String> shiftsToWrite = new ArrayList<String>();
			String description = "default";
			
			switch(group)
			{
				case 0:
					shiftsToWrite.addAll(tuesdayShiftsBaby);
					description = "Baby";
					break;
				case 1:
					shiftsToWrite.addAll(tuesdayShiftsMini);
					description = "Mini";
					break;
				case 2:
					shiftsToWrite.addAll(tuesdayShiftsPeewee);
					description = "Peewee";
					break;
				case 3:
					shiftsToWrite.addAll(tuesdayShiftsMighty);
					description = "Mighty";
					break;
				case 4:
					shiftsToWrite.addAll(tuesdayShiftsHalfpint);
					description = "Halfpint";
					break;
				case 5:
					shiftsToWrite.addAll(tuesdayShiftsJunior);
					description = "Junior";
					break;
				case 6:
					shiftsToWrite.addAll(tuesdayShiftsSenior);
					description = "Senior";
					break;
			}
			
			for(int i = 0; i < shiftsToWrite.size(); i++)
			{
				//get the shift id
				//tuesday = 2 for first digit
				//if ID = 208, then tuesdayShifts[8]
				Integer shiftID = 200 + counter;
				counter++;
				
				System.out.println("Mapping shift id " + shiftID + " to " + description);
				ids2AgeGroup.put(shiftID, description);

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
				Element descriptionElement = doc.createElement("Description");
				descriptionElement.appendChild(doc.createTextNode(description));
				Element skills = doc.createElement("Skills");
				Element skill = doc.createElement("Skill");
				skill.appendChild(doc.createTextNode("Teacher"));
				shift.appendChild(startTimeElement);
				shift.appendChild(endTimeElement);
				shift.appendChild(descriptionElement);
				skills.appendChild(skill);
				shift.appendChild(skills);
				shiftType.appendChild(shift);
			}
		}
		
		
		counter = 0;
		for(int group = 0; group < 7; group++)
		{
			ArrayList<String> shiftsToWrite = new ArrayList<String>();
			String description = "default";

			switch(group)
			{
			case 0:
				shiftsToWrite.addAll(wednesdayShiftsBaby);
				description = "Baby";
				break;
			case 1:
				shiftsToWrite.addAll(wednesdayShiftsMini);
				description = "Mini";
				break;
			case 2:
				shiftsToWrite.addAll(wednesdayShiftsPeewee);
				description = "Peewee";
				break;
			case 3:
				shiftsToWrite.addAll(wednesdayShiftsMighty);
				description = "Mighty";
				break;
			case 4:
				shiftsToWrite.addAll(wednesdayShiftsHalfpint);
				description = "Halfpint";
				break;
			case 5:
				shiftsToWrite.addAll(wednesdayShiftsJunior);
				description = "Junior";
				break;
			case 6:
				shiftsToWrite.addAll(wednesdayShiftsSenior);
				description = "Senior";
				break;
			}

			//add wednesday shifts
			for(int i = 0; i < shiftsToWrite.size(); i++)
			{
				//get the shift id
				//wednesday = 3 for first digit
				//if ID = 312, then wednesdayShifts[12]
				Integer shiftID = 300 + counter;
				counter++;
				
				System.out.println("Mapping shift id " + shiftID + " to " + description);
				ids2AgeGroup.put(shiftID, description);
				
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
				Element descriptionElement = doc.createElement("Description");
				descriptionElement.appendChild(doc.createTextNode(description));
				Element skills = doc.createElement("Skills");
				Element skill = doc.createElement("Skill");
				skill.appendChild(doc.createTextNode("Teacher"));
				shift.appendChild(startTimeElement);
				shift.appendChild(endTimeElement);
				shift.appendChild(descriptionElement);
				skills.appendChild(skill);
				shift.appendChild(skills);
				shiftType.appendChild(shift);
			}
		}

		
		counter = 0;
		for(int group = 0; group < 7; group++)
		{
			ArrayList<String> shiftsToWrite = new ArrayList<String>();
			String description = "default";

			switch(group)
			{
			case 0:
				shiftsToWrite.addAll(thursdayShiftsBaby);
				description = "Baby";
				break;
			case 1:
				shiftsToWrite.addAll(thursdayShiftsMini);
				description = "Mini";
				break;
			case 2:
				shiftsToWrite.addAll(thursdayShiftsPeewee);
				description = "Peewee";
				break;
			case 3:
				shiftsToWrite.addAll(thursdayShiftsMighty);
				description = "Mighty";
				break;
			case 4:
				shiftsToWrite.addAll(thursdayShiftsHalfpint);
				description = "Halfpint";
				break;
			case 5:
				shiftsToWrite.addAll(thursdayShiftsJunior);
				description = "Junior";
				break;
			case 6:
				shiftsToWrite.addAll(thursdayShiftsSenior);
				description = "Senior";
				break;
			}

			//add thursday shifts
			for(int i = 0; i < shiftsToWrite.size(); i++)
			{
				//get the shift id
				//thursday = 4 for first digit
				//if ID = 400, then thursdayShifts[0]
				Integer shiftID = 400 + counter;
				counter++;
				
				System.out.println("Mapping shift id " + shiftID + " to " + description);
				ids2AgeGroup.put(shiftID, description);
				
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
				Element descriptionElement = doc.createElement("Description");
				descriptionElement.appendChild(doc.createTextNode(description));
				Element skills = doc.createElement("Skills");
				Element skill = doc.createElement("Skill");
				skill.appendChild(doc.createTextNode("Teacher"));
				shift.appendChild(startTimeElement);
				shift.appendChild(endTimeElement);
				shift.appendChild(descriptionElement);
				skills.appendChild(skill);
				shift.appendChild(skills);
				shiftType.appendChild(shift);
			}
		}

		counter = 0;
		for(int group = 0; group < 7; group++)
		{
			ArrayList<String> shiftsToWrite = new ArrayList<String>();
			String description = "default";

			switch(group)
			{
			case 0:
				shiftsToWrite.addAll(fridayShiftsBaby);
				description = "Baby";
				break;
			case 1:
				shiftsToWrite.addAll(fridayShiftsMini);
				description = "Mini";
				break;
			case 2:
				shiftsToWrite.addAll(fridayShiftsPeewee);
				description = "Peewee";
				break;
			case 3:
				shiftsToWrite.addAll(fridayShiftsMighty);
				description = "Mighty";
				break;
			case 4:
				shiftsToWrite.addAll(fridayShiftsHalfpint);
				description = "Halfpint";
				break;
			case 5:
				shiftsToWrite.addAll(fridayShiftsJunior);
				description = "Junior";
				break;
			case 6:
				shiftsToWrite.addAll(fridayShiftsSenior);
				description = "Senior";
				break;
			}

			//add friday shifts
			for(int i = 0; i < shiftsToWrite.size(); i++)
			{
				//get the shift id
				//friday = 5 for first digit
				//if ID = 517, then fridayShifts[17]
				Integer shiftID = 500 + counter;
				counter++;
				
				System.out.println("Mapping shift id " + shiftID + " to " + description);
				ids2AgeGroup.put(shiftID, description);

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
				Element descriptionElement = doc.createElement("Description");
				descriptionElement.appendChild(doc.createTextNode(description));
				Element skills = doc.createElement("Skills");
				Element skill = doc.createElement("Skill");
				skill.appendChild(doc.createTextNode("Teacher"));
				shift.appendChild(startTimeElement);
				shift.appendChild(endTimeElement);
				shift.appendChild(descriptionElement);
				skills.appendChild(skill);
				shift.appendChild(skills);
				shiftType.appendChild(shift);
			}
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
			pattern.setAttribute("weight", "1");
			
			
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
			pattern.setAttribute("weight", "1");


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
			pattern.setAttribute("weight", "1");


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

		//System.out.println("There are " + thursdayShifts.size() + " shifts on Thursday");
		//generate patterns for all thursday shifts
		for(int i = 0; i < thursdayShifts.size(); i++)
		{
			Integer id = 400 + i;
			Element pattern = doc.createElement("Pattern");
			pattern.setAttribute("ID", id.toString());
			pattern.setAttribute("weight", "1");


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
			pattern.setAttribute("weight", "1");


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
		Element contracts = doc.createElement("Contracts");
		schedulingPeriod.appendChild(contracts);
		
		//make a unique contract for each employee
		//this is probably overkill as some contracts could be reused
		//I don't care though
		for(int i = 0; i < teachers.size(); i++)
		{
			
			User u = teachers.get(i);
			Integer intI = u.getId();
			Element contract = doc.createElement("Contract");
			contract.setAttribute("ID", intI.toString());
			
			Element description = doc.createElement("Description");
			description.appendChild(doc.createTextNode(u.getFirstname() + " " + u.getLastname() + "'s Contract"));
			contract.appendChild(description);
			
			Element SingleAssignmentPerDay = doc.createElement("SingleAssignmentPerDay");
			SingleAssignmentPerDay.setAttribute("weight", "1");
			SingleAssignmentPerDay.appendChild(doc.createTextNode("true"));
			contract.appendChild(SingleAssignmentPerDay);
			
			Element MaxNumAssignments = doc.createElement("MaxNumAssignments");
			MaxNumAssignments.setAttribute("on", "1");
			MaxNumAssignments.setAttribute("weight", "1");
			MaxNumAssignments.appendChild(doc.createTextNode("5"));
			contract.appendChild(MaxNumAssignments);
			
			Element MinNumAssignments = doc.createElement("MinNumAssignments");
			MinNumAssignments.setAttribute("on", "1");
			MinNumAssignments.setAttribute("weight", "1");
			
			//make a couple "full time employees"
			if(intI < 4)
			{
				MinNumAssignments.appendChild(doc.createTextNode("4"));
				contract.appendChild(MinNumAssignments);			}
			else
			{
				MinNumAssignments.appendChild(doc.createTextNode("1"));
			}
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
			MaxConsecutiveFreeDays.appendChild(doc.createTextNode("8"));
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
			
			//add all the of patterns that the employee cannot work on monday
			for(int j = 0; j < mondayShifts.size(); j++)
			{
				if(unavailableShifts[u.getId()].indexOf(100 + j) != -1)
				{
					Element pattern = doc.createElement("Pattern");
					pattern.appendChild(doc.createTextNode(new Integer(100 + j).toString()));
					UnwantedPatterns.appendChild(pattern);
				}
			}
			//add all the of patterns that the employee cannot work on tuesday
			for(int j = 0; j < tuesdayShifts.size(); j++)
			{
				if(unavailableShifts[u.getId()].indexOf(200 + j) != -1)
				{
					Element pattern = doc.createElement("Pattern");
					pattern.appendChild(doc.createTextNode(new Integer(200 + j).toString()));
					UnwantedPatterns.appendChild(pattern);
				}
			}
			//add all the of patterns that the employee cannot work on wednesday
			for(int j = 0; j < wednesdayShifts.size(); j++)
			{
				if(unavailableShifts[u.getId()].indexOf(300 + j) != -1)
				{
					Element pattern = doc.createElement("Pattern");
					pattern.appendChild(doc.createTextNode(new Integer(300 + j).toString()));
					UnwantedPatterns.appendChild(pattern);
				}
			}
			//add all the of patterns that the employee cannot work on thursday
			for(int j = 0; j < thursdayShifts.size(); j++)
			{
				if(unavailableShifts[u.getId()].indexOf(400 + j) != -1)
				{
					Element pattern = doc.createElement("Pattern");
					pattern.appendChild(doc.createTextNode(new Integer(400 + j).toString()));
					UnwantedPatterns.appendChild(pattern);
				}
			}
			//add all the of patterns that the employee cannot work on friday
			for(int j = 0; j < thursdayShifts.size(); j++)
			{
				if(unavailableShifts[u.getId()].indexOf(500 + j) != -1)
				{
					Element pattern = doc.createElement("Pattern");
					pattern.appendChild(doc.createTextNode(new Integer(500 + j).toString()));
					UnwantedPatterns.appendChild(pattern);
				}
			}
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
		//are the ids here correct?
		for(int i = 0; i < teachers.size(); i++)
		{
			
			User u = teachers.get(i);
			Integer IntI = u.getId();
			Element Employee = doc.createElement("Employee");
			Employee.setAttribute("ID", IntI.toString());
			Employees.appendChild(Employee);
			
			Element ContractID = doc.createElement("ContractID");
			ContractID.appendChild(doc.createTextNode(IntI.toString()));
			Employee.appendChild(ContractID);
			
			Element Name = doc.createElement("Name");
			Name.appendChild(doc.createTextNode(u.getFirstname() + " " + u.getLastname()));
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
			String weekStart = teacherAvailability.get(0).getWeekstart().toString();
			//String filePath = "D:\\Student Data\\Desktop\\optaplanner-distribution-6.1.0.Final\\examples\\data\\nurserostering\\unsolved\\" + weekStart + ".xml";
			//StreamResult result = new StreamResult(new File(filePath));
			String filePath = "C:\\" + weekStartString + ".xml";
			//StreamResult result = new StreamResult(new File("C:\\2015-04-06.xml"));
			StreamResult result = new StreamResult(new File(filePath));
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
