package com.safetynet.alert.web.controller;


import com.safetynet.alert.dao.FireStationDao;
import com.safetynet.alert.dto.firestation.FireStationDto;
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

  private static final Logger log = LoggerFactory.getLogger(FireStationController.class);
  private final FireStationDao fireStationDao;

  public FireStationController(FireStationDao fireStationDao) {
    this.fireStationDao = fireStationDao;
  }

  /**
   * getFireStation. Method that return a
   * list of FireStationDto for response.
   *
   * @return FireStation list
   */
  @GetMapping
  public List<FireStationDto> getFireStation() {
    log.info("Getting address list");

    List<FireStationDto> fireStationDto =
        this.fireStationDao.getFireStationList();

    log.info("Get FireStation request success. FireStation found with data {}",
        fireStationDto);

    return fireStationDto;
  }

  /**
   * saveFireStation. Method that get an FireStationDto
   * of request and return a save FireStationDto for response.
   *
   * @param fireStationDto a given fireStationDto
   * @return FireStationDto object
   */
  @PostMapping
  public ResponseEntity<FireStationDto> saveFireStation(@Valid @RequestBody
                                                               FireStationDto fireStationDto) {
    log.info("Saving fireStation with fireStation {}", fireStationDto);

    FireStationDto fireStationAdded =
        fireStationDao.saveFireStation(fireStationDto);
    if (Objects.isNull(fireStationDto)) {
      return ResponseEntity.noContent().build();
    }

    log.info("Save fireStation request success. Saved with fireStation {}",
        fireStationDto);

    return new ResponseEntity<>(fireStationAdded, HttpStatus.CREATED);
  }

  /**
   * updateFireStation. Method that get an FireStationDto
   * of request and return an update FireStationDto for response.
   *
   * @param fireStationDto a given fireStationDto
   * @return FireStationDto object
   */
  @PutMapping
  public FireStationDto updateFireStation(@Valid @RequestBody
                                                 FireStationDto fireStationDto) {
    log.info("Updating fireStation with fireStation {}", fireStationDto);

    FireStationDto updated =
        fireStationDao.updateFireStation(fireStationDto);

    log.info("Update fireStation request success. fireStation updated with {}",
        fireStationDto);

    return updated;
  }

  /**
   * deleteFireStation. Method that get an FireStationDto
   * from request.
   *
   * @param fireStationDto a given fireStationDto
   */
  @DeleteMapping
  public void deleteFireStation(@RequestBody FireStationDto fireStationDto) {
    log.info("Deleting fireStation {}", fireStationDto);

    fireStationDao.deleteFireStation(fireStationDto);

    log.info("Delete fireStation request success {}", fireStationDto);
  }
}
