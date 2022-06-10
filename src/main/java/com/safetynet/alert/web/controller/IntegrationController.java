package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.IntegrationDao;
import com.safetynet.alert.dto.integration.PersonInfoListDto;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * IntegrationController. class that implement
 * request/response logic of urls.
 */
@RestController
public class IntegrationController {

  private static final Logger log = LoggerFactory.getLogger(AddressFireStationController.class);
  private final IntegrationDao integrationDao;

  public IntegrationController(IntegrationDao integrationDao) {
    this.integrationDao = integrationDao;
  }

  /**
   * getPersonStationList. Method that get a station number from request
   * and return a list of people covered by the fire station
   * to response.
   *
   * @param stationNumber a given station number
   * @return a map of adult number, child number and Person list
   */
  @GetMapping("/firestation")
  public Map<String, Object> getPersonStationList(
      @RequestParam("stationNumber") String stationNumber) {
    log.info("Getting person station list by address for station number {}", stationNumber);

    Map<String, Object> result = integrationDao.getPersonStationListByAddresses(stationNumber);

    log.info("getPersonStationList request success. list found for station number {}",
        stationNumber);
    return result;
  }

  /**
   * getChildList. Method that get an address from request
   * and return a list  of children living at this address
   * to response.
   *
   * @param address a given address
   * @return a map of child and other family members
   */
  @GetMapping("/childAlert")
  public Map<String, Object> getChildList(@RequestParam("address") String address) {
    log.info("Getting child list for address {}", address);

    Map<String, Object> result = integrationDao.getChildList(address);

    log.info("getChildList request success. list found for address {}", address);
    return result;
  }

  /**
   * getPhoneAlertList. Method that get a station from request
   * and return a String list of phone number to response.
   *
   * @param fireStation a given station
   * @return a String list of phone number
   */
  @GetMapping("/phoneAlert")
  public List<String> getPhoneAlertList(@RequestParam("fireStation") String fireStation) {
    return integrationDao.getPersonPhoneList(fireStation);
  }

  /**
   * getPersonFireAddressList. Method that get an address from request
   * and return a map of station and residents in response.
   *
   * @param address a given address
   * @return a map of station and residents
   */
  @GetMapping("/fire")
  public Map<String, Object> getPersonFireAddressList(@RequestParam("address") String address) {
    log.info("Getting person fire address list for address {}", address);

    Map<String, Object> result = integrationDao.getPersonFireAddressList(address);

    log.info("getPersonFireAddressList request success. list found for address {}", address);
    return result;
  }

  /**
   * getPersonFloodStationList. Method that get an array of station
   * from request and map of address and person list in response.
   *
   * @param stations a given array of stations
   * @return a map of address and person list
   */
  @GetMapping("/flood/stations")
  public Map<String, Object> getPersonFloodStationList(@RequestParam String[] stations) {
    log.info("Getting person flood station list for station {}", (Object) stations);

    Map<String, Object> result = integrationDao.getPersonFloodStationList(stations);

    log.info("getPersonFloodStationList request success. list found for stations {}",
        (Object) stations);
    return result;
  }

  /**
   * getPersonInfoList. Method that get a firstname and lastname
   * from request and return a person List with medicalRecords
   * in response.
   *
   * @param firstName a given firstname
   * @param lastName  a given lastname
   * @return a person List with medicalRecords
   */
  @GetMapping("/personInfo")
  public List<PersonInfoListDto> getPersonInfoList(@RequestParam("firstName") String firstName,
                                                   @RequestParam("lastName") String lastName) {
    log.info("Getting person info list  for {}, {}", firstName, lastName);

    List<PersonInfoListDto> result = integrationDao.getPersonInfoList(firstName, lastName);

    log.info("getPersonInfoList request success. list found for firstname {}, lastname {}",
        firstName, lastName);
    return result;
  }

  /**
   * getCommunityEmailList. Method that get a city
   * from request and return a person email List
   * in response.
   *
   * @param city a given city
   * @return a person List email
   */
  @GetMapping("/communityEmail")
  public List<String> getCommunityEmailList(@RequestParam("city") String city) {
    log.info("Getting community email list  for city {}", city);

    List<String> result = integrationDao.getCommunityEmailList(city);

    log.info("getCommunityEmailList request success. list found for city {}", city);
    return result;
  }
}
