package com.safetynet.alert.dao.impl;

import com.safetynet.alert.dao.MedicalRecordsDao;
import com.safetynet.alert.dao.PersonDao;
import com.safetynet.alert.dao.database.Database;
import com.safetynet.alert.dto.medicalrecords.MedicalRecordsDto;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.web.exception.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicalRecordsDaoImpl implements MedicalRecordsDao {
  private final List<MedicalRecords> medicalRecordsData;

  private final PersonDao personDao;

  public MedicalRecordsDaoImpl(PersonDao personDao) {

    this.medicalRecordsData = Database.getMedicalRecordsData();

    this.personDao = personDao;

  }

  /**
   *
   * {@inheritDoc}
   *
   */
  @Override
  public List<MedicalRecords> getMedicalRecordsList() {
    return medicalRecordsData;
  }

  /**
   *
   * {@inheritDoc}
   *
   */
  @Override
  public MedicalRecords saveMedicalRecords(MedicalRecordsDto medicalRecordsDto) {

   Optional<Person> existPerson =
        personDao.getPersonByFirstNameAndLastName(medicalRecordsDto.getFirstName(),
            medicalRecordsDto.getLastName());

    if (existPerson.isEmpty()) {
      throw new NoSuchElementException("Person doesn't exist !");
    }

    Optional<MedicalRecords> existMedicalRecords =
        getMedicalRecordsByFirstNameAndLastName(medicalRecordsDto.getFirstName(),
            medicalRecordsDto.getLastName());
    if (existMedicalRecords.isPresent()) {
      throw new BadRequestException("Medical record already exist !");
    }

    MedicalRecords medicalRecords = medicalRecordsDto.convertToEntity(medicalRecordsDto);

    medicalRecordsData.add(medicalRecords);

    return medicalRecords;
  }

  /**
   *
   * {@inheritDoc}
   *
   */
  @Override
  public MedicalRecords updateMedicalRecords(MedicalRecordsDto medicalRecordsDto) {
    Optional<MedicalRecords> existMedicalRecords =
        getMedicalRecordsByFirstNameAndLastName(medicalRecordsDto.getFirstName(),
            medicalRecordsDto.getLastName());
    if (existMedicalRecords.isEmpty()) {
      throw new NoSuchElementException("Medical record doesn't exist!");
    }

    medicalRecordsData.set(medicalRecordsData.indexOf(existMedicalRecords.get()),
        new MedicalRecordsDto().convertToEntity(medicalRecordsDto));

    return new MedicalRecordsDto().convertToEntity(medicalRecordsDto);
  }

  /**
   *
   * {@inheritDoc}
   *
   */
  @Override
  public void deleteMedicalRecords(MedicalRecordsDto medicalRecordsDto) {
    Optional<MedicalRecords> existMedicalRecords =
        getMedicalRecordsByFirstNameAndLastName(medicalRecordsDto.getFirstName(),
            medicalRecordsDto.getLastName());
    if (existMedicalRecords.isEmpty()) {
      throw new NoSuchElementException("Medical record doesn't exist !");
    }

    medicalRecordsData.remove(new MedicalRecordsDto().convertToEntity(medicalRecordsDto));

  }

  /**
   *
   * {@inheritDoc}
   *
   */
  public Optional<MedicalRecords> getMedicalRecordsByFirstNameAndLastName(String firstName, String lastName){

    return
        medicalRecordsData.stream()
            .filter(current -> Objects.equals(current.getFirstName(), firstName) &&
                Objects.equals(current.getLastName(), lastName))
            .findFirst();
  }

  /**
   *
   * {@inheritDoc}
   *
   */
  public String getPersonBirthdate (String firstname, String lastname) {
    String birthdate = null;

    for (MedicalRecords current : medicalRecordsData) {
      if (Objects.equals(current.getFirstName(), firstname)
          && Objects.equals(current.getLastName(), lastname)) {
        birthdate = current.getBirthdate();
      }
    }

    return birthdate;
  }
}
