package com.safetynet.alert.dao;

import com.safetynet.alert.model.Station;
import java.util.List;
import java.util.Optional;

/**
 * StationDao interface structure the business logic
 * of station.
 */
public interface StationDao {

  /**
   * getStationList. Method that get a
   * list of station from a data source.
   *
   * @return a Station list
   */
  List<Station> getStationList();

  /**
   * saveStation. Method that save an
   * Station to a data source.
   *
   * @param station an FireStation object
   * @return an FireStation object
   */
  Station saveStation(Station station);

  /**
   * updateStation. Method that update an
   * Station in a data source.
   *
   * @param station an FireStation object
   * @return a FireStation object
   */
  Station updateStation(Station station);

  /**
   * deleteStation. Method that delete an
   * Station in a data source.
   *
   * @param station an FireStation object
   */
  void deleteStation(Station station);

  /**
   * getStationByStation. Method that get or not a
   * Station from a data source.
   *
   * @param station a station
   * @return an optional Station object
   */
  Optional<Station> getStationByStation(String station);

}
