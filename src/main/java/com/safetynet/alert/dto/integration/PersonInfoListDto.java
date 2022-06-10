package com.safetynet.alert.dto.integration;

import com.safetynet.alert.model.Person;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The PersonInfoListDto class implements a personInfoListDto
 * entity.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class PersonInfoListDto {

  private String lastname;

  private int age;

  private String email;

  private List<String> medications = new ArrayList<>();

  private List<String> allergies = new ArrayList<>();

  /**
   * convertToDto. Method that convert Person to PersonInfoListDto
   *
   * @param person a person
   * @return PersonInfoListDto
   */
  public PersonInfoListDto convertToDto(Person person) {
    if (person == null) {
      return null;
    }
    return PersonInfoListDto.builder()
        .lastname(person.getLastName())
        .age(person.getAge())
        .email(person.getEmail())
        .medications(person.getMedicalRecords().getMedications())
        .allergies(person.getMedicalRecords().getAllergies())
        .build();
  }

}
