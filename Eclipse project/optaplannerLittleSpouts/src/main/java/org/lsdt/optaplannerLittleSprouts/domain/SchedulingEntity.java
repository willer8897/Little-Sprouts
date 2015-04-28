package org.lsdt.optaplannerLittleSprouts.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class SchedulingEntity {
	private int count;

	@SuppressWarnings("unused")
	private SchedulingEntity() {
	}

	public SchedulingEntity(int i) {
		this.count = i;
	}

	@PlanningVariable(valueRangeProviderRefs = { "helloCountRange" })
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
