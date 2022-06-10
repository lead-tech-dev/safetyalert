package com.safetynet.alert.dto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("PersonDtoTest")
@DisplayName("Person dto test logic")
class PersonDtoTest {

  @Test
  @DisplayName("convertToEntity should convert given personDto to person")
  void convertToEntity_ShouldConvertPersonDtoToPerson_ForGivenPersonDto() {
    // GIVEN
    PersonDto personDto = new PersonDto("Cartman", "Eric", "7 rue lucien deneau", "0758951895",
        "Mainvilliers", "28300", "ericmaximan@gmail.com");
    Person expected = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());

    // WHEN
    Person result = new PersonDto().convertToEntity(personDto);

    // THEN
    assertThat(result).isEqualTo(expected);
  }

  @Test
  @DisplayName("convertToDto should convert given person to personDto")
  void convertToDto_ShouldConvertPersonToPersonDto_ForGivenPerson() {
    // GIVEN
    Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    PersonDto expected = new PersonDto("Cartman", "Eric", "7 rue lucien deneau", "0758951895",
        "Mainvilliers", "28300", "ericmaximan@gmail.com");

    // WHEN
    PersonDto result = new PersonDto().convertToDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }
}