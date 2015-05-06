package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

import com.lsdt.LittleSproutsScheduler.model.Request;

public interface RequestService {
	Request save(Request request);
	List<Request> getRequests();
}
