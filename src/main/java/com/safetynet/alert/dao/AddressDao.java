package com.safetynet.alert.dao;

import com.safetynet.alert.model.Address;
import java.util.List;
import java.util.Optional;

/**
 * AddressDao interface structure the business logic
 * of address.
 *
 */
public interface AddressDao {

  /**
   * getAddressList. Method that get a
   * list of address from a data source.
   *
   * @return an address list
   */
  List<Address> getAddressList();

  /**
   * saveAddress. Method that save an
   * address to a data source.
   *
   * @param address an address object
   *
   * @return an address object
   */
  Address saveAddress(Address address);

  /**
   * updateAddress. Method that update an
   * address in a data source.
   *
   * @param address an address object
   *
   * @return an address object
   */
  Address updateAddress(Address address);

  /**
   * deleteAddress. Method that delete an
   * address in a data source.
   *
   * @param address an address object
   */
  void deleteAddress(Address address);

  /**
   * getAddressByStreet. Method that get an
   * address in a data source.
   *
   * @param address an address object
   */
  Optional<Address> getAddressByStreet(String street);

}
