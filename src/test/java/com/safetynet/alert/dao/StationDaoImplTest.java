package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.StationDaoImpl;
import com.safetynet.alert.model.Station;
import com.safetynet.alert.web.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
@Tag("StationDaoImplTest")
@DisplayName("Station data access object")
class StationDaoImplTest {


  private static MockedStatic<Database> database;

  private static StationDaoImpl stationDaoImpl;

  @BeforeEach
  private void setUp() {
    List<Station> stations = new ArrayList<>();
    stations.add(new Station("1"));

    database = mockStatic(Database.class);
    when(Database.getStationData()).thenReturn(stations);

    stationDaoImpl = new StationDaoImpl();
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getStationList should return station list")
  void getStationList_ShouldReturnStationList() {
    // Given
    Station expected = new Station("1");

    // WHEN
    List<Station> result = stationDaoImpl.getStationList();

    // THEN
    assertThat(result).containsExactly(expected);
  }


  @Test
  @DisplayName("saveStation should throw bad request exception")
  void saveStation_ShouldThrowBadRequestException_ForGivenStation() {
    // GIVEN
    Station given = new Station("1");

    // WHEN
    var exception = assertThrows(BadRequestException.class,
        () -> stationDaoImpl.saveStation(given));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("FireStation already exist!");
  }

  @Test
  @DisplayName("saveStation should add one station")
  void saveStation_ShouldAddOne_forGivenStation() {
    // GIVEN
    Station given = new Station("2");

    // WHEN
    stationDaoImpl.saveStation(given);

    // THEN
    assertThat(stationDaoImpl.getStationList()).contains(given);
  }

  @Test
  @DisplayName("saveStation should return given station")
  void saveStation_ShouldReturnAddedStation_forGivenStation() {
    // GIVEN
    Station given = new Station("2");

    // WHEN
    Station result = stationDaoImpl.saveStation(given);

    // THEN
    assertThat(result).isEqualTo(given);
  }

  @Test
  @DisplayName("updateStation should throw no such element exception")
  void updateStation_ShouldThrowNoSuchElementException_forNotExistStation() {
    // GIVEN
    Station given = new Station("2");

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> stationDaoImpl.updateStation(given));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Station doesn't exist!");
  }

  @Test
  @DisplayName("updateStation should update one")
  void updateStation_ShouldUpdateOne_forGivenStation() {
    // GIVEN
    Station given = new Station("1");

    // WHEN
    stationDaoImpl.updateStation(given);

    // THEN
    assertThat(stationDaoImpl.getStationList()).contains(given);
  }

  @Test
  @DisplayName("updateStation should return given station")
  void updateStation_ShouldReturnGivenStation_forGivenStation() {
    // GIVEN
    Station given = new Station("1");

    // WHEN
    Station result = stationDaoImpl.updateStation(given);

    // THEN
    assertThat(result).isEqualTo(given);
  }


  @Test
  @DisplayName("deleteStation should throw no such element exception")
  void deleteStation_ShouldThrowNoSuchElementException_forNotExistStation() {
    // GIVEN
    Station given = new Station("2");

    // WHEN
    var exception = assertThrows(NoSuchElementException.class,
        () -> stationDaoImpl.deleteStation(given));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("FireStation doesn't exist!");
  }

  @Test
  @DisplayName("deleteStation should delete one station")
  void deleteStation_ShouldDeleteOne_forGivenStation() {
    // GIVEN
    Station given = new Station("1");

    // WHEN
    stationDaoImpl.deleteStation(given);

    // THEN
    assertThat(stationDaoImpl.getStationList()).doesNotContain(given);
  }
}