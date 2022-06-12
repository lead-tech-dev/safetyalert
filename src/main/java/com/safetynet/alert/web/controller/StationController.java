package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.StationDao;
import com.safetynet.alert.model.Station;
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
 * StationController. class that implement
 * request/response logic of Station.
 */
@RestController
@RequestMapping("/station")
public class StationController {

  private static final Logger log = LoggerFactory.getLogger(StationController.class);
  private final StationDao StationDao;

  public StationController(StationDao StationDao) {
    this.StationDao = StationDao;
  }

  /**
   * getStation. Method that return a
   * list of Station for response.
   *
   * @return Station list
   */
  @GetMapping
  public List<Station> getStation() {
    log.info("Getting address list");

    List<Station> Stations = StationDao.getStationList();

    log.info("Get Stations request success. Stations found with data {}",
        Stations);

    return Stations;
  }

  /**
   * saveStation. Method that get a Station
   * of request and return a save Station for response.
   *
   * @param Station a given Station
   * @return Station object
   */
  @PostMapping
  public ResponseEntity<Station> saveStation(@Valid @RequestBody Station Station) {
    log.info("Saving Station with Station {}", Station);

    Station StationAdded = StationDao.saveStation(Station);

    if (Objects.isNull(StationAdded)) {
      return ResponseEntity.noContent().build();
    }

    log.info("Save Station request success. Saved with Station {}",
        Station);

    return new ResponseEntity<>(StationAdded, HttpStatus.CREATED);
  }

  /**
   * updateStation. Method that get an Station
   * of request and return an update Station for response.
   *
   * @param Station a given addressStationDto
   * @return Station object
   */
  @PutMapping
  public Station updateStation(@Valid @RequestBody Station Station) {
    log.info("Updating Station with Station {}", Station);

    Station updated = StationDao.updateStation(Station);

    log.info("Update Station request success. Station updated with {}",
        Station);

    return updated;
  }

  /**
   * deleteStation. Method that get an Station
   * from request.
   *
   * @param Station a given Station
   */
  @DeleteMapping
  public void deleteStation(@Valid @RequestBody Station Station) {
    log.info("Deleting station {}", Station);

    StationDao.deleteStation(Station);

    log.info("Delete station request success {}", Station);
  }
}
