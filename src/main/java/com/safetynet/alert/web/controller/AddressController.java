package com.safetynet.alert.web.controller;

import com.safetynet.alert.dao.AddressDao;
import com.safetynet.alert.model.Address;
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
 * AddressController. class that implement
 * request/response logic of Address.
 */
@RestController
@RequestMapping("/address")
public class AddressController {

  private static final Logger log = LoggerFactory.getLogger(AddressController.class);
  private final AddressDao addressDao;

  public AddressController(AddressDao addressDao) {
    this.addressDao = addressDao;
  }

  /**
   * getAddressByStreet. Method that get a list
   * of address.
   *
   * @return address list
   */
  @GetMapping
  public List<Address> getAddress() {
    log.info("Getting address list");
    return addressDao.getAddressList();
  }

  /**
   * saveAddress. Method that save a address.
   *
   * @param address a given address
   * @return address object
   */
  @PostMapping
  public ResponseEntity<Address> saveAddress(@Valid @RequestBody Address address) {
    log.info("Creating address with address {}", address.getStreet());
    Address addressAdded = addressDao.saveAddress(address);

    if (Objects.isNull(addressAdded)) {
      return ResponseEntity.noContent().build();
    }
    log.info("Save address request success. Address saved with address {}",
        address.getStreet());
    return new ResponseEntity<>(addressAdded, HttpStatus.CREATED);
  }

  /**
   * updateAddress. Method that update an address.
   *
   * @param address a given address
   * @return address object
   */
  @PutMapping
  public Address updateAddress(@Valid @RequestBody Address address) {
    log.info("Updating address with address {}", address.getStreet());

    Address updated = addressDao.updateAddress(address);

    log.info("Update address request success. Address updated with address {}",
        address.getStreet());

    return updated;
  }

  /**
   * updateAddress. Method that delete a address.
   *
   * @param address a given address
   */
  @DeleteMapping
  public void deleteAddress(@Valid @RequestBody Address address) {
    log.info("Deleting address {}", address.getStreet());
    addressDao.deleteAddress(address);
    log.info("Delete address request success {}", address.getStreet());
  }
}