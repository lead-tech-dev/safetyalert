package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.AddressDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.web.exception.BadRequestException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * AddressDaoImpl. class that implement
 *  address business logic
 */
@Service
public class AddressDaoImpl implements AddressDao {
  private final List<Address> addressData;

  public AddressDaoImpl() {
    this.addressData = Database.getAddressData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Address> getAddressList() {
    return addressData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Address saveAddress(Address address) {
    Optional<Address> existAddress = getAddressByStreet(address.getStreet());
    if (existAddress.isPresent()) {
      throw new BadRequestException("Address already exit!");
    }

    addressData.add(address);

    return address;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Address updateAddress(Address address) {
    Optional<Address> existAddress = getAddressByStreet(address.getStreet());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address  doesn't exist !");
    }

    addressData.set(addressData.indexOf(existAddress.get()), address);

    return address;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAddress(Address address) {
    Optional<Address> existAddress = getAddressByStreet(address.getStreet());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address  doesn't exist !");
    }

    addressData.remove(address);
  }

  @Override
  public Optional<Address> getAddressByStreet(String street) {
    return addressData.stream()
        .filter(current -> Objects.equals(current.getStreet(), street))
        .findFirst();
  }

}
