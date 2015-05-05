package com.lsdt.LittleSproutsScheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Schedule;

@Repository("scheduleRepository")
public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {

}
