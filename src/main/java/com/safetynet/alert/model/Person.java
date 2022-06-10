package com.safetynet.alert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class Person {

  private String firstName;

  private String lastName;

  private String phone;

  private String email;

  private Address address;

  @JsonInclude(JsonInclude.Include. NON_NULL)
  private int age;


  private MedicalRecords medicalRecords;

}
