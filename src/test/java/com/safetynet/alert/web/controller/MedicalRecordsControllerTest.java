package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.dto.person.PersonDto;
import com.safetynet.alert.model.MedicalRecords;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;
import java.util.List;


@Tag("MedicalRecordsControllerTest")
@DisplayName("MedicalRecords controller test logic")
class MedicalRecordsControllerTest extends AbstractTest {

  @Test
  @DisplayName("getMedicalRecords should return list of medicalRecords")
  void getMedicalRecords_ShouldReturnMedicalRecords() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List medicalRecords = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(medicalRecords.size() > 0);
  }

  @Test
  @DisplayName("saveMedicalRecords should return added medicalRecords with 201 Status code")
  void saveMedicalRecords_ShouldReturnAddedMedicalRecordsWith201StatusCode_ForSuccessAdded() throws Exception {
    // GIVEN
    PersonDto person =   new PersonDto("Cartman", "Eric", "112 Steppes Pl", "0758951895",
        "Culver", "97451", "ericmaximan@gmail.com");
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Cartman",
        "Eric", "23/02/1981", List.of("tetramine:280"), List.of("gluten"));
    String inputJson = super.mapToJson(medicalRecords);
    String inputJsonPerson = super.mapToJson(person);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post("/person")
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJsonPerson));
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.firstName").value("Cartman"))
        .andExpect(jsonPath("$.lastName").value("Eric"))
        .andReturn();
  }

  @Test
  @DisplayName("saveMedicalRecords should return 404 status code for person not exist")
  void saveMedicalRecords_ShouldReturn404StatusCode_ForPersonNotExist() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Charly",
        "Chaplin", "08/06/1945", List.of("tradoxidine:400mg"), Collections.emptyList());
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("saveMedicalRecords should return 400 status code for already exist medicalRecords")
  void saveMedicalRecords_ShouldReturn40OStatusCode_ForAlreadyExistMedicalRecords() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Eric",
        "Cadigan", "08/06/1945", List.of("tradoxidine:400mg"), Collections.emptyList());
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("updateMedicalRecords should return 400 status code for not exist medicalRecords")
  void updateMedicalRecords_ShouldReturn404StatusCode_ForNotExistMedicalRecords() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Charly",
        "Chaplin", "08/06/1945", List.of("tradoxidine:400mg"), Collections.emptyList());
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("updateMedicalRecords should return updated medicalRecords with 200 status code")
  void updateMedicalRecords_ShouldReturnUpdatedMedicalRecordsWith200StatusCode_ForSuccessUpdated() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Eric",
        "Cadigan", "08/06/1945", List.of("alpine:800mg"), List.of("gluten"));
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.firstName").value("Eric"))
        .andExpect(jsonPath("$.lastName").value("Cadigan"))
        .andReturn();
  }


  @Test
  @DisplayName("deleteMedicalRecords should return 404 status code")
  void deleteMedicalRecords_ShouldReturn404StatusCode_ForNotExistMedicalRecords() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Charly",
        "Chaplin", "08/06/1945", List.of("tradoxidine:400mg"), Collections.emptyList());
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("deleteMedicalRecords should return 200 status code")
  void deleteMedicalRecords_ShouldReturn200StatusCode_ForSuccessDeleted() throws Exception {
    // GIVEN
    String uri = "/medicalRecord";
    MedicalRecords medicalRecords = new MedicalRecords("Eric",
        "Cadigan", "08/06/1945", List.of("alpine:800mg"), List.of("gluten"));
    String inputJson = super.mapToJson(medicalRecords);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}