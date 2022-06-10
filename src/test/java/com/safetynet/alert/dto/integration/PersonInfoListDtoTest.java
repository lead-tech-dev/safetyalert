package com.safetynet.alert.dto.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

@Tag("PersonInfoListDtoTest")
@DisplayName("Person info list dto test logic")
class PersonInfoListDtoTest {

  @Test
  @DisplayName("convertToDto should convert given person to PersonInfoListDto")
  void convertToDto_ShouldConvertPersonToPersonInfoListDto_ForGivenPerson() {
    // GIVEN
    Person person = new Person("Cartman", "Eric", "0758951895", "ericmaximan@gmail.com",
        new Address("7 rue lucien deneau", "Mainvilliers", "28300"),
        0, new MedicalRecords());
    PersonInfoListDto expected =   new PersonInfoListDto("Eric",
        0, "ericmaximan@gmail.com", new ArrayList<>(), new ArrayList<>());

    // WHEN
    PersonInfoListDto result = new PersonInfoListDto().convertToDto(person);

    // THEN
    assertThat(result).isEqualTo(expected);
  }
}