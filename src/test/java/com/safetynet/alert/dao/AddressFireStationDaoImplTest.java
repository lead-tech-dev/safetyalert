package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.AddressDaoImpl;
import com.safetynet.alert.dao.impl.AddressFireStationDaoImpl;
import com.safetynet.alert.dao.impl.FireStationDaoImpl;
import com.safetynet.alert.dto.AddressFirestation.AddressFireStationDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.AddressFireStation;
import com.safetynet.alert.model.FireStation;
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
@Tag("AddressFireStationDaoImplTest")
@DisplayName("AddressFireStation data access object")
class AddressFireStationDaoImplTest {

  private static MockedStatic<Database> database;

  private static AddressFireStationDaoImpl addressFireStationDaoImpl;

  @Mock
  private static AddressDaoImpl addressDaoImpl;

  @Mock
  private static FireStationDaoImpl fireStationDaoImpl;

  @BeforeEach
  private void setUp() {
    List<AddressFireStation> addressFireStations = new ArrayList<>();
    addressFireStations.add(
        new AddressFireStation(
            new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
            new FireStation("3"))
    );
    addressFireStations.add(
        new AddressFireStation(
            new Address("7 rue charles debrune", "Mainvilliers", "28300"),
            new FireStation("4")
        )
    );
    database = mockStatic(Database.class);
    when(Database.getAddressFireStationData()).thenReturn(addressFireStations);

    addressFireStationDaoImpl = new AddressFireStationDaoImpl(addressDaoImpl, fireStationDaoImpl);
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getAddressFireStationList should return addressFireStation list")
  void getAddressFireStationList_ShouldReturnAddressFireStationList() {
    // Given
    AddressFireStation expected1 = new AddressFireStation(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        new FireStation("3")
    );

    AddressFireStation expected2 = new AddressFireStation(
        new Address("7 rue charles debrune", "Mainvilliers", "28300"),
        new FireStation("4")
    );

    // WHEN
    List<AddressFireStationDto> result = addressFireStationDaoImpl.getAddressFireStationList();

    // THEN
    assertThat(result).containsExactly(
        ConvertDto.convertToAddressFireStationListDto(expected1),
        ConvertDto.convertToAddressFireStationListDto(expected2)
    );
  }

  @Test
  @DisplayName("saveAddressFireStation should throw no such element exception for Not Exist " +
      "Address")
  void saveAddressFireStation_ShouldThrowNoSuchElementException_forNotExistAddress() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue anatol france","3"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue anatol france")).thenReturn(Optional.empty());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> addressFireStationDaoImpl.saveAddressFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Address  doesn't exist !");
  }

  @Test
  @DisplayName("saveAddressFireStation should throw no such element exception for Not Exist " +
      "Station")
  void saveAddressFireStation_ShouldThrowNoSuchElementException_forNotExistStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau","6"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );
    when(fireStationDaoImpl.getFireStationByStation("6")).thenReturn(Optional.empty());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> addressFireStationDaoImpl.saveAddressFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Station doesn't exist !");
  }

  @Test
  @DisplayName("saveAddressFireStation should throw bad request exception for exit addressStation" +
      "addressFireStation")
  void saveAddressFireStation_ShouldThrowBadRequestException_forExistAddressStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto (
        "7 rue lucien deneau",
        "3"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );
    when(fireStationDaoImpl.getFireStationByStation("3")).thenReturn(Optional.of(
        new FireStation("3"))
    );


    // WHEN
    var ex = assertThrows(BadRequestException.class,
        () -> addressFireStationDaoImpl.saveAddressFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Mapping address/station already exist !");
  }

  @Test
  @DisplayName("saveAddressFireStation should add one for given addressFireStation")
  void saveAddressFireStation_ShouldAddOne_forGivenAddressStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );

    when(fireStationDaoImpl.getFireStationByStation("4")).thenReturn(Optional.of(
        new FireStation("4"))
    );

    // WHEN
    addressFireStationDaoImpl.saveAddressFireStation(expected);

    // THEN
    assertThat(addressFireStationDaoImpl.getAddressFireStationList()).contains(expected);
  }

  @Test
  @DisplayName("saveAddressFireStation should return added addressFireStation for given " +
      "addressStation")
  void saveAddressFireStation_ShouldReturnAddedAddressFireStation_forGivenAddressFireStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(addressDaoImpl.getAddressByStreet("7 rue lucien deneau")).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"))
    );

    when(fireStationDaoImpl.getFireStationByStation("4")).thenReturn(Optional.of(
        new FireStation("4"))
    );

    // WHEN
    AddressFireStationDto result = addressFireStationDaoImpl.saveAddressFireStation(expected);

    // THEN
    assertThat(result).isEqualTo(expected);
  }


  @Test
  @DisplayName("updateAddressFireStation should throw no such element exception for Not " +
      "exist mapping addressStation")
  void updateAddressFireStation_ShouldThrowNoSuchElementException_ForNotExistMappingAddressStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "2 rue lucien deneau",
        "4"
    );

    // WHEN
    var exception = assertThrows(NoSuchElementException.class, () -> {
      addressFireStationDaoImpl.updateAddressFireStation(expected);
    });

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Mapping address/station doesn't exist !");
  }


  @Test
  @DisplayName("updateAddressFireStation should throw no such element exception for Not " +
      "exist Station")
  void updateAddressFireStation_ShouldThrowNoSuchElementException_ForNotExistStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau",
        "6"
    );

    // WHEN
    var exception = assertThrows(NoSuchElementException.class, () -> {
      addressFireStationDaoImpl.updateAddressFireStation(expected);
    });

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Station doesn't exist !");
  }

  @Test
  @DisplayName("saveAddressFireStation should update one for given addressFireStation")
  void updateAddressFireStation_ShouldUpdateOne_ForGivenAddressFireStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(fireStationDaoImpl.getFireStationByStation("4")).thenReturn(Optional.of(new FireStation(
        "4")));

    // WHEN
    addressFireStationDaoImpl.updateAddressFireStation(expected);

    // THEN
    assertThat(addressFireStationDaoImpl.getAddressFireStationList()).contains(expected);
  }

  @Test
  @DisplayName("saveAddressFireStation should return updated addressFireStation")
  void updateAddressFireStation_ShouldReturnUpdatedAddressFireStation_forGivenAddressFireStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "7 rue lucien deneau",
        "4"
    );
    when(fireStationDaoImpl.getFireStationByStation("4")).thenReturn(Optional.of(new FireStation(
        "4")));

    // WHEN
    AddressFireStationDto result = addressFireStationDaoImpl.updateAddressFireStation(expected);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("deleteAddressFireStation should throw no such element exception")
  void deleteAddressFireStation_ShouldThrowNoSuchElementException_forNotExistAddressAndStation() {
    // GIVEN
    AddressFireStationDto expected = new AddressFireStationDto(
        "17 rue lucien deneau",
        "6"
    );

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> addressFireStationDaoImpl.deleteAddressFireStation(expected));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("station or address or both doesn't exit");
  }

  @Test
  @DisplayName("deleteAddressFireStation should delete by address")
  void deleteAddressFireStation_ShouldDeleteByAddress_forGivenAddressFireStation() {
    // GIVEN
    AddressFireStationDto given = new AddressFireStationDto(
        "7 rue lucien deneau",
        "6"
    );

    AddressFireStationDto expectedDelete = new AddressFireStationDto(
        "7 rue lucien deneau",
        "3"
    );

    // WHEN
    addressFireStationDaoImpl.deleteAddressFireStation(given);

    // THEN
    assertThat(addressFireStationDaoImpl.getAddressFireStationList()).doesNotContain(expectedDelete);
  }

  @Test
  @DisplayName("deleteAddressFireStation should delete by station")
  void deleteAddressFireStation_ShouldDeleteByStation_forGivenAddressFireStation() {
    // GIVEN
    AddressFireStationDto given = new AddressFireStationDto(
        "17 rue julien assange",
        "3"
    );

    AddressFireStationDto expectedDelete = new AddressFireStationDto(
        "7 rue lucien deneau",
        "3"
    );

    // WHEN
    addressFireStationDaoImpl.deleteAddressFireStation(given);

    // THEN
    assertThat(addressFireStationDaoImpl.getAddressFireStationList()).doesNotContain(expectedDelete);
  }


  @Test
  @DisplayName("getAddressListByStation should return a address list")
  void getAddressListByStation_ShouldReturnAddressList_forGivenStation() {
    // GIVEN
    String given = "3";

    // WHEN
    List<String> addresses = addressFireStationDaoImpl.getAddressListByStation(given);

    // THEN
    assertThat(addresses).contains("7 rue lucien deneau");
  }

  @Test
  @DisplayName("getStationListByAddress should return a station list")
  void getStationListByAddress_ShouldReturnStationList_forGivenAddress() {
    // GIVEN
    String given = "7 rue lucien deneau";

    // WHEN
    List<String> stations = addressFireStationDaoImpl.getStationListByAddress(given);

    // THEN
    assertThat(stations).contains("3");
  }

}