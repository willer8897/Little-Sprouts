package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsdt.LittleSproutsScheduler.model.Availability;
import com.lsdt.LittleSproutsScheduler.repository.AvailabilityRepository;

@Service("availabilityService")
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	public List<Availability> getAvailabilities() {
		List<Availability> stud = availabilityRepository.getAllAvailabilities();
		return stud;
	}
}
