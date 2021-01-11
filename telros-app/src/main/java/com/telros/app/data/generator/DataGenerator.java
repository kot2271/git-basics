package com.telros.app.data.generator;

import com.telros.app.data.entity.Role;
import com.telros.app.data.entity.User;
import com.telros.app.data.service.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import com.telros.app.data.service.PersonRepository;
import com.telros.app.data.entity.PersonEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

  @Bean
  public CommandLineRunner loadData(
      PersonRepository personRepository, UserRepository userRepository) {
    return args -> {
      Logger logger = LoggerFactory.getLogger(getClass());
      if (personRepository.count() != 0L) {
        logger.info("Using existing database");
        return;
      }
      int seed = 123;

      logger.info("Generating demo data");

      logger.info("... generating 100 Person entities...");
      ExampleDataGenerator<PersonEntity> personRepositoryGenerator =
          new ExampleDataGenerator<>(PersonEntity.class);
      personRepositoryGenerator.setData(PersonEntity::setId, DataType.ID);
      personRepositoryGenerator.setData(PersonEntity::setFirstName, DataType.FIRST_NAME);
      personRepositoryGenerator.setData(PersonEntity::setLastName, DataType.LAST_NAME);
      personRepositoryGenerator.setData(PersonEntity::setEmail, DataType.EMAIL);
      personRepositoryGenerator.setData(PersonEntity::setPhone, DataType.PHONE_NUMBER);
      personRepositoryGenerator.setData(PersonEntity::setDateOfBirth, DataType.DATE_OF_BIRTH);
      personRepositoryGenerator.setData(PersonEntity::setOccupation, DataType.OCCUPATION);
      personRepositoryGenerator.setData(PersonEntity::setImportant, DataType.BOOLEAN_10_90);
      personRepository.saveAll(personRepositoryGenerator.create(100, seed));

      //            userRepository.save(new User("user", "111111", Role.USER));
      //            userRepository.save(new User("admin", "admin", Role.ADMIN));
      userRepository.save(new User("user", "111111", Role.ADMIN));
      userRepository.save(new User("admin", "admin", Role.ADMIN));

      logger.info("Generated demo data");
    };
  }
}
