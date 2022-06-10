package com.safetynet.alert.dao;

import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.Person;
import java.util.List;
import java.util.Optional;

/**
 * PersonDao interface structure the business logic
 * of Person.
 *
 */
public interface PersonDao {

   /**
    * getPersonList. Method that get a
    * list of Person from a data source.
    *
    * @return a Person list
    */
   List<PersonDto> getPersonList();

   /**
    * savePerson. Method that save a
    * Person to a data source.
    *
    * @param personDto a Person object
    *
    * @return a Person object
    */
   PersonDto savePerson(PersonDto personDto);

   /**
    * updatePerson. Method that update an
    * Person in a data source.
    *
    * @param personDto an Person object
    *
    * @return a Person object
    */
   PersonDto updatePerson(PersonDto personDto);

   /**
    * deletePerson. Method that delete an
    * Person in a data source.
    *
    * @param personDeleteDto a Person object
    */
   void deletePerson(PersonDto personDeleteDto);

   /**
    * getPersonByFirstNameAndLastName. Method that get or not an
    * Person from a data source.
    *
    * @param firstName a person firstname
    * @param lastName a person lastname
    *
    * @return an optional Person object
    */
   Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName);

   /**
    * getPersonByAddress. Method that get
    * Person from a data source.
    *
    * @param address a person street address
    *
    * @return a Person
    */
   Person getPersonByAddress (String address);

}
