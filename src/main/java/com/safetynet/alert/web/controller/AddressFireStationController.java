package com.safetynet.alert.web.controller;


import com.safetynet.alert.dao.AddressFireStationDao;
import com.safetynet.alert.dto.addressfirestation.AddressFireStationDto;
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
 * AddressFireStationController. class that implement
 * request/response logic of AddressFireStation.
 */
@RestController
@RequestMapping("/addressfirestations")
public class AddressFireStationController {

  private static final Logger log = LoggerFactory.getLogger(AddressFireStationController.class);
  private final AddressFireStationDao addressFireStationService;

  public AddressFireStationController(AddressFireStationDao addressFireStationService) {
    this.addressFireStationService = addressFireStationService;
  }

  /**
   * getAddressFireStation. Method that return a
   * list of AddressFireStationDto for response.
   *
   * @return AddressFireStation list
   */
  @GetMapping
  public List<AddressFireStationDto> getAddressFireStation() {
    log.info("Getting address list");

    List<AddressFireStationDto> addressFireStationDto =
        this.addressFireStationService.getAddressFireStationList();

    log.info("Get AddressFireStation request success. AddressFireStation found with data {}",
        addressFireStationDto);

    return addressFireStationDto;
  }

  /**
   * saveAddressFireStation. Method that get an AddressFireStationDto
   * of request and return a save AddressFireStationDto for response.
   *
   * @param addressFireStationDto a given addressFireStationDto
   * @return AddressFireStationDto object
   */
  @PostMapping
  public ResponseEntity<AddressFireStationDto> saveAddressFireStation(@Valid @RequestBody
         AddressFireStationDto addressFireStationDto) {
    log.info("Saving addressFireStation with addressFireStation {}", addressFireStationDto);

    AddressFireStationDto fireStationAdded =
        addressFireStationService.saveAddressFireStation(addressFireStationDto);
    if (Objects.isNull(addressFireStationDto)) {
      return ResponseEntity.noContent().build();
    }

    log.info("Save addressFireStation request success. Saved with addressFireStation {}",
        addressFireStationDto);

    return new ResponseEntity<>(fireStationAdded, HttpStatus.CREATED);
  }

  /**
   * updateAddressFireStation. Method that get an AddressFireStationDto
   * of request and return an update AddressFireStationDto for response.
   *
   * @param addressFireStationDto a given addressFireStationDto
   * @return AddressFireStationDto object
   */
  @PutMapping
  public AddressFireStationDto updateAddressFireStation(@Valid @RequestBody
             AddressFireStationDto addressFireStationDto) {
    log.info("Updating addressFireStation with addressFireStation {}", addressFireStationDto);

    AddressFireStationDto updated =
        addressFireStationService.updateAddressFireStation(addressFireStationDto);

    log.info("Update addressFireStation request success. addressFireStation updated with {}",
        addressFireStationDto);

    return updated;
  }

  /**
   * deleteAddressFireStation. Method that get an AddressFireStationDto
   * from request.
   *
   * @param addressFireStationDto a given addressFireStationDto
   */
  @DeleteMapping
  public void deleteAddressFireStation(@RequestBody AddressFireStationDto addressFireStationDto) {
    log.info("Deleting addressFireStation {}", addressFireStationDto);

    addressFireStationService.deleteAddressFireStation(addressFireStationDto);

    log.info("Delete addressFireStation request success {}", addressFireStationDto);
  }
}
