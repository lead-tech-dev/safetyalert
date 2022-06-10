package com.safetynet.alert.dao;

import com.safetynet.alert.model.FireStation;
import java.util.List;
import java.util.Optional;

/**
 * FireStationDao interface structure the business logic
 * of fireStation.
 *
 */
public interface FireStationDao {

   /**
    * getFireStationList. Method that get a
    * list of address/station from a data source.
    *
    * @return a FireStation list
    */
   List<FireStation> getFireStationList();

   /**
    * saveFireStation. Method that save an
    * FireStation to a data source.
    *
    * @param fireStation an FireStation object
    *
    * @return an FireStation object
    */
   FireStation saveFireStation(FireStation fireStation);

   /**
    * updateFireStation. Method that update an
    * FireStation in a data source.
    *
    * @param fireStation an FireStation object
    *
    * @return a FireStation object
    */
   FireStation updateFireStation(FireStation fireStation);

   /**
    * deleteFireStation. Method that delete an
    * FireStation in a data source.
    *
    * @param fireStation an FireStation object
    */
   void deleteFireStation(FireStation fireStation);

   /**
    * getFireStationByStation. Method that get or not an
    * FireStation from a data source.
    *
    * @param station a fireStation station
    *
    * @return an optional FireStation object
    */
   Optional<FireStation> getFireStationByStation(String station);

}
