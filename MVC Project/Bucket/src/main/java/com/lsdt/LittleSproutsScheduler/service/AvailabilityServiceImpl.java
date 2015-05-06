package com.lsdt.LittleSproutsScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsdt.LittleSproutsScheduler.repository.AvailabilityRepository;

@Service("availabilityService")
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;
}
