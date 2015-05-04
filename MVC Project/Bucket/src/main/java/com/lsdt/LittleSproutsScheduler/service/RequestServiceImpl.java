package com.lsdt.LittleSproutsScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsdt.LittleSproutsScheduler.model.Request;
import com.lsdt.LittleSproutsScheduler.repository.RequestRepository;

@Service("requestService")
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestRepository requestRepository;
	
	@Transactional
	public Request save(Request request) {
		return requestRepository.save(request);
	}

}
