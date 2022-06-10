package com.safetynet.alert.dto.integration;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The FloodStationListDto class implements a floodStationListDto
 * entity.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class FloodStationListDto {
  private String lastname;

  private String phone;

  private int age;

  private List<String> medications = new ArrayList<>();

  private List<String> allergies = new ArrayList<>();
}
