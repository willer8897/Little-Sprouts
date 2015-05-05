package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsdt.LittleSproutsScheduler.model.Schedule;
import com.lsdt.LittleSproutsScheduler.repository.ScheduleRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	public List<Schedule> getSchedules() {
		List<Schedule> stud = scheduleRepository.getAllSchedules();
		return stud;
	}

}
