package com.safetynet.alert.dto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.safetynet.alert.dto.medicalrecords.MedicalRecordsDto;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

@Tag("MedicalRecordsDtoTest")
@DisplayName("MedicalRecords dto test logic")
class MedicalRecordsDtoTest {

  @Test
  @DisplayName("convertToEntity should convert given medicalRecordsDto to medicalRecords")
  void convertToEntity_ShouldConvertMedicalRecordsDtoToMedicalRecords_ForGivenMedicalRecordsDto() {
    // GIVEN
    MedicalRecordsDto given = new MedicalRecordsDto("Cartman", "Eric", "23/02/1981",
        new ArrayList<>(), new ArrayList<>(), new Person());
    MedicalRecords expected = new MedicalRecords("Cartman", "Eric", "23/02/1981",
        new ArrayList<>(), new ArrayList<>());

    // WHEN
    MedicalRecords result = new MedicalRecordsDto().convertToEntity(given);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("convertToDto should convert given medicalRecords  to medicalRecordsDto")
  void convertToDto_ShouldConvertMedicalRecordsToMedicalRecordsDto_ForGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Cartman", "Eric", "23/02/1981",
        new ArrayList<>(), new ArrayList<>());
    MedicalRecordsDto expected = new MedicalRecordsDto("Cartman", "Eric", "23/02/1981",
        new ArrayList<>(), new ArrayList<>());

    // WHEN
    MedicalRecordsDto result = new MedicalRecordsDto().convertToDto(given);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

}