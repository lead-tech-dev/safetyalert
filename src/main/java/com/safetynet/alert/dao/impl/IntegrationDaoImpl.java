package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.AddressFireStationDao;
import com.safetynet.alert.dao.IntegrationDao;
import com.safetynet.alert.dao.MedicalRecordsDao;
import com.safetynet.alert.dao.PersonDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dto.integration.ChildListDto;
import com.safetynet.alert.dto.integration.PersonFireAddressListDto;
import com.safetynet.alert.dto.integration.PersonInfoListDto;
import com.safetynet.alert.dto.integration.PersonStationListDto;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.utils.ConvertDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 * IntegrationDaoImpl. class that implement
 *  integration business logic
 */
@Service
public class IntegrationDaoImpl implements IntegrationDao {
  private final PersonDao personDao;

  private final AddressFireStationDao addressFireStationDao;

  private final MedicalRecordsDao medicalRecordsDao;

  private final List<Person> personData;

  private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE);

  /**
   * Constructor of class IntegrationDaoImpl.
   *
   * @param personDao a personDto
   * @param addressFireStationDao a addressFireStationDao
   * @param medicalRecordsDao a medicalRecordsDao
   */
  public IntegrationDaoImpl(PersonDao personDao, AddressFireStationDao addressFireStationDao,
                            MedicalRecordsDao medicalRecordsDao) {
    this.personDao = personDao;
    this.addressFireStationDao = addressFireStationDao;
    this.medicalRecordsDao = medicalRecordsDao;
    this.personData = Database.getPersonDataInfo();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getPersonStationListByAddresses(String stationNumber) {
    Map<String, Object> data = new HashMap<>();
    List<PersonStationListDto> persons = new ArrayList<>();

    List<String> addresses = addressFireStationDao.getAddressListByStation(stationNumber);
    int adultNumber = 0;
    int childNumber = 0;

    for (String address : addresses) {
      for (Person current : personData) {
        if (Objects.equals(current.getAddress().getStreet(), address)) {
          int age = getPersonAge(personDao.getPersonByAddress(address));
          if (age > 18) {
            adultNumber++;
          } else {
            childNumber++;
          }

          persons.add(ConvertDto.convertToPersonStationListDto(
              personDao.getPersonByAddress(address))
          );
        }
      }
    }
    data.put("Data", persons);
    data.put("Adult number", adultNumber);
    data.put("Child number", childNumber);

    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getChildList(String address) {
    Map<String, Object> children = new HashMap<>();
    List<ChildListDto> adultPersons = new ArrayList<>();
    List<ChildListDto> childPersons = new ArrayList<>();

    for (Person current : personData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        int age = getPersonAge(current);
        current.setAge(age);
        if (age > 18) {
          adultPersons.add(ConvertDto.convertToChildListDto(current));
        } else {
          childPersons.add(ConvertDto.convertToChildListDto(current));
        }
      }
    }

    if (childPersons.size() == 0) {
      return children;
    } else {
      children.put("Children at this address", childPersons);
      children.put("Other family members", adultPersons);
    }

    return children;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getPersonPhoneList(String fireStation) {
    List<String> data = new ArrayList<>();
    List<String> addresses = addressFireStationDao.getAddressListByStation(fireStation);

    for (String address : addresses) {
      Person person = personDao.getPersonByAddress(address);
      data.add(person.getPhone());
    }

    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Person> getPersonListByAddress(String address) {
    List<Person> persons = new ArrayList<>();

    for (Person current : personData) {
      if (Objects.equals(current.getAddress().getStreet(), address)) {
        current.setAge(getPersonAge(current));
        persons.add(current);
      }
    }
    return persons;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getPersonFireAddressList(String address) {
    Map<String, Object> data = new HashMap<>();
    List<PersonFireAddressListDto> personFireAddress = new ArrayList<>();
    List<Person> persons = getPersonListByAddress(address);

    for (Person person : persons) {
      personFireAddress.add(ConvertDto.convertToPersonFireAddressListDto(person));
    }

    data.put("Station", addressFireStationDao.getStationListByAddress(address));
    data.put("Residents", personFireAddress);
    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getPersonFloodStationList(String[] stations) {
    Map<String, Object> data = new HashMap<>();
    List<String> addresses = new ArrayList<>();

    for (String station : stations) {
      addresses.addAll(addressFireStationDao.getAddressListByStation(station));
    }

    for (String address : addresses) {
      data.put(address, ConvertDto.convertToFloodStationListDto(
          getPersonListByAddress(address))
      );
    }

    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PersonInfoListDto> getPersonInfoList(String firstname, String lastname) {

    List<PersonInfoListDto> personPhoneListDto = new ArrayList<>();

    for (Person current : personData) {
      if (Objects.equals(current.getFirstName(), firstname)
          &&
          Objects.equals(current.getLastName(), lastname)) {
        current.setAge(getPersonAge(current));
        personPhoneListDto.add(ConvertDto.convertToPersonInfoListDto(current));
      }

    }
    return personPhoneListDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getCommunityEmailList(String city) {
    return personData
        .stream()
        .filter(person -> Objects.equals(person.getAddress().getCity(), city))
        .map(Person::getEmail)
        .distinct()
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  private int getPersonAge(Person person) {
    int age = 0;
    try {
      Date personAge =
          formatter.parse(medicalRecordsDao.getPersonBirthdate(person.getFirstName(),
              person.getLastName()));

      Date curDate = Date.from(Instant.now());

      age = curDate.getYear() - personAge.getYear();

    } catch (ParseException parseException) {
      System.out.println("error");
    }

    return age;
  }

}
