package com.safetynet.alert.dto.firestation;

import com.safetynet.alert.model.FireStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class FireStationDto {

  private String  station;

  public FireStation convertToEntity(FireStationDto fireStationDto) {

    if(fireStationDto == null) {
      return null;
    }
    return FireStation.builder()
        .station(fireStationDto.getStation())
        .build();
  }

  public FireStationDto convertToDto(FireStation firestation) {
    if(firestation == null) {
      return null;
    }
    return FireStationDto.builder()
        .station(firestation.getStation())
        .build();
  }

}
