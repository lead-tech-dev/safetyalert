package com.safetynet.alert.dto.firestation;

import com.safetynet.alert.model.FireStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The FireStationDto class implements a fireStationDto
 * entity.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class FireStationDto {

  private String station;

  /**
   * convertToEntity. Method that convert fireStationDto
   * to entity.
   *
   * @param fireStationDto a fireStationDto
   * @return FireStation
   */
  public FireStation convertToEntity(FireStationDto fireStationDto) {

    if (fireStationDto == null) {
      return null;
    }
    return FireStation.builder()
        .station(fireStationDto.getStation())
        .build();
  }

  /**
   * convertToEntity. Method that convert fireStation
   * to fireStationDto.
   *
   * @param firestation a fireStationDto
   * @return FireStationDto
   */
  public FireStationDto convertToDto(FireStation firestation) {
    if (firestation == null) {
      return null;
    }
    return FireStationDto.builder()
        .station(firestation.getStation())
        .build();
  }

}
