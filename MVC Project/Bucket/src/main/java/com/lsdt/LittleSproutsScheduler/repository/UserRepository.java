package com.lsdt.LittleSproutsScheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lsdt.LittleSproutsScheduler.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.username = :username")
  User findByUserName(@Param("username") String username);
  
  @Query("select u from User u")
  List<User> getAllUsers();

}
