package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.MedicalRecordsDao;
import com.safetynet.alert.dto.medicalrecords.MedicalRecordsDto;
import com.safetynet.alert.model.MedicalRecords;
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
 * MedicalRecordsController. class that implement
 * request/response logic of medicalRecord.
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

  private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);
  private final MedicalRecordsDao medicalRecordsDao;


  public MedicalRecordsController(MedicalRecordsDao medicalRecordsDao) {
    this.medicalRecordsDao = medicalRecordsDao;
  }

  /**
   * getMedicalRecords. Method that return a
   * list of MedicalRecords to response.
   *
   * @return a list of MedicalRecords to response
   */
  @GetMapping
  public List<MedicalRecords> getMedicalRecords() {
    logger.info("Getting medicalRecords list");

    List<MedicalRecords> result = medicalRecordsDao.getMedicalRecordsList();

    logger.info("getMedicalRecords request success. MedicalRecords found with data {}",
        result);
    return result;
  }

  /**
   * saveMedicalRecord. Method that get an MedicalRecord
   * for request and return a save MedicalRecord in response.
   *
   * @param medicalRecordsDto a given MedicalRecord
   * @return MedicalRecord object
   */
  @PostMapping
  public ResponseEntity<MedicalRecords> saveMedicalRecord(
      @Valid @RequestBody MedicalRecordsDto medicalRecordsDto) {
    logger.info("Saving MedicalRecords with MedicalRecords {}", medicalRecordsDto);
    MedicalRecords medicalRecordAdded = medicalRecordsDao.saveMedicalRecords(medicalRecordsDto);

    if (Objects.isNull(medicalRecordAdded)) {
      return ResponseEntity.noContent().build();
    }

    logger.info("saveMedicalRecord request success. Saved with MedicalRecord {}",
        medicalRecordAdded);
    return new ResponseEntity<>(medicalRecordAdded, HttpStatus.CREATED);
  }

  /**
   * updateMedicalRecord. Method that get an MedicalRecord
   * from request and return an update MedicalRecord for response.
   *
   * @param medicalRecordsDto a given medicalRecords
   * @return MedicalRecord object
   */
  @PutMapping
  public MedicalRecords updateMedicalRecord(
      @Valid @RequestBody MedicalRecordsDto medicalRecordsDto) {
    logger.info("Updating MedicalRecord with MedicalRecord {}", medicalRecordsDto);

    MedicalRecords updated = medicalRecordsDao.updateMedicalRecords(medicalRecordsDto);

    logger.info("Update MedicalRecords request success. MedicalRecords updated with {}",
        updated);

    return updated;
  }

  /**
   * deleteMedicalRecord. Method that get an medicalRecords
   * from request.
   *
   * @param medicalRecordsDto a given medicalRecords
   */
  @DeleteMapping
  public void deleteMedicalRecord(@Valid @RequestBody MedicalRecordsDto medicalRecordsDto) {
    logger.info("Deleting MedicalRecord {}", medicalRecordsDto);

    medicalRecordsDao.deleteMedicalRecords(medicalRecordsDto);

    logger.info("Delete fireStation request success {}", medicalRecordsDto);
  }
}
