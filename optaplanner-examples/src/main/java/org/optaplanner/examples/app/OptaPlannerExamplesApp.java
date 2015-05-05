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

package org.optaplanner.examples.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lsdt.optaplannerLittleSprouts.Availability;
import org.lsdt.optaplannerLittleSprouts.Child;
import org.lsdt.optaplannerLittleSprouts.User;
import org.lsdt.optaplannerLittleSprouts.xmlKing;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.cloudbalancing.app.CloudBalancingApp;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.swingui.SolverAndPersistenceFrame;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.curriculumcourse.app.CurriculumCourseApp;
import org.optaplanner.examples.dinnerparty.app.DinnerPartyApp;
import org.optaplanner.examples.examination.app.ExaminationApp;
import org.optaplanner.examples.machinereassignment.app.MachineReassignmentApp;
import org.optaplanner.examples.nqueens.app.NQueensApp;
import org.optaplanner.examples.nurserostering.app.NurseRosteringApp;
import org.optaplanner.examples.pas.app.PatientAdmissionScheduleApp;
import org.optaplanner.examples.projectjobscheduling.app.ProjectJobSchedulingApp;
import org.optaplanner.examples.tennis.app.TennisApp;
import org.optaplanner.examples.travelingtournament.app.TravelingTournamentApp;
import org.optaplanner.examples.tsp.app.TspApp;
import org.optaplanner.examples.vehiclerouting.app.VehicleRoutingApp;









//our imports
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.lsdt.optaplannerLittleSprouts.domain.SchedulingSolution;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import  org.optaplanner.core.api.solver.Solver;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class OptaPlannerExamplesApp extends JFrame {

	private static SessionFactory factory;
	private static xmlKing KING;
	private static JComboBox box;
	private static JPanel contentPane;
	
    public static void main(String[] args) {
        CommonApp.prepareSwingEnvironment();
        OptaPlannerExamplesApp optaPlannerExamplesApp = new OptaPlannerExamplesApp();
        optaPlannerExamplesApp.pack();
        optaPlannerExamplesApp.setLocationRelativeTo(null);
        optaPlannerExamplesApp.setVisible(true);
        
        //generateXMLFile();
    }
    
    public static void generateXMLFile()
    {
    	KING = new xmlKing();


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
    		tx = session.beginTransaction();
    		ArrayList<User> users = (ArrayList<User>) session.createQuery("FROM User WHERE type = 'T'").list(); 
    		ArrayList<Child> children = (ArrayList<Child>) session.createQuery("FROM Child").list(); 
    		ArrayList<Availability> childAvailabilities = (ArrayList<Availability>) session.createQuery("FROM Availability WHERE is_child = 1").list(); 
    		ArrayList<Availability> teacherAvailabilities = (ArrayList<Availability>) session.createQuery("FROM Availability WHERE is_child = 0").list();

    		KING.retrieveUserData(users);
    		KING.retrieveChildAvailabilityData(childAvailabilities);
    		KING.retrieveTeacherAvailabilityData(teacherAvailabilities);
    		KING.retrieveChildData(children);
    		
    		KING.doItAll(box.getSelectedItem().toString());

    		tx.commit();
    	}catch (HibernateException e) {
    		if (tx!=null) tx.rollback();
    		e.printStackTrace(); 
    	}finally {
    		session.close(); 
    	}
    }

    private static String determineOptaPlannerExamplesVersion() {
        String optaPlannerExamplesVersion = OptaPlannerExamplesApp.class.getPackage().getImplementationVersion();
        if (optaPlannerExamplesVersion == null) {
            optaPlannerExamplesVersion = "";
        }
        return optaPlannerExamplesVersion;
    }

    private JTextArea descriptionTextArea;

    public OptaPlannerExamplesApp() {
        super("Little Sprouts " + determineOptaPlannerExamplesVersion());
        setIconImage(SolverAndPersistenceFrame.OPTA_PLANNER_ICON.getImage());
        setContentPane(createContentPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Container createContentPane() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel titleLabel = new JLabel("Welcome To The Bucket", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
        contentPane.add(titleLabel, BorderLayout.NORTH);
        JScrollPane examplesScrollPane = new JScrollPane(createExamplesPanel());
        examplesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        examplesScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        contentPane.add(examplesScrollPane, BorderLayout.CENTER);
        //contentPane.add(createDescriptionPanel(), BorderLayout.SOUTH);
        return contentPane;
    }

    private JPanel createExamplesPanel() {
        JPanel examplesPanel = new JPanel();
        examplesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GroupLayout layout = new GroupLayout(examplesPanel);
        examplesPanel.setLayout(layout);
        //JPanel basicExamplesPanel = createBasicExamplesPanel();
        //JPanel realExamplesPanel = createRealExamplesPanel();
        JPanel difficultExamplesPanel = createDifficultExamplesPanel();
        layout.setHorizontalGroup(layout.createSequentialGroup()
                //.addComponent(basicExamplesPanel)
                .addGap(10)
                //.addComponent(realExamplesPanel)
                .addGap(10)
                .addComponent(difficultExamplesPanel));
        layout.setVerticalGroup(layout.createParallelGroup()
                //.addComponent(basicExamplesPanel)
                //.addComponent(realExamplesPanel)
                .addComponent(difficultExamplesPanel));
        return examplesPanel;
    }

    private JPanel createBasicExamplesPanel() {
   
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        //TitledBorder titledBorder = BorderFactory.createTitledBorder("Basic examples");
        //titledBorder.setTitleColor(TangoColorFactory.CHAMELEON_3);
        //panel.setBorder(BorderFactory.createCompoundBorder(titledBorder,
                //BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        //panel.add(createExampleButton(new NQueensApp()));
       // panel.add(createExampleButton(new CloudBalancingApp()));
        //panel.add(createExampleButton(new TspApp()));
        //panel.add(createExampleButton(new DinnerPartyApp()));
        //panel.add(createExampleButton(new TennisApp()));
        return panel;
        
        
    }

    private JPanel createRealExamplesPanel() {
    	
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        /*
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Real examples");
        titledBorder.setTitleColor(TangoColorFactory.BUTTER_3);
        panel.setBorder(BorderFactory.createCompoundBorder(titledBorder,
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel.add(createExampleButton(new CurriculumCourseApp()));
        panel.add(createExampleButton(new MachineReassignmentApp()));
        panel.add(createExampleButton(new VehicleRoutingApp()));
        panel.add(createExampleButton(new ProjectJobSchedulingApp()));
        panel.add(createExampleButton(new PatientAdmissionScheduleApp()));
        */
        return panel;
        
    }

    private JPanel createDifficultExamplesPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        //TitledBorder titledBorder = BorderFactory.createTitledBorder("Difficult examples");
        //titledBorder.setTitleColor(TangoColorFactory.SCARLET_3);
        //panel.setBorder(BorderFactory.createCompoundBorder(titledBorder,
                //BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        //panel.add(createExampleButton(new ExaminationApp()));
        
        
        
        box = new JComboBox();
        box.addItem("2014-04-06");
        box.addItem("2014-04-13");
        box.addItem("2014-04-20");
        panel.add(new JLabel("Select The Week"));
        panel.add(box).setLocation(1, 1);
        
        //panel.add(createExampleButton(new NurseRosteringApp()));
        //panel.add(createExampleButton(new TravelingTournamentApp()));
        
        panel.add(createGenerateButton(new NurseRosteringApp()));
        panel.add(createExitButton());
        
        panel.add(new JPanel());
        return panel;
    }

    private JButton createGenerateButton(final CommonApp commonApp) {
        JButton button = new JButton(new AbstractAction("Generate Schedule") {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Generating");
            	//KING.setWeekStart(box.getSelectedItem().toString());
            	//KING.doItAll(box.getSelectedItem().toString());
            	contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            	generateXMLFile();
                commonApp.init(OptaPlannerExamplesApp.this, false, KING.getMondayShifts(), KING.getTuesdayShfits() , KING.getWednesdayShfits() , KING.getThursdayShfits() , KING.getFridayShfits(), KING.getWeekStart(), KING.getDescriptionMap() );
                contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        button.setHorizontalAlignment(JButton.LEFT);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        return button;
    }
    
    private JButton createExitButton() {
        JButton button = new JButton(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        button.setHorizontalAlignment(JButton.LEFT);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        return button;
    }
    
    private String getUserSelection()
    {
    	String result = box.getSelectedItem().toString();
    	return result;
    }
    
    private JButton createExampleButton(final CommonApp commonApp) {
        JButton button = new JButton(new AbstractAction("Generate Schedule") {
            public void actionPerformed(ActionEvent e) {
            	
            	KING.doItAll(box.getSelectedItem().toString());
            	
                commonApp.init(OptaPlannerExamplesApp.this, false, null, null, null, null, null, null, null);
            }
        });
        button.setHorizontalAlignment(JButton.LEFT);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        return button;
    }

    private JButton createDisabledExampleButton(final CommonApp commonApp) {
        JButton exampleButton = createExampleButton(commonApp);
        exampleButton.setEnabled(false);
        return exampleButton;
    }

    private JPanel createDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(new JLabel("Description"), BorderLayout.NORTH);
        descriptionTextArea = new JTextArea(8, 80);
        descriptionTextArea.setEditable(false);
        descriptionPanel.add(new JScrollPane(descriptionTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        return descriptionPanel;
    }

    private static class EmptyIcon implements Icon {

        @Override
        public int getIconWidth() {
            return 64;
        }

        @Override
        public int getIconHeight() {
            return 64;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            // Do nothing
        }

    }

}
