package com.safetynet.alert.dao.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.AddressFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The Database class define methods
 * to access get data from json file.
 *
 */
public class Database {
  private static List<Person> personData = null;
  private static List<MedicalRecords> medicalRecordsData = null;
  private static List<Address> addressData = null;

  private static List<FireStation> fireStationData = null;

  private static List<AddressFireStation> addressFireStationData = null;

  /**
   * Use to get data by entity.
   *
   * @param entity an entity
   *
   * @return JsonNode
   */
  private static JsonNode getJsonData (String entity) {
    JsonNode records = null;

    try  {
      FileInputStream inputStream = new FileInputStream("src/main/resources/data.json");
      ObjectMapper mapper = new ObjectMapper();
      JsonNode readJsonData  = mapper.readTree(inputStream);
      records = readJsonData.at("/" + entity);
    } catch (IOException e) {
      System.out.println("Unable to fetch persons: " + e.getMessage());
    }

    return records;
  }

  /**
   * Use to get persons data.
   *
   * @return Person List
   */
  public static List<Person> getPersonDataInfo() {

    if (personData == null) {

      personData = new ArrayList<>();
      JsonNode personRecords = getJsonData("persons");

      for (int i = 0; i < personRecords.size(); i++) {

        JsonNode firstName = personRecords.get(i).at("/firstName");
        JsonNode lastName = personRecords.get(i).at("/lastName");
        JsonNode phone = personRecords.get(i).at("/phone");
        JsonNode email = personRecords.get(i).at("/email");
        JsonNode street = personRecords.get(i).at("/address");
        JsonNode city = personRecords.get(i).at("/city");
        JsonNode zip = personRecords.get(i).at("/zip");
        MedicalRecords medicalRecords = getMedicalRecordByFirstNameAndLastName(
            getMedicalRecordsData(), firstName.asText(), lastName.asText()
        );

        personData.add(
            new Person(firstName.asText(), lastName.asText(), phone.asText(), email.asText(),
                new Address(
                    street.asText(), city.asText(), zip.asText()), 0,
                medicalRecords
            ));
      }
    }
    return personData;
  }

  /**
   * Use to get medicalRecords data.
   *
   * @return MedicalRecords List
   */
  public static List<MedicalRecords> getMedicalRecordsData() {

    if (medicalRecordsData == null) {
      medicalRecordsData = new ArrayList<>();

      JsonNode medicalRecords = getJsonData("medicalrecords");

      for (JsonNode data: medicalRecords) {
        List<String> medicationSet = new ArrayList<>();
        List<String> allergySet = new ArrayList<>();
        JsonNode firstName = data.at("/firstName");
        JsonNode lastName = data.at("/lastName");
        JsonNode birthdate = data.at("/birthdate");
        JsonNode medications = data.at("/medications");
        if (medications.isArray()) {
          medications.forEach(current -> medicationSet.add(current.asText()));
        }
        JsonNode allergies = data.at("/allergies");
        if (allergies.isArray()) {
          allergies.forEach(current -> allergySet.add(current.asText()));
        }

        medicalRecordsData.add(new MedicalRecords(firstName.asText(), lastName.asText(),
            birthdate.asText(),
            allergySet, medicationSet));
      }

    }
    return medicalRecordsData;
  }

  /**
   * Use to get address data.
   *
   * @return Address List
   */
  public static List<Address> getAddressData() {
    Set<Address> addresses = new HashSet<>();

    List<Person> personRecords = getPersonDataInfo();

    for (Person personRecord : personRecords) {
      addresses.add(personRecord.getAddress());
    }

    if (addressData == null) {
      addressData = new ArrayList<>(addresses);

    }
    return addressData;
  }

  /**
   * Use to get FireStation data.
   *
   * @return FireStation List
   */
  public static List<FireStation> getFireStationData() {
    JsonNode fireStationRecords = getJsonData("firestations");
    Set<FireStation> fireStations = new HashSet<>();

    for (int i = 0; i < fireStationRecords.size(); i++) {
      fireStations.add(new FireStation(fireStationRecords.get(i).at("/station").asText()));
    }

    if (fireStationData == null) {
      fireStationData = new ArrayList<>(fireStations);
    }

    return fireStationData;
  }


  /**
   * Use to get mapping address/station data.
   *
   * @return mapping address/station List
   */
  public static List<AddressFireStation> getAddressFireStationData() {
    JsonNode addressFireStationRecords = getJsonData("firestations");

    if (addressFireStationData == null) {
      addressFireStationData = new ArrayList<>();

      for (int i = 0; i < addressFireStationRecords.size(); i++) {
        addressFireStationData.add(new AddressFireStation(
            getAddressByStreet(getPersonDataInfo(), addressFireStationRecords.get(i).at("/address").asText()),
            new FireStation(addressFireStationRecords.get(i).at("/station").asText())
        ));
      }
    }

    return addressFireStationData;
  }

  /**
   * Use to get medicalRecords data by firstname
   * and lastname.
   *
   * @param medicalRecords a medicalRecords list
   * @param firstname a person firstname
   * @param lastname a person lastname
   *
   * @return MedicalRecords
   */
  private static MedicalRecords getMedicalRecordByFirstNameAndLastName (List<MedicalRecords> medicalRecords, String firstname, String lastname){
    MedicalRecords medicalRecord = null;
    for (MedicalRecords record : medicalRecords) {
      if (Objects.equals(record.getFirstName(), firstname) &&
          Objects.equals(record.getLastName(), lastname)) {
        medicalRecord = record;
      }
    }

    return medicalRecord;
  }

  /**
   * Use to get address data.
   *
   * @param street an address street
   * @param persons a person list
   *
   * @return Address
   */
  private static Address getAddressByStreet(List<Person> persons, String street) {
    Address address = null;

    for (Person person : persons) {
      if (Objects.equals(person.getAddress().getStreet(), street)) {
        address = person.getAddress();
      }
    }

    return address;
  }
}
