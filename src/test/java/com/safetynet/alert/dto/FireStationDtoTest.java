package com.safetynet.alert.dto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.safetynet.alert.dto.firestation.FireStationDto;
import com.safetynet.alert.model.FireStation;
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
    FireStationDto given = new FireStationDto("2");

    // WHEN
    FireStation result = new FireStationDto().convertToEntity(given);

    // THEN
    assertThat(result).isEqualTo(new FireStation("2"));
  }

  @Test
  @DisplayName("convertToDto should convert given fireStation to fireStationDto")
  void convertToDto_ShouldConvertFireStationToFireStationDto_ForGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("2");

    // WHEN
    FireStationDto result = new FireStationDto().convertToDto(given);

    // THEN
    assertThat(result).isEqualTo(new FireStationDto("2"));
  }
}