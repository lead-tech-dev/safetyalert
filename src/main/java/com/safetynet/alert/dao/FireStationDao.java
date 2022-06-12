package com.safetynet.alert.dao;

import com.safetynet.alert.dto.firestation.FireStationDto;
import com.safetynet.alert.model.FireStation;
import java.util.List;
import java.util.Optional;

/**
 * FireStationDao interface structure the business logic
 * of mapping address/station.
 */
public interface FireStationDao {

  /**
   * getStationList. Method that get a
   * list of address/station from a data source.
   *
   * @return a mapping of address/station list
   */
  List<FireStationDto> getFireStationList();

  /**
   * saveStation. Method that save an
   * addressFireStation to a data source.
   *
   * @param fireStationDto an AddressFireStation object
   * @return an FireStation object
   */
  FireStationDto saveFireStation(FireStationDto fireStationDto);

  /**
   * updateStation. Method that update an
   * addressFireStation in a data source.
   *
   * @param addressFireStationDto an AddressFireStation object
   * @return an address object
   */
  FireStationDto updateFireStation(FireStationDto addressFireStationDto);

  /**
   * deleteStation. Method that delete an
   * FireStation in a data source.
   *
   * @param fireStationDto an FireStation object
   */
  void deleteFireStation(FireStationDto fireStationDto);

  /**
   * getFireStationByAddressAndStation. Method that get or not an
   * AddressFireStation from a data source.
   *
   * @param address an address street
   * @param station a fireStation
   * @return an optional FireStation object
   */
  Optional<FireStation> getFireStationByAddressAndStation(String address,
                                                          String station);

  /**
   * getFireStationByAddress. Method that get or not an
   * FireStation from a data source.
   *
   * @param address a street address
   * @return an optional FireStation object
   */
  Optional<FireStation> getFireStationByAddress(String address);

  /**
   * getStationByStation. Method that get or not an
   * FireStation from a data source.
   *
   * @param station a fireStation station
   * @return an optional FireStation object
   */
  Optional<FireStation> getFireStationByStation(String station);

  /**
   * deleteByAddress. Method that delete an
   * FireStation from in data source.
   *
   * @param address an address street
   */
  void deleteFireStationByAddress(String address);

  /**
   * deleteByAddress. Method that delete an
   * FireStation from in data source.
   *
   * @param station an fireStation station
   */
  void deleteFireStationByStation(String station);

  /**
   * getAddressListByStation. Method that get a list
   * of String from a data source.
   *
   * @param station a fireStation station
   * @return a String List
   */
  List<String> getAddressListByStation(String station);

  /**
   * getAddressListByStation. Method that get a list
   * of String from a data source.
   *
   * @param address an address street
   * @return a AddressFireStation List
   */
  List<String> getStationListByAddress(String address);

}
