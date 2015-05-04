package com.lsdt.LittleSproutsScheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Request;

@Repository("requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {

}
