package com.lsdt.LittleSproutsScheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.Availability;

@Repository("availabilityRepository")
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

}
