package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

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

	public List<Request> getRequests() {
		List<Request> stud = requestRepository.getAllRequests();
		return stud;
	}

}
