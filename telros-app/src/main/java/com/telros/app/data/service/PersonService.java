package com.telros.app.data.service;

import com.telros.app.data.entity.PersonEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class PersonService extends CrudService<PersonEntity, Integer> {

  private PersonRepository repository;

  public PersonService(@Autowired PersonRepository repository) {
    this.repository = repository;
  }

  @Override
  protected PersonRepository getRepository() {
    return repository;
  }
}
