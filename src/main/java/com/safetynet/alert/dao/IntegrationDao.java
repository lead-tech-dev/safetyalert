package com.safetynet.alert.dao;

import com.safetynet.alert.dto.integration.PersonInfoListDto;
import com.safetynet.alert.model.Person;
import java.util.List;
import java.util.Map;

/**
 * IntegrationDao interface structure the business logic
 * of Integration urls.
 *
 */
public interface IntegrationDao {

  /**
   * getPersonStationListByAddresses. Method that get a
   * list of people covered by the fire station.
   *
   * @param stationNumber a station number
   *
   * @return a map of adult number, child number
   * and Person list
   */
  Map<String, Object> getPersonStationListByAddresses(String stationNumber);

  /**
   * getChildList. Method that get a
   * list of children living at this address.
   *
   * @param address a given address
   *
   * @return a map of child and other family members
   */
  Map<String, Object> getChildList(String address);

  /**
   * getPersonFireAddressList. Method that get
   * the list of inhabitants living at the given
   * address as well as the number of the barracks
   * of firefighters serving it.
   *
   * @param address a given address
   *
   * @return a map of station and residents
   */
  Map<String, Object> getPersonFireAddressList(String address);

  /**
   * getPersonPhoneList. Method that get
   * a list of resident phone numbers.
   *
   * @param fireStation a given station
   *
   * @return String list of phone number
   */
  List<String> getPersonPhoneList (String fireStation);

  /**
   * getPersonFloodStationList. Method that get a
   * list of all households served by the station.
   *
   * @param stations an array of station
   *
   * @return Map of address and person list
   */
  Map<String, Object> getPersonFloodStationList(String[] stations);

  /**
   * getPersonInfoList. Method that get a
   * list of person with medicalRecors.
   *
   * @param firstname a person firstname
   * @param lastname a person lastname
   *
   * @return Person List with medicalRecords
   */
  List<PersonInfoListDto> getPersonInfoList(String firstname, String lastname);

  /**
   * getCommunityEmailList. Method that get a
   * list of city email.
   *
   * @param city a given city
   *
   * @return String List of email
   */
  List<String> getCommunityEmailList(String city);

  /**
   * getPersonListByAddress. Method that get
   * a list of person by address.
   *
   * @param address a given address
   *
   * @return Person list
   */
  List<Person> getPersonListByAddress (String address);

}
