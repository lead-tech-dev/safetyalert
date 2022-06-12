package com.safetynet.alert.dto.station;

import com.safetynet.alert.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The StationDto class implements a stationDto
 * entity.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class StationDto {

  private String station;

  /**
   * convertToEntity. Method that convert stationDto
   * to entity.
   *
   * @param stationDto a stationDto
   * @return Station
   */
  public Station convertToEntity(StationDto stationDto) {

    if (stationDto == null) {
      return null;
    }
    return Station.builder()
        .station(stationDto.getStation())
        .build();
  }

  /**
   * convertToEntity. Method that convert station
   * to stationDto.
   *
   * @param station a station
   * @return StationDto
   */
  public StationDto convertToDto(Station station) {
    if (station == null) {
      return null;
    }
    return StationDto.builder()
        .station(station.getStation())
        .build();
  }

}
