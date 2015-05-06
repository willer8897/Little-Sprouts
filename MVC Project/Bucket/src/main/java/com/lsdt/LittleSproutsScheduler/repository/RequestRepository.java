package com.lsdt.LittleSproutsScheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Request;

@Repository("requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {

	@Query("select r from Request r")
	List<Request> getAllRequests();

}
