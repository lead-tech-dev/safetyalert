package com.safetynet.alert.dto.medicalrecords;

import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class MedicalRecordsDto {

  @NotBlank
  private String firstName;


  @NotBlank
  private String lastName;


  private String birthdate;


  private List<String> allergies = new ArrayList<>();


  private List<String> medications = new ArrayList<>();

  private Person person;

  public MedicalRecordsDto(String firstName, String lastName, String birthdate,
                           List<String> allergies, List<String> medications) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.allergies = allergies;
    this.medications = medications;
  }

  public MedicalRecords convertToEntity(MedicalRecordsDto medicalRecordsDto) {

    if(medicalRecordsDto == null) {
      return null;
    }
    return MedicalRecords.builder()
        .firstName(medicalRecordsDto.getFirstName())
        .lastName(medicalRecordsDto.getLastName())
        .birthdate(medicalRecordsDto.getBirthdate())
        .medications(medicalRecordsDto.getMedications())
        .allergies(medicalRecordsDto.getAllergies())
        .build();
  }

  public MedicalRecordsDto convertToDto(MedicalRecords medicalRecords) {
    if(medicalRecords == null) {
      return null;
    }
    return MedicalRecordsDto.builder()
        .firstName(medicalRecords.getFirstName())
        .lastName(medicalRecords.getLastName())
        .birthdate(medicalRecords.getBirthdate())
        .medications(medicalRecords.getMedications())
        .allergies(medicalRecords.getAllergies())
        .build();
  }
}
