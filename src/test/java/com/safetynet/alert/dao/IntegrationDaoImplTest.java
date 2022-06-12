package com.safetynet.alert.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.FireStationDaoImpl;
import com.safetynet.alert.dao.impl.IntegrationDaoImpl;
import com.safetynet.alert.dao.impl.MedicalRecordsDaoImpl;
import com.safetynet.alert.dao.impl.PersonDaoImpl;
import com.safetynet.alert.dto.integration.ChildListDto;
import com.safetynet.alert.dto.integration.PersonInfoListDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.utils.ConvertDto;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@Tag("IntegrationDaoImplTest")
@DisplayName("Integration data access object")
class IntegrationDaoImplTest {

  private static MockedStatic<Database> database;
  private static IntegrationDaoImpl integrationDaoImpl;

  @Mock
  SimpleDateFormat formatter;
  @Mock
  private PersonDaoImpl personDaoImpl;

  @Mock
  private FireStationDaoImpl addressFireStationDaoImpl;

  @Mock
  private MedicalRecordsDaoImpl medicalRecordsDaoImpl;

  @BeforeEach
  private void setUp() {

    List<Person> persons = new ArrayList<>();
    persons.add(
        new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
            new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
            0, new MedicalRecords()));

    database = mockStatic(Database.class);

    when(Database.getPersonDataInfo()).thenReturn(persons);

    integrationDaoImpl =
        new IntegrationDaoImpl(personDaoImpl, addressFireStationDaoImpl, medicalRecordsDaoImpl);
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }


  @Test
  @DisplayName("getPersonStationListByAddresses should return list of people covered by the fire " +
      "station")
  void getPersonStationListByAddresses_ShouldReturnPersonListCoveredByFireStation_ForGivenStationNumber() {
    // GIVEN
    Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    when(addressFireStationDaoImpl.getAddressListByStation("3")).thenReturn(
        List.of("7 rue lucien deneau"));

    when(personDaoImpl.getPersonByAddress("7 rue lucien deneau")).thenReturn(person);

    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/81");

    // WHEN
    Map<String, Object> result = integrationDaoImpl.getPersonStationListByAddresses("3");

    // THEN
    assertThat(result, IsMapContaining.hasEntry("Adult number", 1));
    assertThat(result, IsMapContaining.hasEntry("Child number", 0));
    assertThat(result, IsMapContaining.hasEntry("Data",
        List.of(ConvertDto.convertToPersonStationListDto(person))));
  }

  @Test
  @DisplayName("getChildList should return list of children living at this address")
  void getChildList_ShouldReturnEmptyListChildrenLivingAt_ForGivenAddress() {
    // GIVEN
    String given = "7 rue lucien deneau";
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/1981");

    // WHEN
    Map<String, Object> result = integrationDaoImpl.getChildList(given);

    // THEN
    assertThat(result.size(), is(0));
  }

  @Test
  @DisplayName("getChildList should return list of children living at this address")
  void getChildList_ShouldReturnChildrenListLivingAt_ForGivenAddress() {
    // GIVEN
    Map<String, Object> expected = new HashMap<>();
    expected.put("Other family members", Collections.EMPTY_LIST);
    expected.put("Children at this address", List.of(new ChildListDto("Cartman", "Eric", 3)));
    String given = "7 rue lucien deneau";
    Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("03/06/2019");

    // WHEN
    Map<String, Object> result = integrationDaoImpl.getChildList(given);

    // THEN
    assertEquals(result, expected);
  }

  @Test
  @DisplayName("getPersonPhoneList should return list resident phone numbers")
  void getPersonPhoneList_ShouldReturnResidentList_ForGivenFireStation() {
    // GIVEN
    Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    String given = "3";
    when(addressFireStationDaoImpl.getAddressListByStation(given)).thenReturn(List.of("7 rue " +
        "lucien deneau"));
    when(personDaoImpl.getPersonByAddress("7 rue lucien deneau")).thenReturn(person);

    // WHEN
    List<String> result = integrationDaoImpl.getPersonPhoneList("3");

    // THEN
    assertEquals(List.of("0758951895"), result);
  }

  @Test
  @DisplayName("getPersonListByAddress should return person list")
  void getPersonListByAddress_ShouldReturnPersonList_ForGivenAddress() {
    // GIVEN
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        40, new MedicalRecords());
    String given = "7 rue lucien deneau";
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/1981");

    // WHEN
    List<Person> result = integrationDaoImpl.getPersonListByAddress(given);

    // THEN
    assertEquals(List.of(expected), result);
  }

  @Test
  @DisplayName("getPersonFireAddressList should return of inhabitants living at the given address ")
  void getPersonFireAddressList_ShouldReturnInhabitantLivingAt_ForGivenAddress() {
    // GIVEN
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        40, new MedicalRecords());
    String given = "7 rue lucien deneau";
    when(addressFireStationDaoImpl.getStationListByAddress(given)).thenReturn(List.of("3"));
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/1981");

    // WHEN
    Map<String, Object> result = integrationDaoImpl.getPersonFireAddressList(given);

    // THEN
    assertThat(result, IsMapContaining.hasEntry("Station", List.of("3")));
    assertThat(result, IsMapContaining.hasEntry("Residents",
        List.of(ConvertDto.convertToPersonFireAddressListDto(expected))));
  }

  @Test
  @DisplayName("getPersonFloodStationList should return all households served by the station")
  void getPersonFloodStationList_ShouldReturnAllHouseholds_ForGivenArrayStation() {
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        40, new MedicalRecords());
    String[] stations = {"3"};
    when(addressFireStationDaoImpl.getAddressListByStation(stations[0])).thenReturn(List.of("7 " +
        "rue lucien deneau"));
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/1981");


    Map<String, Object> result = integrationDaoImpl.getPersonFloodStationList(stations);

    // THEN
    assertThat(result, IsMapContaining.hasEntry("7 rue lucien deneau",
        ConvertDto.convertToFloodStationListDto(List.of(expected))));
  }

  @Test
  @DisplayName("getPersonInfoList should return list of person with medicalRecords")
  void getPersonInfoList_ShouldReturnPersonWithMedicalRecords_ForGivenFirstnameAndLastname() {
    // GIVEN
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        40, new MedicalRecords());
    String firstname = "Cartman";
    String lastname = "Eric";
    when(medicalRecordsDaoImpl.getPersonBirthdate("Cartman", "Eric")).thenReturn("23/02/1981");

    // WHEN
    List<PersonInfoListDto> result = integrationDaoImpl.getPersonInfoList(firstname, lastname);

    // THEN
    assertEquals(List.of(ConvertDto.convertToPersonInfoListDto(expected)), result);
  }

  @Test
  @DisplayName("getCommunityEmailList should return list of city email")
  void getCommunityEmailList_ShouldReturnCityEmailList_ForGivenCity() {
    // GIVEN
    String expected = "ericmaximan@gmail.com";
    String given = "Mainvilliers";

    // WHEN
    List<String> result = integrationDaoImpl.getCommunityEmailList(given);

    // THEN
    assertEquals(List.of(expected), result);
  }
}