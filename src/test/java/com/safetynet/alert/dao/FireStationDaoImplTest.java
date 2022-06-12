package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.AddressDaoImpl;
import com.safetynet.alert.dao.impl.FireStationDaoImpl;
import com.safetynet.alert.dao.impl.StationDaoImpl;
import com.safetynet.alert.dto.firestation.FireStationDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Station;
import com.safetynet.alert.utils.ConvertDto;
import com.safetynet.alert.web.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Tag("FireStationDaoImplTest")
@DisplayName("FireStation data access object")
class FireStationDaoImplTest {

  private static MockedStatic<Database> database;

  private static FireStationDaoImpl fireStationDaoImpl;

  @Mock
  private static AddressDaoImpl addressDaoImpl;

  @Mock
  private static StationDaoImpl stationDaoImpl;

  @BeforeEach
  private void setUp() {
    List<FireStation> fireStations = new ArrayList<>();
    fireStations.add(
        new FireStation(
            new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
            new Station("3"))
    );
    fireStations.add(
        new FireStation(
            new Address("7 rue charles debrune", "Mainvilliers", "28300"),
            new Station("4")
        )
    );
    database = mockStatic(Database.class);
    when(Database.getFireStationData()).thenReturn(fireStations);

    fireStationDaoImpl = new FireStationDaoImpl(addressDaoImpl, stationDaoImpl);
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getFireStationList should return fireStation list")
  void getFireStationList_ShouldReturnFireStationList() {
    // Given
    FireStation expected1 = new FireStation(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        new Station("3")
    );

    FireStation expected2 = new FireStation(
        new Address("7 rue charles debrune", "Mainvilliers", "28300"),
        new Station("4")
    );

    // WHEN
    List<FireStationDto> result = fireStationDaoImpl.getFireStationList();

    // THEN
    assertThat(result).containsExactly(
        ConvertDto.convertToFireStationListDto(expected1),
        ConvertDto.convertToFireStationListDto(expected2)
    );
  }

  @Test
  @DisplayName("saveFireStation should throw no such element exception for Not Exist " +
      "Address")
  void saveFireStation_ShouldThrowNoSuchElementException_forNotExistAddress() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue anatol france", "3"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue anatol france")).thenReturn(Optional.empty());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> fireStationDaoImpl.saveFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Address  doesn't exist !");
  }

  @Test
  @DisplayName("saveFireStation should throw no such element exception for Not Exist " +
      "Station")
  void saveFireStation_ShouldThrowNoSuchElementException_forNotExistStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau", "6"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );
    when(stationDaoImpl.getStationByStation("6")).thenReturn(Optional.empty());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> fireStationDaoImpl.saveFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Station doesn't exist !");
  }

  @Test
  @DisplayName("saveFireStation should throw bad request exception for already exist fireStation")
  void saveFireStation_ShouldThrowBadRequestException_forExistFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "3"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );
    when(stationDaoImpl.getStationByStation("3")).thenReturn(Optional.of(
        new Station("3"))
    );


    // WHEN
    var ex = assertThrows(BadRequestException.class,
        () -> fireStationDaoImpl.saveFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Mapping address/station already exist !");
  }

  @Test
  @DisplayName("saveFireStation should add one for given fireStation")
  void saveFireStation_ShouldAddOne_forGivenFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );

    when(stationDaoImpl.getStationByStation("4")).thenReturn(Optional.of(
        new Station("4"))
    );

    // WHEN
    fireStationDaoImpl.saveFireStation(expected);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).contains(expected);
  }

  @Test
  @DisplayName("saveFireStation should return added fireStation for given " +
      "fireStation")
  void saveFireStation_ShouldReturnAddedAddressFireStation_forGivenFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );

    when(stationDaoImpl.getStationByStation("4")).thenReturn(Optional.of(
        new Station("4"))
    );

    // WHEN
    FireStationDto result = fireStationDaoImpl.saveFireStation(expected);

    // THEN
    assertThat(result).isEqualTo(expected);
  }


  @Test
  @DisplayName("updateFireStation should throw no such element exception for Not " +
      "exist fireStation")
  void updateFireStation_ShouldThrowNoSuchElementException_ForNotExistFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "2 rue lucien deneau",
        "4"
    );

    // WHEN
    var exception = assertThrows(NoSuchElementException.class, () -> {
      fireStationDaoImpl.updateFireStation(expected);
    });

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Mapping address/station doesn't exist !");
  }


  @Test
  @DisplayName("updateFireStation should throw no such element exception for Not " +
      "exist Station")
  void updateFireStation_ShouldThrowNoSuchElementException_ForNotExistStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "6"
    );

    // WHEN
    var exception = assertThrows(NoSuchElementException.class, () -> {
      fireStationDaoImpl.updateFireStation(expected);
    });

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Station doesn't exist !");
  }

  @Test
  @DisplayName("saveFireStation should update one for given fireStation")
  void updateFireStation_ShouldUpdateOne_ForGivenAddressFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(stationDaoImpl.getStationByStation("4")).thenReturn(Optional.of(new Station(
        "4")));

    // WHEN
    fireStationDaoImpl.updateFireStation(expected);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).contains(expected);
  }

  @Test
  @DisplayName("saveFireStation should return updated fireStation")
  void updateFireStation_ShouldReturnUpdatedFireStation_forGivenFireStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(stationDaoImpl.getStationByStation("4")).thenReturn(Optional.of(new Station(
        "4")));

    // WHEN
    FireStationDto result = fireStationDaoImpl.updateFireStation(expected);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("deleteFireStation should throw no such element exception")
  void deleteFireStation_ShouldThrowNoSuchElementException_forNotExistAddressAndStation() {
    // GIVEN
    FireStationDto expected = new FireStationDto(
        "17 rue lucien deneau",
        "6"
    );

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> fireStationDaoImpl.deleteFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("station or address or both doesn't exit");
  }

  @Test
  @DisplayName("deleteFireStation should delete by address")
  void deleteFireStation_ShouldDeleteByAddress_forGivenFireStation() {
    // GIVEN
    FireStationDto given = new FireStationDto(
        "7 rue lucien deneau",
        "6"
    );

    FireStationDto expectedDelete = new FireStationDto(
        "7 rue lucien deneau",
        "3"
    );

    // WHEN
    fireStationDaoImpl.deleteFireStation(given);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).doesNotContain(
        expectedDelete);
  }

  @Test
  @DisplayName("deleteFireStation should delete by station")
  void deleteFireStation_ShouldDeleteByStation_forGivenFireStation() {
    // GIVEN
    FireStationDto given = new FireStationDto(
        "17 rue julien assange",
        "3"
    );

    FireStationDto expectedDelete = new FireStationDto(
        "7 rue lucien deneau",
        "3"
    );

    // WHEN
    fireStationDaoImpl.deleteFireStation(given);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).doesNotContain(
        expectedDelete);
  }


  @Test
  @DisplayName("getAddressListByStation should return a address list")
  void getAddressListByStation_ShouldReturnAddressList_forGivenStation() {
    // GIVEN
    String given = "3";

    // WHEN
    List<String> addresses = fireStationDaoImpl.getAddressListByStation(given);

    // THEN
    assertThat(addresses).contains("7 rue lucien deneau");
  }

  @Test
  @DisplayName("getStationListByAddress should return a station list")
  void getStationListByAddress_ShouldReturnStationList_forGivenAddress() {
    // GIVEN
    String given = "7 rue lucien deneau";

    // WHEN
    List<String> stations = fireStationDaoImpl.getStationListByAddress(given);

    // THEN
    assertThat(stations).contains("3");
  }

}