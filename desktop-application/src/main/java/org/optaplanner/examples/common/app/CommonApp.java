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

package org.optaplanner.examples.common.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.optaplanner.benchmark.impl.aggregator.swingui.SwingUncaughtExceptionHandler;
import org.optaplanner.benchmark.impl.aggregator.swingui.SwingUtils;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.examples.common.swingui.SolverAndPersistenceFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommonApp extends LoggingMain {

    protected static final Logger logger = LoggerFactory.getLogger(CommonApp.class);

    /**
     * Some examples are not compatible with every native LookAndFeel.
     * For example, NurseRosteringPanel is incompatible with Mac.
     */
    public static void prepareSwingEnvironment() {
        SwingUncaughtExceptionHandler.register();
        SwingUtils.fixateLookAndFeel();
    }

    protected final String name;
    protected final String description;
    protected final String iconResource;

    protected SolverAndPersistenceFrame solverAndPersistenceFrame;
    protected SolutionBusiness solutionBusiness;

    protected CommonApp(String name, String description, String iconResource) {
        this.name = name;
        this.description = description;
        this.iconResource = iconResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIconResource() {
        return iconResource;
    }

    public void init() {
        init(null, true, null, null, null, null, null, null, null);
    }

    public void init(Component centerForComponent, boolean exitOnClose, ArrayList<String> mondayShifts, ArrayList<String> tuesdayShifts, ArrayList<String> wednesdayShifts
    		,ArrayList<String> thursdayShifts, ArrayList<String> fridayShifts, String weekStart, HashMap map) {
        solutionBusiness = createSolutionBusiness();
        solverAndPersistenceFrame = new SolverAndPersistenceFrame(
                solutionBusiness, createSolutionPanel(), "Little Sprouts");
        solverAndPersistenceFrame.setDefaultCloseOperation(exitOnClose ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
        solverAndPersistenceFrame.init(centerForComponent);
        solverAndPersistenceFrame.setVisible(true);
        
        //pass the arraylists one
        solverAndPersistenceFrame.getMondayShifts(mondayShifts);
        solverAndPersistenceFrame.getTuesdayShifts(tuesdayShifts);
        solverAndPersistenceFrame.getWednesdayShifts(wednesdayShifts);
        solverAndPersistenceFrame.getThursdayShifts(thursdayShifts);
        solverAndPersistenceFrame.getFridayShifts(fridayShifts);
        solverAndPersistenceFrame.getWeekStart(weekStart);
        solverAndPersistenceFrame.getDescriptionMap(map);
        
        solverAndPersistenceFrame.doTheDeed();
    }

    public SolutionBusiness createSolutionBusiness() {
        SolutionBusiness solutionBusiness = new SolutionBusiness();
        solutionBusiness.setSolutionDao(createSolutionDao());
        solutionBusiness.setImporter(createSolutionImporter());
        solutionBusiness.setExporter(createSolutionExporter());
        solutionBusiness.updateDataDirs();
        solutionBusiness.setSolver(createSolver());
        return solutionBusiness;
    }

    protected abstract Solver createSolver();

    protected abstract SolutionPanel createSolutionPanel();

    protected abstract SolutionDao createSolutionDao();

    protected AbstractSolutionImporter createSolutionImporter() {
        return null;
    }

    protected AbstractSolutionExporter createSolutionExporter() {
        return null;
    }

}
