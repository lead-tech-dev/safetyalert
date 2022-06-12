package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.AddressDao;
import com.safetynet.alert.dao.FireStationDao;
import com.safetynet.alert.dao.StationDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dto.firestation.FireStationDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Station;
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
 * FireStationDaoImpl. class that implement
 *  FireStation business logic
 */
@Service
public class FireStationDaoImpl implements FireStationDao {

  private final List<FireStation> fireStationData;

  private final AddressDao addressDao;

  private final StationDao stationDao;


  /**
   * Constructor of class FireStationDaoImpl.
   *
   * @param addressDao a addressDao
   * @param stationDao a stationDao
   */
  public FireStationDaoImpl(AddressDao addressDao, StationDao stationDao) {
    this.fireStationData = Database.getFireStationData();
    this.addressDao = addressDao;
    this.stationDao = stationDao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<FireStationDto> getFireStationList() {
    return fireStationData.stream()
        .map(ConvertDto::convertToFireStationListDto)
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FireStationDto saveFireStation(FireStationDto fireStationDto) {
    Optional<Address> existAddress =
        addressDao.getAddressByStreet(fireStationDto.getAddress());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address  doesn't exist !");
    }

    Optional<Station> existFireStation =
        stationDao.getStationByStation(fireStationDto.getStation());
    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("Station doesn't exist !");
    }

    Optional<FireStation> existAddressFireStation =
        getFireStationByAddressAndStation(fireStationDto.getAddress(),
            fireStationDto.getStation());
    if (existAddressFireStation.isPresent()) {
      throw new BadRequestException("Mapping address/station already exist !");
    }

    fireStationData.add(new FireStation(
        new Address(existAddress.get().getStreet(), existAddress.get().getCity(),
            existAddress.get().getZip()),
        new Station(existFireStation.get().getStation())));

    return fireStationDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FireStationDto updateFireStation(
      FireStationDto fireStationDto) {

    Optional<FireStation> existFireStation =
        getFireStationByAddress(fireStationDto.getAddress());

    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("Mapping address/station doesn't exist !");
    }

    Optional<Station> existStation =
        stationDao.getStationByStation(fireStationDto.getStation());
    if (existStation.isEmpty()) {
      throw new NoSuchElementException("Station doesn't exist !");
    }

    fireStationData.set(
        fireStationData.indexOf(existFireStation.get()
        ), new FireStation(existFireStation.get().getAddress(), new Station(
            fireStationDto.getStation())));

    return fireStationDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteFireStation(FireStationDto fireStationDto) {
    Optional<FireStation> existFireStationAddress =
        getFireStationByAddress(fireStationDto.getAddress());

    Optional<FireStation> existFireStationStation =
        getFireStationByStation(fireStationDto.getStation());

    if (existFireStationAddress.isEmpty() && existFireStationStation.isEmpty()) {
      throw new NoSuchElementException("station or address or both doesn't exit");
    }

    if (existFireStationAddress.isPresent()) {
      deleteFireStationByAddress(fireStationDto.getAddress());
    }

    if (existFireStationStation.isPresent()) {
      deleteFireStationByStation(fireStationDto.getStation());
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<FireStation> getFireStationByAddressAndStation(String address,
                                                                 String station) {
    return fireStationData.stream()
        .filter(current -> Objects.equals(current.getAddress().getStreet(), address)
            && Objects.equals(current.getFirestation().getStation(), station))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<FireStation> getFireStationByAddress(String address) {

    return fireStationData.stream()
        .filter(current -> Objects.equals(current.getAddress().getStreet(), address))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<FireStation> getFireStationByStation(String station) {
    return fireStationData.stream()
        .filter(current -> Objects.equals(current.getFirestation().getStation(), station))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteFireStationByAddress(String address) {
    List<FireStation> removedAddress = new ArrayList<>();

    for (FireStation current : fireStationData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        removedAddress.add(current);
      }
    }

    fireStationData.removeAll(removedAddress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteFireStationByStation(String station) {
    List<FireStation> removedStation = new ArrayList<>();

    for (FireStation current : fireStationData) {
      if (Objects.equals(current.getFirestation().getStation(), station)) {
        removedStation.add(current);
      }
    }

    fireStationData.removeAll(removedStation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getAddressListByStation(String station) {

    return fireStationData.stream()
        .distinct()
        .filter(current -> Objects.equals(current.getFirestation().getStation(), station))
        .map(current -> current.getAddress().getStreet())
        .collect(Collectors.toList());

  }

  /**
   * {@inheritDoc}
   */
  public List<String> getStationListByAddress(String address) {
    List<String> stations = new ArrayList<>();

    for (FireStation current : fireStationData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        stations.add(current.getFirestation().getStation());
      }
    }

    return stations;
  }

}
