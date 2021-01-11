package com.telros.app.data.service;

import com.telros.app.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User getByUsername(String username);
}
