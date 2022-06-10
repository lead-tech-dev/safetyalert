package com.safetynet.alert.dao;

import com.safetynet.alert.dto.medicalrecords.MedicalRecordsDto;
import com.safetynet.alert.model.MedicalRecords;
import java.util.List;
import java.util.Optional;

/**
 * MedicalRecordsDao interface structure the business logic
 * of medicalRecords.
 *
 */
public interface MedicalRecordsDao {

   /**
    * getMedicalRecordsList. Method that get a
    * list of medicalRecords from a data source.
    *
    * @return a MedicalRecords list
    */
   List<MedicalRecords> getMedicalRecordsList();

   /**
    * saveMedicalRecords. Method that save a
    * MedicalRecords to a data source.
    *
    * @param medicalRecordsDto an MedicalRecords object
    *
    * @return an MedicalRecords object
    */
   MedicalRecords saveMedicalRecords(MedicalRecordsDto medicalRecordsDto);

   /**
    * updateMedicalRecords. Method that update an
    * MedicalRecords in a data source.
    *
    * @param medicalRecordsDto an MedicalRecords object
    *
    * @return a MedicalRecords object
    */
   MedicalRecords updateMedicalRecords(MedicalRecordsDto medicalRecordsDto);

   /**
    * deleteMedicalRecords. Method that delete an
    * MedicalRecords in a data source.
    *
    * @param medicalRecordsDto an MedicalRecords object
    */
   void deleteMedicalRecords(MedicalRecordsDto medicalRecordsDto);

   /**
    * getMedicalRecordsByFirstNameAndLastName. Method that get or not an
    * MedicalRecords from a data source.
    *
    * @param firstName a person firstname
    * @param lastName a person lastname
    *
    * @return an optional MedicalRecords object
    */
   Optional<MedicalRecords> getMedicalRecordsByFirstNameAndLastName(String firstName,
                                                                            String lastName);

   /**
    * getPersonBirthdate. Method that get a
    * person birthdate from a data source.
    *
    * @param firstname a person firstname
    * @param lastname a person firstname
    *
    * @return a String
    */
   String getPersonBirthdate (String firstname, String lastname);

}
