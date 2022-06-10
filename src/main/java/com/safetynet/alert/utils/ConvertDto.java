package com.safetynet.alert.utils;

import com.safetynet.alert.dto.AddressFirestation.AddressFireStationDto;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.AddressFireStation;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * ConvertDto class define method to
 * make conversion between Object and Dto.
 *
 */
public class ConvertDto {

  /**
   * convertToPersonDto. Method that convert a
   * list of personDto list
   *
   * @param persons a given person list
   *
   * @return PersonDto list
   */
  public static List<PersonDto> convertToPersonDto(List<Person> persons) {
    List<PersonDto> personDto = new ArrayList<>();
    for (Person person: persons) {
      personDto.add(new PersonDto(person.getFirstName(), person.getLastName(),
          person.getAddress().getStreet(),  person.getPhone(),
          person.getAddress().getCity(), person.getAddress().getZip(),
          person.getEmail()));
    }
    return personDto;
  }

  /**
   * convertToPerson. Method that convert a
   * personDto to Person.
   *
   * @param personDto a given personDto
   *
   * @return Person
   */
  public static Person convertToPerson(PersonDto personDto) {


    return new Person(personDto.getFirstName(), personDto.getLastName(),
        personDto.getPhone(), personDto.getEmail(), new Address(personDto.getAddress(), personDto.getCity(),
        personDto.getZip()), 0,
        new MedicalRecords());
  }

  /**
   * convertToAddressFireStationListDto. Method that convert a
   * AddressFireStation to AddressFireStationDto.
   *
   * @param addressFireStation a given addressFireStation
   *
   * @return AddressFireStationDto
   */
  public static AddressFireStationDto convertToAddressFireStationListDto(AddressFireStation addressFireStation) {
    return new AddressFireStationDto(addressFireStation.getAddress().getStreet(),
        addressFireStation.getFirestation().getStation());
  }
}
