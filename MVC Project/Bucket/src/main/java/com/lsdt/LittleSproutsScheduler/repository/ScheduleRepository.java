package com.lsdt.LittleSproutsScheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Schedule;

@Repository("scheduleRepository")
public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {

	@Query("select s from Schedule s")
	  List<Schedule> getAllSchedules();
}
