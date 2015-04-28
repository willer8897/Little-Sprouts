package org.lsdt.optaplannerLittleSprouts;

import java.io.File;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.lsdt.optaplannerLittleSprouts.domain.SchedulingSolution;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

public class Main {
	public static final String SOLVER_CONFIG_XML = "org/lsdt/optaplannerLittleSprouts/solver/schedulingCounterSolverConfig.xml";
	public static ScoreDirector scoreDirector;
	
	private static SessionFactory factory;

	public static void main(String[] args) {
		
		//Test DB connectivity
		String hibernatePropsFilePath = "src/main/resources/org/lsdt/optaplannerLittleSprouts/database/hibernate.cfg.xml";
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
	         java.util.List users = session.createQuery("FROM User").list(); 
	         for (Iterator iterator = 
	                           users.iterator(); iterator.hasNext();){
	            User user = (User) iterator.next(); 
	            System.out.print("First Name: " + user.getFirstname()); 
	            System.out.print("  Last Name: " + user.getLastname()); 
	            System.out.print("\n_____________________________________ \n");
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		
		
		//Create solution
		Solver solver = SolverFactory.createFromXmlResource(SOLVER_CONFIG_XML).buildSolver();
		ScoreDirectorFactory scoreDirectorFactory = solver.getScoreDirectorFactory();
		scoreDirector = scoreDirectorFactory.buildScoreDirector();
		SchedulingSolution schedulingSolution = new SchedulingSolution();
		scoreDirector.setWorkingSolution(schedulingSolution);
		
		System.out.println("\nSolving");
		solver.solve(schedulingSolution);
		
		SchedulingSolution bestSchedulingSolution = (SchedulingSolution) solver.getBestSolution();
		System.out.println("\n\nBEST SOLUTION HAS EXACTLY "+ bestSchedulingSolution.getTaskList().get(0).getCount() + " entities");
	}
}
 