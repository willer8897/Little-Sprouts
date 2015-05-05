/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.common.swingui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.lsdt.optaplannerLittleSprouts.Availability;
import org.lsdt.optaplannerLittleSprouts.Child;
import org.lsdt.optaplannerLittleSprouts.Schedule;
import org.lsdt.optaplannerLittleSprouts.User;
import org.optaplanner.core.api.score.FeasibilityScore;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lowagie.text.Document;

public class SolverAndPersistenceFrame extends JFrame {

    protected final transient Logger logger = LoggerFactory.getLogger(getClass());

    public static final ImageIcon OPTA_PLANNER_ICON = new ImageIcon(
            SolverAndPersistenceFrame.class.getResource("optaPlannerIcon.png"));

    private final String titlePrefix;
    private final SolutionBusiness solutionBusiness;

    private SolutionPanel solutionPanel;
    private ConstraintMatchesDialog constraintMatchesDialog;

    private JPanel quickOpenUnsolvedPanel;
    private List<Action> quickOpenUnsolvedActionList;
    private JPanel quickOpenSolvedPanel;
    private List<Action> quickOpenSolvedActionList;
    private Action openAction;
    private Action saveAction;
    private Action importAction;
    private Action exportAction;
    private JCheckBox refreshScreenDuringSolvingCheckBox;
    private Action solveAction;
    private JButton solveButton;
    private Action terminateSolvingEarlyAction;
    private JButton terminateSolvingEarlyButton;
    private JPanel middlePanel;
    private JProgressBar progressBar;
    private JTextField scoreField;
    private ShowConstraintMatchesDialogAction showConstraintMatchesDialogAction;
    
    private boolean interrupted = false;
    private static SessionFactory factory;
    
    private ArrayList<String> mondayShifts = new ArrayList<String>();
    private ArrayList<String> tuesdayShifts = new ArrayList<String>();
    private ArrayList<String> wednesdayShifts = new ArrayList<String>();
    private ArrayList<String> thursdayShifts = new ArrayList<String>();
    private ArrayList<String> fridayShifts = new ArrayList<String>();
    private String weekStart = "";
    private HashMap assignmentMap = new HashMap();
    private HashMap shiftDescriptionMap = new HashMap();
    
    

    public SolverAndPersistenceFrame(SolutionBusiness solutionBusiness, SolutionPanel solutionPanel,
            String titlePrefix) {
        super(titlePrefix);
        this.titlePrefix = titlePrefix;
        this.solutionBusiness = solutionBusiness;
        this.solutionPanel = solutionPanel;
        setIconImage(OPTA_PLANNER_ICON.getImage());
        solutionPanel.setSolutionBusiness(solutionBusiness);
        solutionPanel.setSolverAndPersistenceFrame(this);
        registerListeners();
        constraintMatchesDialog = new ConstraintMatchesDialog(this, solutionBusiness);
        
        //processOutput();
    }

    private void registerListeners() {
        solutionBusiness.registerForBestSolutionChanges(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // This async, so it doesn't stop the solving immediately
            	interrupted = true;
                solutionBusiness.terminateSolvingEarly();
            }
        });
    }

    public void bestSolutionChanged() {
        Solution solution = solutionBusiness.getSolution();
        if (refreshScreenDuringSolvingCheckBox.isSelected()) {
            solutionPanel.updatePanel(solution);
            validate(); // TODO remove me?
        }
        scoreField.setForeground(determineScoreFieldForeground(solutionBusiness.getScore()));
        scoreField.setText("Latest best score: " + solution.getScore());
    }

    public void init(Component centerForComponent) {
        setContentPane(createContentPane());
        pack();
        setLocationRelativeTo(centerForComponent);
    }

    private JComponent createContentPane() {
        JComponent quickOpenPanel = createQuickOpenPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createToolBar(), BorderLayout.NORTH);
        mainPanel.add(createMiddlePanel(), BorderLayout.CENTER);
        mainPanel.add(createImageOverlay(), BorderLayout.CENTER);
        mainPanel.add(createScorePanel(), BorderLayout.SOUTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, quickOpenPanel, mainPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.2);
        //return slitPane
        
        
        return mainPanel;
    }
    
    private JComponent createImageOverlay() {
    	//TODO: make this work to make things look nice
    	JLabel label = new JLabel("The schedule is being computed. This can take up to 20 minutes.");
    	return label;
    }

    private JComponent createQuickOpenPanel() {
        JPanel quickOpenPanel = new JPanel(new BorderLayout());
        quickOpenPanel.add(new JLabel("Quick open"), BorderLayout.NORTH);
        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
          //      createQuickOpenUnsolvedPanel(), createQuickOpenSolvedPanel());
        //splitPane.setResizeWeight(0.8);
        //splitPane.setBorder(null);
        //quickOpenPanel.add(splitPane, BorderLayout.CENTER);
        //return createQuickOpenUnsolvedPanel();
        return quickOpenPanel;
    }

    private JComponent createQuickOpenUnsolvedPanel() {
        quickOpenUnsolvedPanel = new JPanel();
        
        quickOpenUnsolvedActionList = new ArrayList<Action>();
        //return quickOpenUnsolvedPanel;
        
        List<File> unsolvedFileList = solutionBusiness.getUnsolvedFileList();
        
        return createQuickOpenPanel(quickOpenUnsolvedPanel, "Unsolved (XStream XML)", quickOpenUnsolvedActionList,
               unsolvedFileList);
    }

    private JComponent createQuickOpenSolvedPanel() {
        quickOpenSolvedPanel = new JPanel();
        quickOpenSolvedActionList = new ArrayList<Action>();
 
        
        List<File> solvedFileList = solutionBusiness.getSolvedFileList();
        return createQuickOpenPanel(quickOpenSolvedPanel, "Solved (XStream XML)", quickOpenSolvedActionList,
               solvedFileList);
    }

    private JComponent createQuickOpenPanel(JPanel panel, String title, List<Action> quickOpenActionList, List<File> fileList) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       // refreshQuickOpenPanel(panel, quickOpenActionList, fileList);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setMinimumSize(new Dimension(100, 80));
        // Size fits into screen resolution 1024*768
        scrollPane.setPreferredSize(new Dimension(180, 200));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(scrollPane, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createTitledBorder(title)));
        return titlePanel;
    }

    private void refreshQuickOpenPanel(JPanel panel, List<Action> quickOpenActionList, List<File> fileList) {
        panel.removeAll();
        quickOpenActionList.clear();
        if (fileList.isEmpty()) {
            JLabel noneLabel = new JLabel("None");
            noneLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            panel.add(noneLabel);
        } else {
            for (File file : fileList) {
                //Action quickOpenAction = new QuickOpenAction(file);
                //quickOpenActionList.add(quickOpenAction);
                //JButton quickOpenButton = new JButton(quickOpenAction);
                //quickOpenButton.setHorizontalAlignment(SwingConstants.LEFT);
                //quickOpenButton.setMargin(new Insets(0, 0, 0, 0));
                //panel.add(quickOpenButton);
            }
        }
    }

    private class QuickOpenAction extends AbstractAction {

        private File file;

        public QuickOpenAction(File file) {
            super(file.getName());
            this.file = file;
        }

        public void actionPerformed(ActionEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
            	//TODO: is this even kosher?
                solutionBusiness.openSolution(file);
            	//solutionBusiness.openSolution(new File("C:\\2015-04-06.xml"));
                setSolutionLoaded();
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }

    }

    private JComponent createToolBar() {
        JToolBar toolBar = new JToolBar("File operations");
        toolBar.setFloatable(false);

        importAction = new ImportAction();
        importAction.setEnabled(solutionBusiness.hasImporter());
        //toolBar.add(new JButton(importAction));
        openAction = new OpenAction();
        openAction.setEnabled(true);
        //toolBar.add(new JButton(openAction));
        saveAction = new SaveAction();
        saveAction.setEnabled(false);
        //toolBar.add(new JButton(saveAction));
        exportAction = new ExportAction();
        exportAction.setEnabled(false);
        //toolBar.add(new JButton(exportAction));
        toolBar.addSeparator();

        progressBar = new JProgressBar(0, 100);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        toolBar.add(progressBar);
        toolBar.addSeparator();

        solveAction = new SolveAction();
        solveAction.setEnabled(false);
        solveButton = new JButton(solveAction);
        terminateSolvingEarlyAction = new TerminateSolvingEarlyAction();
        terminateSolvingEarlyAction.setEnabled(false);
        terminateSolvingEarlyButton = new JButton(terminateSolvingEarlyAction);
        terminateSolvingEarlyButton.setVisible(false);
        toolBar.add(solveButton, "solveAction");
        toolBar.add(terminateSolvingEarlyButton, "terminateSolvingEarlyAction");
        solveButton.setMinimumSize(terminateSolvingEarlyButton.getMinimumSize());
        solveButton.setPreferredSize(terminateSolvingEarlyButton.getPreferredSize());
        return toolBar;
    }
   

    private class SolveAction extends AbstractAction {

        public SolveAction() {
            super("Solve", new ImageIcon(SolverAndPersistenceFrame.class.getResource("solveAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            setSolvingState(true);
            Solution planningProblem = solutionBusiness.getSolution();
            new SolveWorker(planningProblem).execute();
           
        }

    }
    
    public void processOutput()
    {
        try {
            
        	System.out.println("Doing the stuff");
        	String fileString = "C://" + weekStart + "solution.xml";
            //File fXmlFile = new File("C://2015-04-06solution.xml");
        	File fXmlFile = new File(fileString);
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
         
        	//optional, but recommended
        	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        	doc.getDocumentElement().normalize();
         
        	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         
        	NodeList nList = doc.getElementsByTagName("Employee");
         
        	System.out.println("----------------------------");
        	
        	//maps employee ids to employee ids in the DB
        	HashMap employeeMap = new HashMap();
        	
        	//maps shift reference ids to a genuine shift id
        	HashMap shiftMap = new HashMap();
        	
        	//
        	HashMap id2typeMap = new HashMap();
         
        	for (int temp = 0; temp < nList.getLength(); temp++) {
         
        		Node nNode = nList.item(temp);
         
        		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
         
        			Element eElement = (Element) nNode;
        			//System.out.println("Hello from " + eElement.getAttribute("id"));
         
        			//System.out.println("Actual ID : " + eElement.getElementsByTagName("code").item(0).getTextContent());
        			employeeMap.put(eElement.getAttribute("id").toString(), eElement.getElementsByTagName("code").item(0).getTextContent());
        			System.out.println("Mapping employee reference id " + eElement.getAttribute("id") + " to " + eElement.getElementsByTagName("code").item(0).getTextContent());
         
        		}
        	}
        	
        	NodeList shiftTypeList = doc.getElementsByTagName("shiftType");
        	for (int temp = 0; temp < shiftTypeList.getLength(); temp++)
        	{
        		Node nNode = shiftTypeList.item(temp);
        		
        		if(nNode.getNodeType() == Node.ELEMENT_NODE)
        		{
        			Element shiftType = (Element) nNode;
        			
        			//make sure it is a shift type declaration
        			NodeList list = shiftType.getElementsByTagName("code");
        			if(list.getLength() == 0)
        				continue;
        			
        			shiftMap.put(shiftType.getAttribute("id"), list.item(0).getTextContent());
        			System.out.println("Mapping shift type " + shiftType.getAttribute("id") + " to " + list.item(0).getTextContent());
        			
        			Integer wut = Integer.parseInt(shiftType.getAttribute("id"))-1;
        			System.out.println("Mapping shift type " + wut + " to " + list.item(0).getTextContent());
        			shiftMap.put(wut, list.item(0).getTextContent());
        		}
        	}
        	
        	NodeList shiftList = doc.getElementsByTagName("Shift");
        	for (int temp = 0; temp < shiftList.getLength(); temp++)
        	{
        		Node nNode = shiftList.item(temp);
        		
        		if(nNode.getNodeType() == Node.ELEMENT_NODE)
        		{
        			Element shift = (Element) nNode;
        			
        			//make sure it has a shift type declaration
        			NodeList list = shift.getElementsByTagName("shiftType");
        			if(list.getLength() == 0)
        				continue;
        			
        			if(list.item(0).getNodeType() != Node.ELEMENT_NODE)
        				continue;
        			
        			Element shiftType = (Element) list.item(0);
        			
        			if(shiftType.hasAttribute("reference"))
        			{
        				id2typeMap.put(shift.getAttribute("id"), shiftType.getAttribute("reference"));
            			
            			System.out.println("Mapping shift id " + shift.getAttribute("id") + " to type id " + shiftType.getAttribute("reference"));
        			}
        			else
        			{
        				NodeList codeList = shiftType.getElementsByTagName("code");
        				
        				if(codeList.getLength() == 0)
        					continue;
        				if(codeList.item(0).getNodeType() == Node.ELEMENT_NODE)
        				{
        					Element codeElement = (Element) codeList.item(0);
        					
        					id2typeMap.put(shift.getAttribute("id"), codeElement.getTextContent());
        					
        					System.out.println("Mapping shift id " + shift.getAttribute("id") + " to type id " + codeElement.getTextContent());
        				}
        			}
        			
        			
        			
        		}
        	}
        	
        	NodeList assignmentList = doc.getElementsByTagName("ShiftAssignment");
        	for(int temp = 0; temp < assignmentList.getLength(); temp ++)
        	{
        		Node nNode = assignmentList.item(temp);
        		
        		if(nNode.getNodeType() == Node.ELEMENT_NODE)
        		{
        			Element assignment = (Element) nNode;
        			
        			NodeList list1 = assignment.getElementsByTagName("shift");
        			NodeList list2 = assignment.getElementsByTagName("employee");
        			if(list1.getLength() == 0 || list2.getLength() == 0)
        				continue;
        			
        			if(list1.item(0).getNodeType() != Node.ELEMENT_NODE || list2.item(0).getNodeType() != Node.ELEMENT_NODE)
        				continue;
        			
        			Element shift = (Element) list1.item(0);
        			Element employee = (Element) list2.item(0);
        			
        			//System.out.println("The employee id " + employee.getAttribute("reference") + " maps to " + employeeMap.get(employee.getAttribute("reference")));
        			
        			//System.out.println("The shift reference ID is " + shift.getAttribute("reference"));
        			String shiftReference = shift.getAttribute("reference");
        			String shiftType = id2typeMap.get(shiftReference).toString();
        			
        			//System.out.println("The shiftType ID " + shiftType + " maps to " + shiftMap.get(id2typeMap.get(shiftReference)));

        			if(shiftMap.get(shiftType) == null)
        				continue;
        			
        			int shiftTypeInt = Integer.parseInt(shiftType);
        			//System.out.println("shift type int is " + shiftTypeInt);
        			
        			if(shiftMap.get(shiftType) != null)
        			{	
            			//now retrieve that actual employee and shift id
            			//System.out.println("Employee " + employeeMap.get(employee.getAttribute("reference")) + " is working the shift " + shiftMap.get(shiftType));
            		
            			//if(assignmentMap.containsValue(shiftMap.get(shiftType)))
            				//assignmentMap.values().remove(shiftMap.get(shiftType));
            			
            			assignmentMap.put(shiftMap.get(shiftType), employeeMap.get(employee.getAttribute("reference")));
        			}

        		}
        	}
        	
        	Iterator it = assignmentMap.keySet().iterator();
        	
        	while(it.hasNext())
        	{
        		String shift = (String) it.next();
        		
        		String employee = assignmentMap.get(shift).toString();
        		
        		System.out.println(" Employee " + employee + " is working " + shift);
        		
        		
        		//Now write that garbage to the DB
        		
            	//Test DB connectivity
            	String hibernatePropsFilePath = "D:\\Student Data\\Desktop\\optaplanner-distribution-6.1.0.Final\\examples\\sources\\src\\main\\resources\\hibernate.cfg.xml";
            	File hibernatePropsFile = new File(hibernatePropsFilePath);

            	Configuration configuration = new Configuration();
            	configuration.configure(hibernatePropsFile);

            	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
            			configuration.getProperties()).build();

        		
        		try{
            		factory = configuration.buildSessionFactory(serviceRegistry);
            	}catch (Throwable ex) { 
            		System.err.println("Failed to create sessionFactory object." + ex);
            		throw new ExceptionInInitializerError(ex); 
            	}
            	//Print all users
            	Session session = factory.openSession();
            	Transaction tx = null;
            	try{
            		//tx = session.beginTransaction();
            		session.beginTransaction();
            		
            		
            		Schedule s = new Schedule();
            		s.setId(Integer.parseInt(employee));
            		
            		//String ageGroup = ;
            		//TODO: put em in rooms
            		
            		String description = shiftDescriptionMap.get(Integer.parseInt(shift)).toString();
            		
            		if(description.compareTo("Baby") == 0)
            			s.setRoom(1);
            		else if(description.compareTo("Mini") == 0)
            			s.setRoom(2);
            		else if(description.compareTo("Peewee") == 0)
            			s.setRoom(3);
            		else if(description.compareTo("Mighty") == 0)
            			s.setRoom(4);
            		else if(description.compareTo("Halfpint") == 0)
            			s.setRoom(5);
            		else if(description.compareTo("Junior") == 0)
            			s.setRoom(6);
            		else if(description.compareTo("Senior") == 0)
            			s.setRoom(7);
            		else
            			s.setRoom(11);
            		
            		//get the time start and end
            		Integer shiftId = Integer.parseInt(shift);
            		if(shiftId < 200)
            		{
            			//get monday shift
            			int shiftKey = shiftId - 100;
            			String mondayShift = mondayShifts.get(shiftKey);
            			
            			int delimIndex = mondayShift.indexOf("-");
            			int startIndex = Integer.parseInt(mondayShift.substring(0, delimIndex));
            			int endIndex = Integer.parseInt(mondayShift.substring(delimIndex + 1));
            			
            			//System.out.println("start index " + startIndex);
            			//System.out.println("end index" + endIndex);
            			
            			//convert to proper time format
            			
            			Integer startHour = 600 + (Math.floorDiv(startIndex, 4)*100);
            			Integer startMinute = (startIndex%4)*15;
            			Integer startTimeInteger = startHour + startMinute;
            			String startTime = startTimeInteger.toString();
            			
            			Integer endHour = 600 + (Math.floorDiv(endIndex, 4)*100);
            			Integer endMinutes = (endIndex%4)*15;
            			Integer endTimeInteger = endHour + endMinutes;
            			String endTime = endTimeInteger.toString();
            			
            			if(startTime.length() < 4)
            				startTime = "0" + startTime;
            			
            			s.setTime_start(startTime);
            			s.setTime_end(endTime);
            			
            			//set the proper date
                		//System.out.println("Our weekstart is " + weekStart);
                		delimIndex = weekStart.indexOf("-");
                		String dateYear = weekStart.substring(0, delimIndex);
                		String newWeekStart = weekStart.substring(delimIndex + 1);
                		delimIndex = newWeekStart.indexOf("-");
                		String dateMonth = newWeekStart.substring(0, delimIndex);
                		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
                		System.out.println("Date Month is " + dateMonth);
                		//System.out.println("Creating a date with day " + dateDay + " and month " + dateMonth + " and year " + dateYear);
                		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth)- 1, Integer.parseInt(dateDay));
                		System.out.println(date.getTime());
                		s.setDate(date.getTime());
            		}
            		else if(shiftId < 300)
            		{
            			//get tuesday shift
            			int shiftKey = shiftId - 200;
            			String tuesdayShift = tuesdayShifts.get(shiftKey);
            			
            			int delimIndex = tuesdayShift.indexOf("-");
            			int startIndex = Integer.parseInt(tuesdayShift.substring(0, delimIndex));
            			int endIndex = Integer.parseInt(tuesdayShift.substring(delimIndex + 1));
            			
            			//System.out.println("start index " + startIndex);
            			//System.out.println("end index" + endIndex);
            			
            			//convert to proper time format
            			
            			Integer startHour = 600 + (Math.floorDiv(startIndex, 4)*100);
            			Integer startMinute = (startIndex%4)*15;
            			Integer startTimeInteger = startHour + startMinute;
            			String startTime = startTimeInteger.toString();
            			
            			Integer endHour = 600 + (Math.floorDiv(endIndex, 4)*100);
            			Integer endMinutes = (endIndex%4)*15;
            			Integer endTimeInteger = endHour + endMinutes;
            			String endTime = endTimeInteger.toString();
            			
            			if(startTime.length() < 4)
            				startTime = "0" + startTime;
            			
            			s.setTime_start(startTime);
            			s.setTime_end(endTime);
            			
            			//set the proper date
                		//System.out.println("Our weekstart is " + weekStart);
                		delimIndex = weekStart.indexOf("-");
                		String dateYear = weekStart.substring(0, delimIndex);
                		String newWeekStart = weekStart.substring(delimIndex + 1);
                		delimIndex = newWeekStart.indexOf("-");
                		String dateMonth = newWeekStart.substring(0, delimIndex);
                		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
                		
                		//System.out.println("Creating a date with day " + dateDay + " and month " + dateMonth + " and year " + dateYear);
                		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth) - 1, Integer.parseInt(dateDay));
                		date.add(Calendar.DAY_OF_MONTH, +1);
                		//System.out.println(date.getTime());
                		s.setDate(date.getTime());
            		}
            		else if(shiftId < 400)
            		{
            			//get wednesday shift
            			int shiftKey = shiftId - 300;
            			String wednesdayShift = wednesdayShifts.get(shiftKey);
            			
            			int delimIndex = wednesdayShift.indexOf("-");
            			int startIndex = Integer.parseInt(wednesdayShift.substring(0, delimIndex));
            			int endIndex = Integer.parseInt(wednesdayShift.substring(delimIndex + 1));
            			
            			//System.out.println("start index " + startIndex);
            			//System.out.println("end index" + endIndex);
            			
            			//convert to proper time format
            			
            			Integer startHour = 600 + (Math.floorDiv(startIndex, 4)*100);
            			Integer startMinute = (startIndex%4)*15;
            			Integer startTimeInteger = startHour + startMinute;
            			String startTime = startTimeInteger.toString();
            			
            			Integer endHour = 600 + (Math.floorDiv(endIndex, 4)*100);
            			Integer endMinutes = (endIndex%4)*15;
            			Integer endTimeInteger = endHour + endMinutes;
            			String endTime = endTimeInteger.toString();
            			
            			if(startTime.length() < 4)
            				startTime = "0" + startTime;
            			
            			s.setTime_start(startTime);
            			s.setTime_end(endTime);
            			
            			//set the proper date
                		//System.out.println("Our weekstart is " + weekStart);
                		delimIndex = weekStart.indexOf("-");
                		String dateYear = weekStart.substring(0, delimIndex);
                		String newWeekStart = weekStart.substring(delimIndex + 1);
                		delimIndex = newWeekStart.indexOf("-");
                		String dateMonth = newWeekStart.substring(0, delimIndex);
                		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
                		
                		//System.out.println("Creating a date with day " + dateDay + " and month " + dateMonth + " and year " + dateYear);
                		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth) - 1, Integer.parseInt(dateDay));
                		date.add(Calendar.DAY_OF_MONTH, +2);
                		//System.out.println(date.getTime());
                		s.setDate(date.getTime());
            		}
            		else if(shiftId < 500)
            		{
            			//get thursday shift
            			int shiftKey = shiftId - 400;
            			String thursdayShift = thursdayShifts.get(shiftKey);
            			
            			int delimIndex = thursdayShift.indexOf("-");
            			int startIndex = Integer.parseInt(thursdayShift.substring(0, delimIndex));
            			int endIndex = Integer.parseInt(thursdayShift.substring(delimIndex + 1));
            			
            			//System.out.println("start index " + startIndex);
            			//System.out.println("end index" + endIndex);
            			
            			//convert to proper time format
            			
            			Integer startHour = 600 + (Math.floorDiv(startIndex, 4)*100);
            			Integer startMinute = (startIndex%4)*15;
            			Integer startTimeInteger = startHour + startMinute;
            			String startTime = startTimeInteger.toString();
            			
            			Integer endHour = 600 + (Math.floorDiv(endIndex, 4)*100);
            			Integer endMinutes = (endIndex%4)*15;
            			Integer endTimeInteger = endHour + endMinutes;
            			String endTime = endTimeInteger.toString();
            			
            			if(startTime.length() < 4)
            				startTime = "0" + startTime;
            			
            			s.setTime_start(startTime);
            			s.setTime_end(endTime);
            			
            			//set the proper date
                		//System.out.println("Our weekstart is " + weekStart);
                		delimIndex = weekStart.indexOf("-");
                		String dateYear = weekStart.substring(0, delimIndex);
                		String newWeekStart = weekStart.substring(delimIndex + 1);
                		delimIndex = newWeekStart.indexOf("-");
                		String dateMonth = newWeekStart.substring(0, delimIndex);
                		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
                		
                		//System.out.println("Creating a date with day " + dateDay + " and month " + dateMonth + " and year " + dateYear);
                		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth) - 1, Integer.parseInt(dateDay));
                		date.add(Calendar.DAY_OF_MONTH, +3);
                		//System.out.println(date.getTime());
                		s.setDate(date.getTime());
            		}
            		else
            		{
            			//get friday shift
            			int shiftKey = shiftId - 500;
            			String fridayShift = fridayShifts.get(shiftKey);
            			
            			int delimIndex = fridayShift.indexOf("-");
            			int startIndex = Integer.parseInt(fridayShift.substring(0, delimIndex));
            			int endIndex = Integer.parseInt(fridayShift.substring(delimIndex + 1));
            			
            			//System.out.println("start index " + startIndex);
            			//System.out.println("end index" + endIndex);
            			
            			//convert to proper time format
            			
            			Integer startHour = 600 + (Math.floorDiv(startIndex, 4)*100);
            			Integer startMinute = (startIndex%4)*15;
            			Integer startTimeInteger = startHour + startMinute;
            			String startTime = startTimeInteger.toString();
            			
            			Integer endHour = 600 + (Math.floorDiv(endIndex, 4)*100);
            			Integer endMinutes = (endIndex%4)*15;
            			Integer endTimeInteger = endHour + endMinutes;
            			String endTime = endTimeInteger.toString();
            			
            			if(startTime.length() < 4)
            				startTime = "0" + startTime;
            			
            			s.setTime_start(startTime);
            			s.setTime_end(endTime);
            			
            			//set the proper date
                		//System.out.println("Our weekstart is " + weekStart);
                		delimIndex = weekStart.indexOf("-");
                		String dateYear = weekStart.substring(0, delimIndex);
                		String newWeekStart = weekStart.substring(delimIndex + 1);
                		delimIndex = newWeekStart.indexOf("-");
                		String dateMonth = newWeekStart.substring(0, delimIndex);
                		String dateDay = newWeekStart.substring(newWeekStart.indexOf("-") + 1);
                		
                		//System.out.println("Creating a date with day " + dateDay + " and month " + dateMonth + " and year " + dateYear);
                		Calendar date = new GregorianCalendar(Integer.parseInt(dateYear), Integer.parseInt(dateMonth) - 1, Integer.parseInt(dateDay));
                		date.add(Calendar.DAY_OF_MONTH, +4);
                		//System.out.println(date.getTime());
                		s.setDate(date.getTime());
            		}
            		
            		
            		
            		
            		session.save(s);
            		
            		
            		session.getTransaction().commit();
            		//tx.commit();
            	}catch (HibernateException e) {
            		if (tx!=null) tx.rollback();
            		e.printStackTrace(); 
            	}finally {
            		session.close(); 
            	}
        		
        		
        		
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    protected class SolveWorker extends SwingWorker<Solution, Void> {

        protected final Solution planningProblem;

        public SolveWorker(Solution planningProblem) {
            this.planningProblem = planningProblem;
        }

        @Override
        protected Solution doInBackground() throws Exception {
            return solutionBusiness.solve(planningProblem);
        }

        @Override
        protected void done() {
            try {
                Solution bestSolution = get();
                solutionBusiness.setSolution(bestSolution);
            } 
            catch (InterruptedException e)
            {
                throw new IllegalStateException("Solving interrupted.", e);
            }
            catch (ExecutionException e) 
            {
                throw new IllegalStateException("Solving failed.", e.getCause());
            } 
            finally 
            {
                setSolvingState(false);
                resetScreen();
                System.out.println("Done");
                
                //TODO:
                //run heathers code here
            
                //TOO:
                //display a success screen
                
                if(!interrupted)
                {
                	String st = weekStart + "solution.xml";
                	solutionBusiness.saveSolution(new File("C:\\", st));
                	
                	processOutput();
                	
                	Object[] options = {"OK"};
                	int n = JOptionPane.showOptionDialog(null,
                			"Schedule Computed Successfully","Success!",
                			JOptionPane.PLAIN_MESSAGE,
                			JOptionPane.QUESTION_MESSAGE,
                			null,
                			options,
                			options[0]);

                	dispose();
                }
                interrupted = false;
                
            }
        }

    }
    
    public void getMondayShifts(ArrayList<String> shifts)
    {
    	for(int i = 0; i < shifts.size(); i++)
    	{
    		mondayShifts.add(shifts.get(i));
    	}
    	
    	System.out.println("Solver and PErsistence has received " + mondayShifts.size() + " shifts for monday");
    	
    }
    
    public void getTuesdayShifts(ArrayList<String> shifts)
    {
    	for(int i = 0; i < shifts.size(); i++)
    	{
    		tuesdayShifts.add(shifts.get(i));
    	}
    	
    	System.out.println("Solver and PErsistence has received " + tuesdayShifts.size() + " shifts for tuesday");
    }
    
    public void getWednesdayShifts(ArrayList<String> shifts)
    {
    	for(int i = 0; i < shifts.size(); i++)
    	{
    		wednesdayShifts.add(shifts.get(i));
    	}
    	
    	System.out.println("Solver and PErsistence has received " + wednesdayShifts.size() + " shifts for wednesday");
    }
    
    public void getThursdayShifts(ArrayList<String> shifts)
    {
    	for(int i = 0; i < shifts.size(); i++)
    	{
    		thursdayShifts.add(shifts.get(i));
    	}
    	
    	System.out.println("Solver and PErsistence has received " + thursdayShifts.size() + " shifts for thursday");
    }
    
    public void getFridayShifts(ArrayList<String> shifts)
    {
    	for(int i = 0; i < shifts.size(); i++)
    	{
    		fridayShifts.add(shifts.get(i));
    	}
    	
    	System.out.println("Solver and PErsistence has received " + fridayShifts.size() + " shifts for friday");
    	
    	//processOutput();
    }
    
    public void getWeekStart(String week)
    {
    	weekStart = week;
    	System.out.println("Passed weekstart " + weekStart);
    	//processOutput();
    }
    
    public void getDescriptionMap(HashMap map)
    {
    	shiftDescriptionMap = map;
    	System.out.println("Passed description map with " + shiftDescriptionMap.size() + " mappings");
    	//processOutput();
    }
    


    private class TerminateSolvingEarlyAction extends AbstractAction {

        public TerminateSolvingEarlyAction() {
            super("Terminate solving early",
                    new ImageIcon(SolverAndPersistenceFrame.class.getResource("terminateSolvingEarlyAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
        	
            terminateSolvingEarlyAction.setEnabled(false);
            progressBar.setString("Terminating...");
            // This async, so it doesn't stop the solving immediately
            solutionBusiness.terminateSolvingEarly();
            
            interrupted = true;
            Object[] options = {"OK"};
            int n = JOptionPane.showOptionDialog(null,
                           "Schedule computation was interrupted. Please try again.","Failure!",
                           JOptionPane.PLAIN_MESSAGE,
                           JOptionPane.QUESTION_MESSAGE,
                           null,
                           options,
                           options[0]);
            
            dispose();
        }

    }

    private class OpenAction extends AbstractAction {

        private static final String NAME = "Open...";

        public OpenAction() {
            super(NAME, new ImageIcon(SolverAndPersistenceFrame.class.getResource("openAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(solutionBusiness.getSolvedDataDir());
            fileChooser.setFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().endsWith(".xml");
                }

                public String getDescription() {
                    return "Solution XStream XML files";
                }
            });
            fileChooser.setDialogTitle(NAME);
            int approved = fileChooser.showOpenDialog(SolverAndPersistenceFrame.this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    solutionBusiness.openSolution(fileChooser.getSelectedFile());
                    setSolutionLoaded();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }

    private class SaveAction extends AbstractAction {

        private static final String NAME = "Save as...";

        public SaveAction() {
            super(NAME, new ImageIcon(SolverAndPersistenceFrame.class.getResource("saveAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(solutionBusiness.getSolvedDataDir());
            fileChooser.setFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().endsWith(".xml");
                }

                public String getDescription() {
                    return "Solution XStream XML files";
                }
            });
            fileChooser.setDialogTitle(NAME);
            fileChooser.setSelectedFile(new File(solutionBusiness.getSolvedDataDir(),
                    FilenameUtils.getBaseName(solutionBusiness.getSolutionFileName()) + ".xml"));
            int approved = fileChooser.showSaveDialog(SolverAndPersistenceFrame.this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    solutionBusiness.saveSolution(fileChooser.getSelectedFile());
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
                refreshQuickOpenPanel(quickOpenUnsolvedPanel, quickOpenUnsolvedActionList,
                        solutionBusiness.getUnsolvedFileList());
                refreshQuickOpenPanel(quickOpenSolvedPanel, quickOpenSolvedActionList,
                        solutionBusiness.getSolvedFileList());
                SolverAndPersistenceFrame.this.validate();
            }
        }

    }

    private class ImportAction extends AbstractAction {

        private static final String NAME = "Import...";

        public ImportAction() {
            super(NAME, new ImageIcon(SolverAndPersistenceFrame.class.getResource("importAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(solutionBusiness.getImportDataDir());
            fileChooser.setFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory() || solutionBusiness.acceptImportFile(file);
                }

                public String getDescription() {
                    return "Import files (*." + solutionBusiness.getImportFileSuffix() + ")";
                }
            });
            fileChooser.setDialogTitle(NAME);
            int approved = fileChooser.showOpenDialog(SolverAndPersistenceFrame.this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    //solutionBusiness.importSolution(fileChooser.getSelectedFile());
                	String fileString = "C:\\" + weekStart + ".xml";
                	//solutionBusiness.importSolution(new File("C:\\2015-04-06.xml"));
                	solutionBusiness.importSolution(new File(fileString));
                    setSolutionLoaded();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }
    
    public void doTheDeed()
    {	
    	try {
            //solutionBusiness.importSolution(fileChooser.getSelectedFile());
        	//solutionBusiness.importSolution(new File("C:\\2015-04-06.xml"));
    		String fileString = "C:\\" + weekStart + ".xml";
    		solutionBusiness.importSolution(new File(fileString));
            setSolutionLoaded();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    	
    	//start solving

        setSolvingState(true);
        Solution planningProblem = solutionBusiness.getSolution();
        new SolveWorker(planningProblem).execute();

    }

    private class ExportAction extends AbstractAction {

        private static final String NAME = "Export as...";

        public ExportAction() {
            super(NAME, new ImageIcon(SolverAndPersistenceFrame.class.getResource("exportAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(solutionBusiness.getExportDataDir());
            fileChooser.setFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().endsWith("." + solutionBusiness.getExportFileSuffix());
                }
                public String getDescription() {
                    return "Export files (*." + solutionBusiness.getExportFileSuffix() + ")";
                }
            });
            fileChooser.setDialogTitle(NAME);
            fileChooser.setSelectedFile(new File(solutionBusiness.getExportDataDir(),
                    FilenameUtils.getBaseName(solutionBusiness.getSolutionFileName())
                            + "." + solutionBusiness.getExportFileSuffix()));
            int approved = fileChooser.showSaveDialog(SolverAndPersistenceFrame.this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    solutionBusiness.exportSolution(fileChooser.getSelectedFile());
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }

    private JPanel createMiddlePanel() {
        middlePanel = new JPanel(new CardLayout());
        ImageIcon usageExplanationIcon = new ImageIcon(getClass().getResource(solutionPanel.getUsageExplanationPath()));
        JLabel usageExplanationLabel = new JLabel(usageExplanationIcon);
        // Allow splitPane divider to be moved to the right
        usageExplanationLabel.setMinimumSize(new Dimension(100, 100));
        middlePanel.add(usageExplanationLabel, "usageExplanationPanel");
        JComponent wrappedSolutionPanel;
        if (solutionPanel.isWrapInScrollPane()) {
            wrappedSolutionPanel = new JScrollPane(solutionPanel);
        } else {
            wrappedSolutionPanel = solutionPanel;
        }
        middlePanel.add(wrappedSolutionPanel, "solutionPanel");
        return middlePanel;
    }

    private JPanel createScorePanel() {
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setBorder(BorderFactory.createEtchedBorder());
        showConstraintMatchesDialogAction = new ShowConstraintMatchesDialogAction();
        showConstraintMatchesDialogAction.setEnabled(false);
        //scorePanel.add(new JButton(showConstraintMatchesDialogAction), BorderLayout.WEST);
        scoreField = new JTextField("Score:");
        scoreField.setEditable(false);
        scoreField.setForeground(Color.BLACK);
        scoreField.setBorder(BorderFactory.createLoweredBevelBorder());
        //scorePanel.add(scoreField, BorderLayout.CENTER);
        refreshScreenDuringSolvingCheckBox = new JCheckBox("Refresh screen during solving",
                solutionPanel.isRefreshScreenDuringSolving());
       // scorePanel.add(refreshScreenDuringSolvingCheckBox, BorderLayout.EAST);
        return scorePanel;
    }

    private class ShowConstraintMatchesDialogAction extends AbstractAction {

        public ShowConstraintMatchesDialogAction() {
            super("Constraint matches", new ImageIcon(SolverAndPersistenceFrame.class.getResource("showConstraintMatchesDialogAction.png")));
        }

        public void actionPerformed(ActionEvent e) {
            constraintMatchesDialog.resetContentPanel();
            constraintMatchesDialog.setVisible(true);
        }

    }

    private void setSolutionLoaded() {
        setTitle(titlePrefix + " - " + solutionBusiness.getSolutionFileName());
        ((CardLayout) middlePanel.getLayout()).show(middlePanel, "solutionPanel");
        solveAction.setEnabled(true);
        saveAction.setEnabled(true);
        exportAction.setEnabled(solutionBusiness.hasExporter());
        showConstraintMatchesDialogAction.setEnabled(true);
        resetScreen();
    }

    private void setSolvingState(boolean solving) {
    	/*
        for (Action action : quickOpenUnsolvedActionList) {
            action.setEnabled(!solving);
        }
        for (Action action : quickOpenSolvedActionList) {
            action.setEnabled(!solving);
        }
        */
        //importAction.setEnabled(!solving && solutionBusiness.hasImporter());
        //openAction.setEnabled(!solving);
        //saveAction.setEnabled(!solving);
        //exportAction.setEnabled(!solving && solutionBusiness.hasExporter());
        solveAction.setEnabled(!solving);
        solveButton.setVisible(!solving);
        terminateSolvingEarlyAction.setEnabled(solving);
        terminateSolvingEarlyButton.setVisible(solving);
        solutionPanel.setEnabled(!solving);
        progressBar.setIndeterminate(solving);
        progressBar.setStringPainted(solving);
        progressBar.setString(solving ? "Solving..." : null);
        showConstraintMatchesDialogAction.setEnabled(!solving);
        solutionPanel.setSolvingState(solving);
    }

    public void resetScreen() {
        solutionPanel.resetPanel(solutionBusiness.getSolution());
        validate();
        scoreField.setForeground(determineScoreFieldForeground(solutionBusiness.getScore()));
        scoreField.setText("Score: " + solutionBusiness.getScore());
        
        //TODO:
        
    }

    private Color determineScoreFieldForeground(Score<?> score) {
        if (!(score instanceof FeasibilityScore)) {
            return Color.BLACK;
        } else {
            FeasibilityScore<?> feasibilityScore = (FeasibilityScore<?>) score;
            return feasibilityScore.isFeasible() ? TangoColorFactory.CHAMELEON_3 : TangoColorFactory.SCARLET_3;
        }
    }

}
