package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.FireStationDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.model.FireStation;
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
public class FireStationDaoImpl implements FireStationDao {

  private final List<FireStation> fireStationData;

  public FireStationDaoImpl() {
    this.fireStationData = Database.getFireStationData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<FireStation> getFireStationList() {
    return fireStationData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FireStation saveFireStation(FireStation fireStation) {
    Optional<FireStation> existFireStation = getFireStationByStation(fireStation.getStation());
    if (existFireStation.isPresent()) {
      throw new BadRequestException("FireStation already exist!");
    }
    fireStationData.add(fireStation);

    return fireStation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FireStation updateFireStation(FireStation fireStation) {

    Optional<FireStation> existFireStation = getFireStationByStation(fireStation.getStation());

    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("FireStation doesn't exist!");
    }

    fireStationData.set(fireStationData.indexOf(existFireStation.get()), fireStation);

    return fireStation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteFireStation(FireStation fireStation) {
    Optional<FireStation> existFireStation = getFireStationByStation(fireStation.getStation());
    if (existFireStation.isEmpty()) {
      throw new NoSuchElementException("FireStation doesn't exist!");
    }

    fireStationData.remove(fireStation);
  }

  /**
   * {@inheritDoc}
   */
  public Optional<FireStation> getFireStationByStation(String station) {
    return fireStationData.stream()
        .filter(current -> Objects.equals(current.getStation(), station))
        .findFirst();
  }
}
