package com.safetynet.alert.dto.medicalrecords;

import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The MedicalRecordsDto class implements a medicalRecordsDto
 * entity.
 *
 */
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

  /**
   * Constructor of class MedicalRecordsDto.
   *
   * @param firstName a firstName
   * @param lastName a lastName
   * @param birthdate a birthdate
   * @param allergies an allergies
   * @param medications a medications
   */
  public MedicalRecordsDto(String firstName, String lastName, String birthdate,
                           List<String> allergies, List<String> medications) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.allergies = allergies;
    this.medications = medications;
  }

  /**
   * convertToEntity. Method that convert MedicalRecordsDto to entity.
   *
   * @param medicalRecordsDto a medicalRecordsDto
   * @return MedicalRecords
   */
  public MedicalRecords convertToEntity(MedicalRecordsDto medicalRecordsDto) {

    if (medicalRecordsDto == null) {
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

  /**
   * convertToEntity. Method that convert MedicalRecords to MedicalRecordsDto.
   *
   * @param medicalRecords a medicalRecords
   * @return MedicalRecordsDto
   */
  public MedicalRecordsDto convertToDto(MedicalRecords medicalRecords) {
    if (medicalRecords == null) {
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
