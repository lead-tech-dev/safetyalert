package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.AddressDao;
import com.safetynet.alert.dao.PersonDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.utils.ConvertDto;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * PersonDaoImpl. class that implement
 * person business logic
 */
@Service
public class PersonDaoImpl implements PersonDao {
  private final List<Person> personData;

  private final AddressDao addressDao;

  /**
   * Constructor of class PersonDaoImpl.
   *
   * @param addressDao a addressDao
   */
  public PersonDaoImpl(AddressDao addressDao) {

    this.personData = Database.getPersonDataInfo();

    this.addressDao = addressDao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PersonDto> getPersonList() {
    return ConvertDto.convertToPersonDto(personData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PersonDto savePerson(PersonDto personDto) {

    Optional<Address> existAddress = addressDao.getAddressByStreet(personDto.getAddress());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address doesn't exit!");
    }
    personData.add(ConvertDto.convertToPerson(personDto));

    return personDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PersonDto updatePerson(PersonDto personDto) {
    Optional<Person> existPerson =
        getPersonByFirstNameAndLastName(personDto.getFirstName(),
            personDto.getLastName());

    if (existPerson.isEmpty()) {
      throw new NoSuchElementException("Person doesn't exist !");
    }

    Optional<Address> existAddress =
        addressDao.getAddressByStreet(personDto.getAddress());
    if (existAddress.isEmpty()) {
      throw new NoSuchElementException("Address doesn't exit !");
    }

    personData.set(personData.indexOf(existPerson.get()),
        new PersonDto().convertToEntity(personDto));

    return personDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deletePerson(PersonDto personDto) {
    Optional<Person> existPerson =
        getPersonByFirstNameAndLastName(personDto.getFirstName(),
            personDto.getLastName());

    if (existPerson.isEmpty()) {
      throw new NoSuchElementException("Person doesn't exist !");
    }
    System.out.println(new PersonDto().convertToEntity(personDto));
    personData.remove(new PersonDto().convertToEntity(personDto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
    return personData.stream()
        .filter(current -> Objects.equals(current.getFirstName(), firstName)
            &&
            Objects.equals(current.getLastName(), lastName))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Person getPersonByAddress(String address) {
    Person person = null;

    for (Person current : personData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        person = current;
      }
    }
    return person;
  }
}
