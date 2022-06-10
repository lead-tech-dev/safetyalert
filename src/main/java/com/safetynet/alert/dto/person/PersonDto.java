package com.safetynet.alert.dto.person;

import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The PersonDto class implements a personDto
 * entity.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@lombok.Generated
public class PersonDto {


  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  private String address;

  private String phone;

  private String city;

  private String zip;

  private String email;

  /**
   * convertToEntity. Method that convert PersonDto to Person
   *
   * @param personDto a personDto
   * @return Person
   */
  public Person convertToEntity(PersonDto personDto) {

    if (personDto == null) {
      return null;
    }
    return Person.builder()
        .firstName(personDto.getFirstName())
        .lastName(personDto.getLastName())
        .address(new Address(personDto.getAddress(), personDto.getCity(), personDto.getZip()))
        .phone(personDto.getPhone())
        .email(personDto.getEmail())
        .age(0)
        .medicalRecords(new MedicalRecords())
        .build();
  }

  /**
   * convertToEntity. Method that convert Person to PersonDto
   *
   * @param person a person
   * @return PersonDto
   */
  public PersonDto convertToDto(Person person) {
    if (person == null) {
      return null;
    }
    return PersonDto.builder()
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .address(person.getAddress().getStreet())
        .phone(person.getPhone())
        .city(person.getAddress().getCity())
        .zip(person.getAddress().getZip())
        .email(person.getEmail())
        .build();
  }
}
