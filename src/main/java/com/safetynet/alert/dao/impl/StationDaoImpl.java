package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.StationDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.model.Station;
import com.safetynet.alert.web.exception.BadRequestException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;


/**
 * FireStationDaoImpl. class that implement
 *  fireStation business logic
 */
@Service
public class StationDaoImpl implements StationDao {

  private final List<Station> fireStationData;

  public StationDaoImpl() {
    this.fireStationData = Database.getStationData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Station> getStationList() {
    return fireStationData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Station saveStation(Station station) {
    Optional<Station> existFireStation = getStationByStation(station.getStation());
    if (existFireStation.isPresent()) {
      throw new BadRequestException("FireStation already exist!");
    }
    fireStationData.add(station);

    return station;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Station updateStation(Station station) {

    Optional<Station> existFireStation = getStationByStation(station.getStation());

    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("Station doesn't exist!");
    }

    fireStationData.set(fireStationData.indexOf(existFireStation.get()), station);

    return station;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteStation(Station station) {
    Optional<Station> existFireStation = getStationByStation(station.getStation());
    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("FireStation doesn't exist!");
    }

    fireStationData.remove(station);
  }

  /**
   * {@inheritDoc}
   */
  public Optional<Station> getStationByStation(String station) {
    return fireStationData.stream()
        .filter(current -> Objects.equals(current.getStation(), station))
        .findFirst();
  }
}
