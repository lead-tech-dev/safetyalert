package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

@Tag("PersonControllerTest")
@DisplayName("Person controller test logic")
class PersonControllerTest extends AbstractTest {

  @Test
  @DisplayName("getPerson should return list of person")
  void getPerson_ShouldReturnPersonList() throws Exception {
    // GIVEN
    String uri = "/person";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List persons = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(persons.size() > 0);
  }


  @Test
  @DisplayName("savePerson should return added person with 201 Status code")
  void savePerson_ShouldReturnAddedPersonWith201StatusCode_ForSuccessAdded() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("Cartman", "Eric", "112 Steppes Pl", "0758951895",
        "Culver", "97451", "ericmaximan@gmail.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.firstName").value("Cartman"))
        .andExpect(jsonPath("$.lastName").value("Eric"))
        .andReturn();
  }

  @Test
  @DisplayName("savePerson should return 404 status code for not exit address")
  void savePerson_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("John", "Boyd", "7 rue lucien deneau", "0758951895",
        "Mainvilliers", "28300", "jaboyd@email.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updatePerson should return 400 status code for not exist person")
  void updatePerson_ShouldReturn404StatusCode_ForNotExistPerson() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("Eric", "Maximan", "7 rue lucien deneau", "0758951895",
        "Mainvilliers", "28300", "cartmanien@email.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updatePerson should return 404 status code for not exit address")
  void updatePerson_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("John", "Boyd", "12 rue des pommiers", "0758951895",
        "Mainvilliers", "28300", "jaboyd@email.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updatePerson should return updated person with 200 status code")
  void updatePerson_ShouldReturnUpdatedPersonWith200StatusCode_ForSuccessUpdated() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("John", "Boyd", "1509 Culver St", "0758951895",
        "Culver", "97451", "addresschange@gmail.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Boyd"))
        .andReturn();
  }


  @Test
  @DisplayName("deletePerson should return 404 status code")
  void deletePerson_ShouldReturn404StatusCode_ForNotExistPerson() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("Eric", "Man", "7 rue lucien deneau", "0758951895",
        "Mainvilliers", "28300", "cartmanien@email.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("deletePerson should return 200 status code")
  void deletePerson_ShouldReturn200StatusCode_ForSuccessDeleted() throws Exception {
    // GIVEN
    String uri = "/person";
    PersonDto person =   new PersonDto("Lily", "Cooper", "489 Manchester St", "841-874-6512",
        "Culver", "97451", "lily@email.com");
    String inputJson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}