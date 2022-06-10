package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.PersonDao;
import com.safetynet.alert.dto.person.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * PersonController. class that implement
 * request/response logic of person.
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {

  private	static final Logger log = LoggerFactory.getLogger(AddressFireStationController.class);
  private final PersonDao personDao;

  public PersonController(PersonDao personDao) {
    this.personDao = personDao;
  }

  /**
   * getPerson. Method that return a
   * list of PersonDto to response.
   *
   * @return a list of PersonDto
   */
  @GetMapping
  public List<PersonDto> getPerson() {
    log.info("Getting PersonDto list");

    List<PersonDto> result = personDao.getPersonList();

    log.info("getPerson request success. Person found with data {}",
        result);
    return result;
  }

  /**
   * savePerson. Method that get a PersonDto
   * for request and return a save PersonDto in response.
   *
   * @param personDto a given PersonDto
   *
   * @return PersonDto object
   */
  @PostMapping
  public ResponseEntity<PersonDto> savePerson(@Valid  @RequestBody PersonDto personDto) {
    log.info("Saving Person with Person {}", personDto);

    PersonDto personAdded = personDao.savePerson(personDto);
    if (Objects.isNull(personAdded)) {
      return   ResponseEntity.noContent().build();
    }

    log.info("savePerson request success. Saved with person {}",
        personDto);
    return new ResponseEntity<>(personAdded, HttpStatus.CREATED);
   }

  /**
   * updatePerson. Method that get a personDto
   * from request and return an update personDto for response.
   *
   * @param personDto a given personDto
   *
   * @return personDto object
   */
  @PutMapping
  public PersonDto updatePerson(@Valid @RequestBody PersonDto personDto) {
    log.info("Updating Person with Person {}", personDto);

    PersonDto updated = personDao.updatePerson(personDto);
    log.info("updatePerson request success. Person updated with {}",
        updated);

    return updated;
  }

  /**
   * deletePerson. Method that get an personDto
   * from request.
   *
   * @param personDto a given personDto
   */
  @DeleteMapping
  public void deletePerson(@Valid @RequestBody PersonDto personDto) {
    log.info("Deleting person {}", personDto);

    personDao.deletePerson(personDto);

    log.info("deletePerson request success {}", personDto);
  }
}
