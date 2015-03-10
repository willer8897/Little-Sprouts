package org.lsdt.optaplannerLittleSprouts.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsdt.optaplannerLittleSprouts.Main;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

@PlanningSolution
public class SchedulingSolution implements Solution<HardSoftScore>, Serializable {
	List<SchedulingEntity> employees;
	List<Integer> helloCounts;
	private final int HELLO_MAX = 25;
	private HardSoftScore score;

	public SchedulingSolution() {
		populateHellos();
		populateHelloCounts();
	}

	@PlanningEntityCollectionProperty
	public List<SchedulingEntity> getTaskList() {
		if (employees == null) {
			populateHellos();
		}
		return employees;
	}

	@ValueRangeProvider(id = "helloCountRange")
	public List<Integer> getHelloCountList() {
		return helloCounts;
	}

	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = new ArrayList<Object>();
		// nothing to add because the only facts are already added automatically
		// by planner
		return facts;
	}

	public HardSoftScore getScore() {
		if(null==Main.scoreDirector){
			System.out.println("WOOPS YOU SHOULD EXPECT SOME ISSUES HERE");
		}
		HardSoftScore hardSoftScore = (HardSoftScore)Main.scoreDirector.calculateScore();
		System.out.println("SCORE "+ hardSoftScore.toString() );
		return hardSoftScore;
	}

	public void setScore(HardSoftScore arg0) {
		this.score = score;
	}

	private void populateHellos() {
		if (null == employees) {
			employees = new ArrayList<SchedulingEntity>();
			employees.add(new SchedulingEntity(17));
		}
	}

	private void populateHelloCounts() {
		if (null == helloCounts) {
			helloCounts = new ArrayList<Integer>();
			for (int i = 0; i < HELLO_MAX; i++) {
				helloCounts.add(new Integer(i));
			}
		}
	}
}
