package com.safetynet.alert.dao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dao.impl.AddressDaoImpl;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.web.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@ExtendWith(MockitoExtension.class)
@Tag("AddressDaoImplTest")
@DisplayName("address data access object")
class AddressDaoImplTest {

  private static MockedStatic<Database> database;

  private static AddressDaoImpl addressDaoImpl;

  @BeforeEach
  private void setUp() {
    List<Address> addresses = new ArrayList<>();
    addresses.add(new Address("7 rue lucien deneau",
        "Mainvilliers", "28300"));
    database = mockStatic(Database.class);
    when(Database.getAddressData()).thenReturn(addresses);

    addressDaoImpl = new AddressDaoImpl();
  }

  @AfterEach
  private void tearDown() {
    database.close();
  }

  @Test
  @DisplayName("getAddressList should return address list")
  void getAddressList_ShouldReturnAddressList() {
    // Given
    Address expected = new Address("7 rue lucien deneau", "Mainvilliers", "28300");

    // WHEN
    List<Address> result = addressDaoImpl.getAddressList();

    // THEN
    assertThat(result).containsExactly(expected);

  }

  @Test
  @DisplayName("saveAddress should throw bad request exception")
  void saveAddress_ShouldThrowBadRequestException_forExistAddress() {
    // GIVEN
    Address given = new Address("7 rue lucien deneau", "Mainvilliers", "28300");

    // WHEN
    var exception = assertThrows(BadRequestException.class,
        () -> addressDaoImpl.saveAddress(given));

    // THEN
    assertThat(exception.getMessage()).isEqualTo("Address already exit!");
  }

  @Test
  @DisplayName("saveAddress should add one address")
  void saveAddress_ShouldAddOne_forGivenAddress() {
    // GIVEN
    Address address = new Address("16 rue lucien deneau", "Mainvilliers", "28300");

    // WHEN
    addressDaoImpl.saveAddress(address);

    // THEN
    assertThat(addressDaoImpl.getAddressList()).contains(address);
  }

  @Test
  @DisplayName("saveAddress should return given address")
  void saveAddress_ShouldReturnAddedAddress_forGivenAddress() {
    // GIVEN
    Address address = new Address("16 rue lucien deneau", "Mainvilliers", "28300");

    // WHEN
    Address result = addressDaoImpl.saveAddress(address);

    // THEN
    assertThat(result).isEqualTo(address);
  }

  @Test
  @DisplayName("updateAddress should throw no such element exception")
  void updateAddress_ShouldThrowNoSuchElementException_forNotExistAddress() {
    // GIVEN
    Address address = new Address("14 rue anatol France", "Chartres", "28300");

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> addressDaoImpl.updateAddress(address));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Address  doesn't exist !");
  }

  @Test
  @DisplayName("updateAddress should update one address")
  void updateAddress_ShouldUpdateOne_forGivenAddress() {
    // GIVEN
    Address address = new Address("7 rue lucien deneau", "Chartres", "28000");

    // WHEN
    addressDaoImpl.updateAddress(address);

    // THEN
    assertThat(addressDaoImpl.getAddressList()).contains(address);
  }

  @Test
  @DisplayName("updateAddress should return given address")
  void updateAddress_ShouldReturnGivenAddress_forGivenAddress() {
    // GIVEN
    Address address = new Address("7 rue lucien deneau", "Chartres", "28000");

    // WHEN
    Address result = addressDaoImpl.updateAddress(address);

    // THEN
    assertThat(result).isEqualTo(address);
  }

  @Test
  @DisplayName("deleteAddress should throw no such element exception")
  void deleteAddress_ShouldThrowNoSuchElementException_forNotExistAddress() {
    // GIVEN
    Address address = new Address("14 rue anatol France", "Chartres", "28300");

    // WHEN
    var ex = assertThrows(NoSuchElementException.class,
        () -> addressDaoImpl.deleteAddress(address));

    // THEN
    assertThat(ex.getMessage()).isEqualTo("Address  doesn't exist !");
  }

  @Test
  @DisplayName("deleteAddress should delete one address")
  void deleteAddress_ShouldDeleteOne_forGivenAddress() {
    // GIVEN
    Address address = new Address("7 rue lucien deneau", "Chartres", "28000");

    // WHEN
    addressDaoImpl.deleteAddress(address);

    // THEN
    assertThat(addressDaoImpl.getAddressList()).doesNotContain(address);
  }

}