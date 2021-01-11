package com.telros.app.data.service;

import com.telros.app.data.entity.PersonEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {}
