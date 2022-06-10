package com.safetynet.alert.utils;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.safetynet.alert.dto.addressfirestation.AddressFireStationDto;
import com.safetynet.alert.dto.integration.ChildListDto;
import com.safetynet.alert.dto.integration.FloodStationListDto;
import com.safetynet.alert.dto.integration.PersonFireAddressListDto;
import com.safetynet.alert.dto.integration.PersonInfoListDto;
import com.safetynet.alert.dto.integration.PersonStationListDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.AddressFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConvertDtoTest {
  private final Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
      new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
      40, new MedicalRecords("Cartman", "Eric", "23/02/1981", new ArrayList<>(),
      new ArrayList<>()));

  @Test
  @DisplayName("convertToPersonStationListDto should convert person to person list dto")
  void convertToPersonStationListDto_ShouldConvertToPersonListDto_ForGivenPerson() {
    // GIVEN
    PersonStationListDto expected =     new PersonStationListDto("Cartman", "Eric", "7 rue lucien" +
        " deneau", "0758951895");

    // WHEN
    PersonStationListDto result = ConvertDto.convertToPersonStationListDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("convertToChildListDto should convert person to child list dto")
  void convertToChildListDto_ShouldConvertPersonToChildListDto_ForGivenPerson() {
    // GIVEN
    ChildListDto expected = new ChildListDto("Cartman", "Eric", 40);

    // WHEN
    ChildListDto result = ConvertDto.convertToChildListDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("convertToPersonFireAddressListDto should convert person to person " +
      "address fire list dto")
  void convertToPersonFireAddressListDto_ShouldConvertToPersonAddressFireListDto_ForGivenPerson() {
    // GIVEN
    PersonFireAddressListDto expected = new PersonFireAddressListDto("Eric", "0758951895", 40,
        Collections.emptyList(), Collections.emptyList());

    // WHEN
    PersonFireAddressListDto result = ConvertDto.convertToPersonFireAddressListDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("convertToFloodStationListDto should convert person to flood station list dto")
  void convertToFloodStationListDto_ShouldConvertToFloodStationListDtp_ForGivenPerson() {
    // GIVEN
    FloodStationListDto expected = new FloodStationListDto("Eric", "0758951895", 40,
        Collections.emptyList(), Collections.emptyList());

    // WHEN
    List<FloodStationListDto> result = ConvertDto.convertToFloodStationListDto(List.of(person));

    // THEN
    assertThat(result).containsExactly(expected);
  }

  @Test
  @DisplayName("convertToPersonInfoListDto should convert person to person info list dto")
  void convertToPersonInfoListDto_ShouldConvertToPersonInfoList_ForGivenPerson() {
    // GIVEN
    PersonInfoListDto expected = new PersonInfoListDto("Eric", 40, "ericmaximan@gmail.com",
        Collections.emptyList(), Collections.emptyList());

    // WHEN
    PersonInfoListDto result = ConvertDto.convertToPersonInfoListDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }


  @Test
  @DisplayName("convertToAddressFireStationListDto should convert person to person address fireStation " +
      "list dto")
  void convertToAddressFireStationListDto_ShouldConvertToPersonAddressFireStationList_ForGivenPerson() {
    // GIVEN
    AddressFireStation addressFireStation = new AddressFireStation(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"), new FireStation("2"));

    // WHEN
    AddressFireStationDto result =
        ConvertDto.convertToAddressFireStationListDto(addressFireStation);

    // THEN
    assertThat(result).isEqualTo(new AddressFireStationDto("7 rue lucien deneau", "2"));
  }
}