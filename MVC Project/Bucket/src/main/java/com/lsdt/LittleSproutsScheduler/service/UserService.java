package com.lsdt.LittleSproutsScheduler.service;

import java.util.List;

import com.lsdt.LittleSproutsScheduler.model.User;

public interface UserService {
  User save(User user);
  User findAndGetAttributes(String username);
  boolean findByLogin(String userName, String password);
  boolean findByUserName(String userName);
  List<User> getUsers();
}
