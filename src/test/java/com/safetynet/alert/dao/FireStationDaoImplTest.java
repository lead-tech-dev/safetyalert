package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.FireStationDaoImpl;
import com.safetynet.alert.model.FireStation;
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
@Tag("FireStationDaoImplTest")
@DisplayName("fireStation data access object")
class FireStationDaoImplTest {


  private static MockedStatic<Database> database;

  private static FireStationDaoImpl fireStationDaoImpl;

  @BeforeEach
  private void setUp() {
    List<FireStation> fireStations = new ArrayList<>();
    fireStations.add(new FireStation("1"));

    database = mockStatic(Database.class);
    when(Database.getFireStationData()).thenReturn(fireStations);

    fireStationDaoImpl = new FireStationDaoImpl();
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getFireStationList should return fireStation list")
  void getFireStationList_ShouldReturnFireStationList() {
    // Given
    FireStation expected = new FireStation("1");

    // WHEN
    List<FireStation> result = fireStationDaoImpl.getFireStationList();

    // THEN
    assertThat(result).containsExactly(expected);
  }


  @Test
  @DisplayName("saveFireStation should throw bad request exception")
  void saveFireStation_ShouldThrowBadRequestException_ForGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("1");

    // WHEN
    var exception = assertThrows(BadRequestException.class,
        () -> fireStationDaoImpl.saveFireStation(given));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("FireStation already exist!");
  }

  @Test
  @DisplayName("saveFireStation should add one fireStation")
  void saveFireStation_ShouldAddOne_forGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("2");

    // WHEN
    fireStationDaoImpl.saveFireStation(given);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).contains(given);
  }

  @Test
  @DisplayName("saveFireStation should return given address")
  void saveFireStation_ShouldReturnAddedFireStation_forGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("2");

    // WHEN
    FireStation result = fireStationDaoImpl.saveFireStation(given);

    // THEN
    assertThat(result).isEqualTo(given);
  }

  @Test
  @DisplayName("updateFireStation should throw no such element exception")
  void updateFireStation_ShouldThrowNoSuchElementException_forNotExistFireStation() {
    // GIVEN
    FireStation given = new FireStation("2");

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> fireStationDaoImpl.updateFireStation(given));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("FireStation doesn't exist!");
  }

  @Test
  @DisplayName("updateFireStation should update one")
  void updateFireStation_ShouldUpdateOne_forGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("1");

    // WHEN
    fireStationDaoImpl.updateFireStation(given);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).contains(given);
  }

  @Test
  @DisplayName("updateFireStation should return given fireStation")
  void updateFireStation_ShouldReturnGivenFireStation_forGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("1");

    // WHEN
    FireStation result = fireStationDaoImpl.updateFireStation(given);

    // THEN
    assertThat(result).isEqualTo(given);
  }


  @Test
  @DisplayName("deleteFireStation should throw no such element exception")
  void deleteFireStation_ShouldThrowNoSuchElementException_forNotExistFireStation() {
    // GIVEN
    FireStation given = new FireStation("2");

    // WHEN
    var exception = assertThrows(NoSuchElementException.class,
        () -> fireStationDaoImpl.deleteFireStation(given));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("FireStation doesn't exist!");
  }

  @Test
  @DisplayName("deleteFireStation should delete one fireStation")
  void deleteFireStation_ShouldDeleteOne_forGivenFireStation() {
    // GIVEN
    FireStation given = new FireStation("1");

    // WHEN
    fireStationDaoImpl.deleteFireStation(given);

    // THEN
    assertThat(fireStationDaoImpl.getFireStationList()).doesNotContain(given);
  }
}