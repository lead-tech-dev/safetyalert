package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.MedicalRecordsDaoImpl;
import com.safetynet.alert.dto.medicalrecords.MedicalRecordsDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
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
@Tag("MedicalRecordsDaoImplTest")
@DisplayName("MedicalRecords data access object")
class MedicalRecordsDaoImplTest {

  private static MockedStatic<Database> database;

  private static MedicalRecordsDaoImpl medicalRecordsDaoImpl;

  @Mock
  private static PersonDao personDao;

  @BeforeEach
  private void setUp() {
    List<MedicalRecords> medicalRecords = new ArrayList<>();
    medicalRecords.add(new MedicalRecords("Cartman",
        "Eric", "23/02/81", List.of("tetramine:280"), List.of("gluten")));



    database = mockStatic(Database.class);
    when(Database.getMedicalRecordsData()).thenReturn(medicalRecords);

    medicalRecordsDaoImpl = new MedicalRecordsDaoImpl(personDao);
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getMedicalRecordsList should return medicalRecords list")
  void getMedicalRecordsList_ShouldReturnMedicalRecordsList() {
    // Given
    MedicalRecords expected1 = new MedicalRecords("Cartman",
        "Eric", "23/02/81", List.of("tetramine:280"), List.of("gluten"));

    // WHEN
    List<MedicalRecords> result = medicalRecordsDaoImpl.getMedicalRecordsList();

    // THEN
    assertThat(result).containsExactly(expected1);

  }

  @Test
  @DisplayName("saveMedicalRecords should throw no such element exception")
  void saveMedicalRecords_ShouldThrowNoSuchElementException_forExistMedicalRecords() {
    // GIVEN
    MedicalRecords expected = new MedicalRecords("Daniel",
        "Rousseau", "23/02/21", List.of("tetramine:280"), List.of("gluten"));
    when(personDao.getPersonByFirstNameAndLastName(expected.getFirstName(),
        expected.getLastName())).thenReturn(Optional.empty());

    // WHEN
    var exception = assertThrows(NoSuchElementException.class,
        () -> medicalRecordsDaoImpl.saveMedicalRecords(new MedicalRecordsDto().convertToDto(expected)));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Person doesn't exist !");
  }

  @Test
  @DisplayName("saveMedicalRecords should throw bad request exception")
  void saveMedicalRecords_ShouldThrowBadRequestException_forExistMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Cartman",
        "Eric", "23/02/21", List.of("tetramine:280"), List.of("gluten"));
    when(personDao.getPersonByFirstNameAndLastName(given.getFirstName(),
        given.getLastName())).thenReturn(Optional.of(new Person("Cartman", "Eric",
        "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"), 0, given)));

    // WHEN
    var exception = assertThrows(BadRequestException.class,
        () -> medicalRecordsDaoImpl.saveMedicalRecords(new MedicalRecordsDto().convertToDto(given)));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Medical record already exist !");
  }

  @Test
  @DisplayName("saveMedicalRecords should add one medicalRecords")
  void saveMedicalRecords_ShouldAddOne_forGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Daniel", "Rousseau", "23/02/23", List.of(
        "aspirine:200"), List.of("vlodimir"));

    when(personDao.getPersonByFirstNameAndLastName(given.getFirstName(),
        given.getLastName())).thenReturn(Optional.of(new Person("Daniel", "Rousseau",
        "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"), 0, new MedicalRecords())));


    // WHEN
    medicalRecordsDaoImpl.saveMedicalRecords(new MedicalRecordsDto().convertToDto(given));

    // THEN
    assertThat(medicalRecordsDaoImpl.getMedicalRecordsList()).contains(given);
  }


  @Test
  @DisplayName("saveMedicalRecords should return added medicalRecords")
  void saveAddress_ShouldReturnAddedMedicalRecords_forGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Daniel", "Rousseau", "23/02/23", List.of(
        "aspirine:200"), List.of("vlodimir"));

    when(personDao.getPersonByFirstNameAndLastName(given.getFirstName(),
        given.getLastName())).thenReturn(Optional.of(new Person("Daniel", "Rousseau",
        "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"), 0, new MedicalRecords())));


    // WHEN
    MedicalRecords result =
        medicalRecordsDaoImpl.saveMedicalRecords(new MedicalRecordsDto().convertToDto(given));


    // THEN
    assertThat(result).isEqualTo(given);
  }


  @Test
  @DisplayName("updateMedicalRecords should throw no such element exception")
  void updateMedicalRecords_ShouldThrowNoSuchElementException_forNotExistMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Daniel", "Rousseau", "23/02/23", List.of(
        "aspirine:200"), List.of("vlodimir"));

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> medicalRecordsDaoImpl.updateMedicalRecords(new MedicalRecordsDto().convertToDto(given)));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Medical record doesn't exist!");
  }


  @Test
  @DisplayName("updateMedicalRecords should update one medicalRecords")
  void updateMedicalRecords_ShouldUpdateOne_forGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Cartman",
        "Eric", "23/02/81", List.of("algermine:880"), List.of("ophtax"));
    // WHEN
    medicalRecordsDaoImpl.updateMedicalRecords(new MedicalRecordsDto().convertToDto(given));

    // THEN
    assertThat(medicalRecordsDaoImpl.getMedicalRecordsList()).contains(given);
  }


  @Test
  @DisplayName("updateMedicalRecords should return given medicalRecords")
  void updateMedicalRecords_ShouldReturnGivenMedicalRecords_forGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Cartman",
        "Eric", "23/02/81", List.of("algermine:880"), List.of("ophtax"));

    // WHEN
    MedicalRecords result =
        medicalRecordsDaoImpl.updateMedicalRecords(new MedicalRecordsDto().convertToDto(given));

    // THEN
    assertThat(result).isEqualTo(given);
  }


  @Test
  @DisplayName("deleteMedicalRecords should throw no such element exception")
  void deleteMedicalRecords_ShouldThrowNoSuchElementException_forNotExistMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Daniel",
        "Rousseau", "23/02/23", List.of("algermine:880"), List.of("ophtax"));

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> medicalRecordsDaoImpl.deleteMedicalRecords(new MedicalRecordsDto().convertToDto(given)));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Medical record doesn't exist !");
  }


  @Test
  @DisplayName("deleteMedicalRecords should delete one medicalRecords")
  void deleteMedicalRecords_ShouldDeleteOne_forGivenMedicalRecords() {
    // GIVEN
    MedicalRecords given = new MedicalRecords("Cartman",
        "Eric", "23/02/81", List.of("algermine:880"), List.of("ophtax"));

    // WHEN
    medicalRecordsDaoImpl.deleteMedicalRecords(new MedicalRecordsDto().convertToDto(given));

    // THEN
    assertThat(medicalRecordsDaoImpl.getMedicalRecordsList()).doesNotContain(given);
  }

  @Test
  @DisplayName("getPersonBirthdate should return person birthdate")
  void getPersonBirthdate_ShouldReturnPersonBirthDate_forGivenFirstnameAndLastname() {
    // GIVEN
    String firstname = "Cartman";
    String lastname = "Eric";

    // WHEN
    String result = medicalRecordsDaoImpl.getPersonBirthdate(firstname, lastname);

    // THEN
    assertThat(result).isEqualTo("23/02/81");
  }
}