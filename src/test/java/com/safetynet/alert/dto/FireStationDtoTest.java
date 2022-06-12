package com.safetynet.alert.dto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.safetynet.alert.dto.station.StationDto;
import com.safetynet.alert.model.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("FireStationDtoTest")
@DisplayName("FireStation dto test logic")
class FireStationDtoTest {

  @Test
  @DisplayName("convertToEntity should convert given fireStationDto to fireStation")
  void convertToEntity_ShouldConvertFireStationDtoToFireStation_ForGivenFireStationDto() {
    // GIVEN
    StationDto given = new StationDto("2");

    // WHEN
    Station result = new StationDto().convertToEntity(given);

    // THEN
    assertThat(result).isEqualTo(new Station("2"));
  }

  @Test
  @DisplayName("convertToDto should convert given fireStation to fireStationDto")
  void convertToDto_ShouldConvertFireStationToFireStationDto_ForGivenFireStation() {
    // GIVEN
    Station given = new Station("2");

    // WHEN
    StationDto result = new StationDto().convertToDto(given);

    // THEN
    assertThat(result).isEqualTo(new StationDto("2"));
  }
}