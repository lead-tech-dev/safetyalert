package com.safetynet.alert.dao;

import com.safetynet.alert.dto.addressfirestation.AddressFireStationDto;
import com.safetynet.alert.model.AddressFireStation;
import java.util.List;
import java.util.Optional;

/**
 * AddressFireStationDao interface structure the business logic
 * of mapping address/station.
 */
public interface AddressFireStationDao {

  /**
   * getAddressFireStationList. Method that get a
   * list of address/station from a data source.
   *
   * @return a mapping of address/station list
   */
  List<AddressFireStationDto> getAddressFireStationList();

  /**
   * saveFireStation. Method that save an
   * addressFireStation to a data source.
   *
   * @param addressFireStationDto an AddressFireStation object
   * @return an AddressFireStation object
   */
  AddressFireStationDto saveAddressFireStation(AddressFireStationDto addressFireStationDto);

  /**
   * updateFireStation. Method that update an
   * addressFireStation in a data source.
   *
   * @param addressFireStationDto an AddressFireStation object
   * @return an address object
   */
  AddressFireStationDto updateAddressFireStation(AddressFireStationDto addressFireStationDto);

  /**
   * deleteFireStation. Method that delete an
   * AddressFireStation in a data source.
   *
   * @param addressFireStationDto an AddressFireStation object
   */
  void deleteAddressFireStation(AddressFireStationDto addressFireStationDto);

  /**
   * getFireStationByAddressAndStation. Method that get or not an
   * AddressFireStation from a data source.
   *
   * @param address an address street
   * @param station a fireStation
   * @return an optional AddressFireStation object
   */
  Optional<AddressFireStation> getFireStationByAddressAndStation(String address,
                                                                 String station);

  /**
   * getFireStationByAddress. Method that get or not an
   * AddressFireStation from a data source.
   *
   * @param address a street address
   * @return an optional AddressFireStation object
   */
  Optional<AddressFireStation> getAddressFireStationByAddress(String address);

  /**
   * getFireStationByStation. Method that get or not an
   * AddressFireStation from a data source.
   *
   * @param station a fireStation station
   * @return an optional AddressFireStation object
   */
  Optional<AddressFireStation> getAddressFireStationByStation(String station);

  /**
   * deleteByAddress. Method that delete an
   * AddressFireStation from in data source.
   *
   * @param address an address street
   */
  void deleteAddressFireStationByAddress(String address);

  /**
   * deleteByAddress. Method that delete an
   * AddressFireStation from in data source.
   *
   * @param station an fireStation station
   */
  void deleteAddressFireStationByStation(String station);

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
