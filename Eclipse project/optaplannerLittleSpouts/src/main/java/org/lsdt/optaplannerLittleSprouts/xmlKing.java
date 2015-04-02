package org.lsdt.optaplannerLittleSprouts;

import java.util.ArrayList;

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
	String[] mondayShifts = new String[30];
	String[] tuesdayShifts = new String[30];
	String[] wednesdayShifts = new String[30];
	String[] thursdayShifts = new String[30];
	String[] fridayShifts = new String[30];
	
	
	
	//the children stored in the database
	//TODO: replace with actual data
	Child[] children = new Child[100];
	
	
	
	
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
		tempShifts[1] = tempMondayShifts;
		ArrayList<String> tempWednesdayShifts = new ArrayList<String>();
		tempShifts[2] = tempMondayShifts;
		ArrayList<String> tempThurdayShifts = new ArrayList<String>();
		tempShifts[3] = tempMondayShifts;
		ArrayList<String> tempFridayShifts = new ArrayList<String>();
		tempShifts[4] = tempMondayShifts;
		
		
		
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
				int startTime = Integer.parseInt(shift.substring(0, delimIndex));
				int endTime = Integer.parseInt(shift.substring(delimIndex));
				//check if the shift is shorter than 2 hours
				if((endTime-startTime) < 8)
				{
					//get the amount of time needed to make it 2 hours
					int quarterHoursNeeded = 8 - (endTime-startTime);
					
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
							if(endTimeSteal == startTime)
							{
								//steal the quarterHoursNeeded
							}
							
							//are we stealing hours from the start of the shift?
							if(startTimeSteal == endTime)
							{
								//steal the quarterHoursNeeded
							}
						}
						
					}
					
				}
				//the shift is longer than 2 hours (no worries)
				else
					continue;
			}
		}
	}
	
	//finds the shifts that each employee is unable to work
	public void setUnavailableShifts()
	{
		
	}
	
	//creates the xml document
	//inputs header data and the start-end date xml
	public void generateStartingXML()
	{
		//header data
		//start end date
	}
	
	//inputs the skills xml block
	public void generateSkillsXML()
	{
		
	}
	
	//inputs the shift type xml block
	public void generateShiftTypeXML()
	{
		
	}
	
	//inputs the patterns xml block
	//these patterns are all unwanted patterns for the contract block
	public void generateUnwantedPatternsXML()
	{
		
	}
	
	//inputs the contract block xml
	//each employee gets their own contract that uses their unwanted patterns
	public void generateEmployeeContractsXML()
	{
		
	}
	
	//inputs employee xml block
	public void generateEmployeeXML()
	{
		
	}
	
	//inputs the cover requirements xml block
	public void generateCoverRequirementsXML()
	{
		
	}
	
	//inputs closing cml tags
	//finishes writing to the file
	//verifies the xml content
	public void finalizeXML()
	{
		//closing tags
		//verification
	}
}
