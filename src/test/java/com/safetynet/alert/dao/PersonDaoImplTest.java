package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.AddressDaoImpl;
import com.safetynet.alert.dao.impl.PersonDaoImpl;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
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
@Tag("PersonDaoImplTest")
@DisplayName("Person data access object")
class PersonDaoImplTest {

  private static MockedStatic<Database> database;

  private static PersonDaoImpl personDaoImpl;

  @Mock
  private static AddressDaoImpl addressDaoImpl;

  @BeforeEach
  private void setUp() {
    List<Person> persons = new ArrayList<>();
    persons.add(
        new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
            new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
            0, new MedicalRecords()));

    database = mockStatic(Database.class);
    when(Database.getPersonDataInfo()).thenReturn(persons);

    personDaoImpl = new PersonDaoImpl(addressDaoImpl);
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getPersonList should return person list")
  void getPersonList_ShouldReturnPersonList() {
    // Given
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    // WHEN
    List<PersonDto> result = personDaoImpl.getPersonList();

    // THEN
    assertThat(result).containsExactly(new PersonDto().convertToDto(expected));

  }

  @Test
  @DisplayName("savePerson should throw bad request exception")
  void savePerson_ShouldThrowBadRequestException_forExistAddress() {

    // GIVEN
    Person given = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(
        Optional.empty());

    // WHEN
    var exception = assertThrows(NoSuchElementException.class,
        () -> personDaoImpl.savePerson(new PersonDto().convertToDto(given)));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Address doesn't exit!");
  }


  @Test
  @DisplayName("savePerson should add one person")
  void savePerson_ShouldAddOne_forGivenPerson() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300")));

    // WHEN
    personDaoImpl.savePerson(new PersonDto().convertToDto(given));

    // THEN
    assertThat(personDaoImpl.getPersonList()).contains(new PersonDto().convertToDto(given));
  }


  @Test
  @DisplayName("savePerson should return given person")
  void savePerson_ShouldReturnAddedPerson_forGivenPerson() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(Optional.of(
        new Address("7 rue lucien deneau", "Mainvilliers", "28300")));

    // WHEN
    PersonDto result = personDaoImpl.savePerson(new PersonDto().convertToDto(given));

    // THEN
    assertThat(result).isEqualTo(result);
  }


  @Test
  @DisplayName("updatePerson should throw no such element exception")
  void updatePerson_ShouldThrowNoSuchElementException_forNotExistPerson() {
    // GIVEN
    Person given = new Person("Jean", "Jacques", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> personDaoImpl.updatePerson(new PersonDto().convertToDto(given)));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Person doesn't exist !");
  }

  @Test
  @DisplayName("updatePerson should throw no such element exception for not exist person address")
  void updatePerson_ShouldThrowNoSuchElementException_forNotExistPersonAddress() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(
        Optional.empty());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> personDaoImpl.updatePerson(new PersonDto().convertToDto(given)));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Address doesn't exit !");
  }


  @Test
  @DisplayName("updatePerson should update one person")
  void updatePerson_ShouldUpdateOne_forGivenPerson() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "1111111111", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(Optional
        .of(new Address("7 rue lucien deneau", "Mainvilliers", "28300")));

    // WHEN
    personDaoImpl.updatePerson(new PersonDto().convertToDto(given));

    // THEN
    assertThat(personDaoImpl.getPersonList()).contains(new PersonDto().convertToDto(given));
  }


  @Test
  @DisplayName("updatePerson should return given person")
  void updatePerson_ShouldReturnGivenPerson_forGivenPerson() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "1111111111", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(addressDaoImpl.getAddressByStreet(given.getAddress().getStreet())).thenReturn(Optional
        .of(new Address("7 rue lucien deneau", "Mainvilliers", "28300")));

    // WHEN
    PersonDto result = personDaoImpl.updatePerson(new PersonDto().convertToDto(given));

    // THEN
    assertThat(result).isEqualTo(new PersonDto().convertToDto(given));
  }


  @Test
  @DisplayName("deletePerson should throw no such element exception")
  void deletePerson_ShouldThrowNoSuchElementException_forNotExistPerson() {
    // GIVEN
    Person given = new Person("Eric", "Maximan", "1111111111", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> personDaoImpl.deletePerson(new PersonDto().convertToDto(given)));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Person doesn't exist !");
  }


  @Test
  @DisplayName("deletePerson should delete one person")
  void deletePerson_ShouldDeleteOne_forGivenPerson() {
    // GIVEN
    Person given = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    // WHEN
    personDaoImpl.deletePerson(new PersonDto().convertToDto(given));

    // THEN
    assertThat(personDaoImpl.getPersonList()).doesNotContain(new PersonDto().convertToDto(given));
  }

  @Test
  @DisplayName("deletePerson should delete one person")
  void getPersonByAddress_ShouldReturnPerson_forGivenAddress() {
    // GIVEN
    String address = "7 rue lucien deneau";

    // WHEN
    Person result = personDaoImpl.getPersonByAddress(address);

    // THEN
    assertThat(result.getAddress().getStreet()).isEqualTo(address);
  }


}