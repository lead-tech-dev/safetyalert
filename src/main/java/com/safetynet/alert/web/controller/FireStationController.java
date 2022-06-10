package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.FireStationDao;
import com.safetynet.alert.model.FireStation;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FireStationController. class that implement
 * request/response logic of FireStation.
 */
@RestController
@RequestMapping("/firestations")
public class FireStationController {

  private static final Logger log = LoggerFactory.getLogger(AddressFireStationController.class);
  private final FireStationDao fireStationDao;

  public FireStationController(FireStationDao fireStationDao) {
    this.fireStationDao = fireStationDao;
  }

  /**
   * getFireStation. Method that return a
   * list of FireStation for response.
   *
   * @return FireStation list
   */
  @GetMapping
  public List<FireStation> getFireStation() {
    log.info("Getting address list");

    List<FireStation> fireStations = fireStationDao.getFireStationList();

    log.info("Get fireStations request success. fireStations found with data {}",
        fireStations);

    return fireStations;
  }

  /**
   * saveFireStation. Method that get an FireStation
   * of request and return a save FireStation for response.
   *
   * @param fireStation a given FireStation
   * @return FireStation object
   */
  @PostMapping
  public ResponseEntity<FireStation> saveFireStation(@Valid @RequestBody FireStation fireStation) {
    log.info("Saving fireStation with fireStation {}", fireStation);

    FireStation fireStationAdded = fireStationDao.saveFireStation(fireStation);

    if (Objects.isNull(fireStationAdded)) {
      return ResponseEntity.noContent().build();
    }

    log.info("Save fireStation request success. Saved with fireStation {}",
        fireStation);

    return new ResponseEntity<>(fireStationAdded, HttpStatus.CREATED);
  }

  /**
   * updateFireStation. Method that get an FireStation
   * of request and return an update FireStation for response.
   *
   * @param fireStation a given addressFireStationDto
   * @return FireStation object
   */
  @PutMapping
  public FireStation updateFireStation(@Valid @RequestBody FireStation fireStation) {
    log.info("Updating fireStation with fireStation {}", fireStation);

    FireStation updated = fireStationDao.updateFireStation(fireStation);

    log.info("Update fireStation request success. fireStation updated with {}",
        fireStation);

    return updated;
  }

  /**
   * deleteFireStation. Method that get an FireStation
   * from request.
   *
   * @param fireStation a given FireStation
   */
  @DeleteMapping
  public void deleteFireStation(@Valid @RequestBody FireStation fireStation) {
    log.info("Deleting addressFireStation {}", fireStation);

    fireStationDao.deleteFireStation(fireStation);

    log.info("Delete fireStation request success {}", fireStation);
  }
}
