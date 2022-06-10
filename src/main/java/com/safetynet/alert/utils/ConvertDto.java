package com.safetynet.alert.utils;

import com.safetynet.alert.dto.addressfirestation.AddressFireStationDto;
import com.safetynet.alert.dto.integration.ChildListDto;
import com.safetynet.alert.dto.integration.FloodStationListDto;
import com.safetynet.alert.dto.integration.PersonFireAddressListDto;
import com.safetynet.alert.dto.integration.PersonInfoListDto;
import com.safetynet.alert.dto.integration.PersonStationListDto;
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
 */
public class ConvertDto {

  public static PersonStationListDto convertToPersonStationListDto(Person person) {
    return new PersonStationListDto(person.getFirstName(),
        person.getLastName(), person.getAddress().getStreet(), person.getPhone());
  }

  /**
   * convertToChildListDto. Method that convert
   * person to childListDto.
   *
   * @param person a given person
   * @return ChildListDto
   */
  public static ChildListDto convertToChildListDto(Person person) {
    return new ChildListDto(person.getFirstName(),
        person.getLastName(), person.getAge());
  }

  /**
   * convertToPersonFireAddressListDto. Method that convert
   * person to PersonFireAddressListDto.
   *
   * @param person a given person
   * @return PersonFireAddressListDto
   */
  public static PersonFireAddressListDto convertToPersonFireAddressListDto(Person person) {
    return new PersonFireAddressListDto(person.getLastName(), person.getPhone(), person.getAge(),
        person.getMedicalRecords().getMedications(), person.getMedicalRecords().getAllergies());
  }

  /**
   * convertToFloodStationListDto. Method that convert a
   * list of person to FloodStationListDto list.
   *
   * @param persons a given person list
   * @return FloodStationListDto list
   */
  public static List<FloodStationListDto> convertToFloodStationListDto(List<Person> persons) {
    List<FloodStationListDto> floodStationListDto = new ArrayList<>();
    for (Person person : persons) {
      floodStationListDto.add(new FloodStationListDto(person.getLastName(), person.getPhone(),
          person.getAge(), person.getMedicalRecords().getMedications(),
          person.getMedicalRecords().getAllergies()));
    }
    return floodStationListDto;
  }

  /**
   * convertToPersonInfoListDto. Method that convert a
   * person to PersonInfoListDto.
   *
   * @param person a given person
   * @return PersonInfoListDto
   */
  public static PersonInfoListDto convertToPersonInfoListDto(Person person) {
    PersonInfoListDto personInfoListDto = new PersonInfoListDto();
    personInfoListDto.setLastname(person.getLastName());
    personInfoListDto.setAge(person.getAge());
    personInfoListDto.setEmail(person.getEmail());
    personInfoListDto.setMedications(person.getMedicalRecords().getMedications());
    personInfoListDto.setAllergies(person.getMedicalRecords().getAllergies());
    return personInfoListDto;
  }

  /**
   * convertToPersonDto. Method that convert a
   * list of personDto list
   *
   * @param persons a given person list
   * @return PersonDto list
   */
  public static List<PersonDto> convertToPersonDto(List<Person> persons) {
    List<PersonDto> personDto = new ArrayList<>();
    for (Person person : persons) {
      personDto.add(new PersonDto(person.getFirstName(), person.getLastName(),
          person.getAddress().getStreet(), person.getPhone(),
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
   * @return Person
   */
  public static Person convertToPerson(PersonDto personDto) {


    return new Person(personDto.getFirstName(), personDto.getLastName(),
        personDto.getPhone(), personDto.getEmail(),
        new Address(personDto.getAddress(), personDto.getCity(),
            personDto.getZip()), 0,
        new MedicalRecords());
  }

  /**
   * convertToAddressFireStationListDto. Method that convert a
   * AddressFireStation to AddressFireStationDto.
   *
   * @param addressFireStation a given addressFireStation
   * @return AddressFireStationDto
   */
  public static AddressFireStationDto convertToAddressFireStationListDto(
      AddressFireStation addressFireStation) {
    return new AddressFireStationDto(addressFireStation.getAddress().getStreet(),
        addressFireStation.getFirestation().getStation());
  }
}
