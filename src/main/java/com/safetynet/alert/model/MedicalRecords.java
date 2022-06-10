package com.safetynet.alert.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The MedicalRecords class implements a medicalRecords
 * entity.
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class MedicalRecords {

  private String firstName;

  private String lastName;

  private String birthdate;

  private List<String> allergies = new ArrayList<>();

  private List<String> medications = new ArrayList<>();

}
