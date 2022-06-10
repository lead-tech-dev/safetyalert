package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.AddressDao;
import com.safetynet.alert.dao.AddressFireStationDao;
import com.safetynet.alert.dao.FireStationDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dto.addressfirestation.AddressFireStationDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.AddressFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.utils.ConvertDto;
import com.safetynet.alert.web.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * AddressFireStationDaoImpl. class that implement
 *  AddressFireStation business logic
 */
@Service
public class AddressFireStationDaoImpl implements AddressFireStationDao {

  private final List<AddressFireStation> addressFireStationData;

  private final AddressDao addressDao;

  private final FireStationDao fireStationDao;


  /**
   * Constructor of class AddressFireStationDaoImpl.
   *
   * @param addressDao a addressDao
   * @param fireStationDao afireStationDao
   */
  public AddressFireStationDaoImpl(AddressDao addressDao, FireStationDao fireStationDao) {
    this.addressFireStationData = Database.getAddressFireStationData();
    this.addressDao = addressDao;
    this.fireStationDao = fireStationDao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AddressFireStationDto> getAddressFireStationList() {
    return addressFireStationData.stream()
        .map(ConvertDto::convertToAddressFireStationListDto)
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AddressFireStationDto saveAddressFireStation(AddressFireStationDto addressFirestationDto) {
    Optional<Address> existAddress =
        addressDao.getAddressByStreet(addressFirestationDto.getAddress());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address  doesn't exist !");
    }

    Optional<FireStation> existFireStation =
        fireStationDao.getFireStationByStation(addressFirestationDto.getStation());
    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("Station doesn't exist !");
    }

    Optional<AddressFireStation> existAddressFireStation =
        getFireStationByAddressAndStation(addressFirestationDto.getAddress(),
            addressFirestationDto.getStation());
    if (existAddressFireStation.isPresent()) {
      throw new BadRequestException("Mapping address/station already exist !");
    }

    addressFireStationData.add(new AddressFireStation(
        new Address(existAddress.get().getStreet(), existAddress.get().getCity(),
            existAddress.get().getZip()),
        new FireStation(existFireStation.get().getStation())));

    return addressFirestationDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AddressFireStationDto updateAddressFireStation(
      AddressFireStationDto addressFirestationDto) {

    Optional<AddressFireStation> existAddressStation =
        getAddressFireStationByAddress(addressFirestationDto.getAddress());

    if (existAddressStation.isEmpty()) {
      throw new NoSuchElementException("Mapping address/station doesn't exist !");
    }

    Optional<FireStation> existStation =
        fireStationDao.getFireStationByStation(addressFirestationDto.getStation());
    if (existStation.isEmpty()) {
      throw new NoSuchElementException("Station doesn't exist !");
    }

    addressFireStationData.set(
        addressFireStationData.indexOf(existAddressStation.get()
        ), new AddressFireStation(existAddressStation.get().getAddress(), new FireStation(
            addressFirestationDto.getStation())));

    return addressFirestationDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAddressFireStation(AddressFireStationDto addressFirestationDto) {
    Optional<AddressFireStation> existFireStationAddress =
        getAddressFireStationByAddress(addressFirestationDto.getAddress());

    Optional<AddressFireStation> existFireStationStation =
        getAddressFireStationByStation(addressFirestationDto.getStation());

    if (existFireStationAddress.isEmpty() && existFireStationStation.isEmpty()) {
      throw new NoSuchElementException("station or address or both doesn't exit");
    }

    if (existFireStationAddress.isPresent()) {
      deleteAddressFireStationByAddress(addressFirestationDto.getAddress());
    }

    if (existFireStationStation.isPresent()) {
      deleteAddressFireStationByStation(addressFirestationDto.getStation());
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AddressFireStation> getFireStationByAddressAndStation(String address,
                                                                        String station) {
    return addressFireStationData.stream()
        .filter(current -> Objects.equals(current.getAddress().getStreet(), address)
            && Objects.equals(current.getFirestation().getStation(), station))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AddressFireStation> getAddressFireStationByAddress(String address) {

    return addressFireStationData.stream()
        .filter(current -> Objects.equals(current.getAddress().getStreet(), address))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AddressFireStation> getAddressFireStationByStation(String station) {
    return addressFireStationData.stream()
        .filter(current -> Objects.equals(current.getFirestation().getStation(), station))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAddressFireStationByAddress(String address) {
    List<AddressFireStation> removedAddress = new ArrayList<>();

    for (AddressFireStation current : addressFireStationData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        removedAddress.add(current);
      }
    }

    addressFireStationData.removeAll(removedAddress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAddressFireStationByStation(String station) {
    List<AddressFireStation> removedStation = new ArrayList<>();

    for (AddressFireStation current : addressFireStationData) {
      if (Objects.equals(current.getFirestation().getStation(), station)) {
        removedStation.add(current);
      }
    }

    addressFireStationData.removeAll(removedStation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getAddressListByStation(String station) {
    List<String> addresses = new ArrayList<>();

    for (AddressFireStation current : addressFireStationData) {
      if (Objects.equals(current.getFirestation().getStation(), station)
          && !addresses.contains(current.getAddress().getStreet())) {
        addresses.add(current.getAddress().getStreet());
      }
    }

    return addresses;
  }

  /**
   * {@inheritDoc}
   */
  public List<String> getStationListByAddress(String address) {
    List<String> stations = new ArrayList<>();

    for (AddressFireStation current : addressFireStationData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        stations.add(current.getFirestation().getStation());
      }
    }

    return stations;
  }

}
