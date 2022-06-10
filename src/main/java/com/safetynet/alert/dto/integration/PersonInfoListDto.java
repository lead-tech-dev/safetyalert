package com.safetynet.alert.dto.integration;

import com.safetynet.alert.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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



  public PersonInfoListDto convertToDto(Person person) {
    if(person == null) {
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
