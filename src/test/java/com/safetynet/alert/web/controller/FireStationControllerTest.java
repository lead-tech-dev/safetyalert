package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

@Tag("FireStationControllerTest")
@DisplayName("FireStation controller test logic")
class FireStationControllerTest extends AbstractTest {

  @Test
  @DisplayName("getFireStation should return list of fireStation")
  void getFireStation_ShouldReturnFireStationList() throws Exception {
    // GIVEN
    String uri = "/firestations";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List fireStations = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(fireStations.size() > 0);
  }


  @Test
  @DisplayName("saveFireStation should return added fireStation with 201 Status code")
  void saveFireStation_ShouldReturnAddedFireStationWith201StatusCode_ForSuccessAdded() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("5");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.station").value("5"))
        .andReturn();
  }

  @Test
  @DisplayName("saveFireStation should return 400 status code for already exist fireStation")
  void saveFireStation_ShouldReturn40OStatusCode_ForAlreadyExistFireStation() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("4");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("updateFireStation should return 400 status code for not exist fireStation")
  void updateFireStation_ShouldReturn404StatusCode_ForNotExistFireStation() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("7");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updateFireStation should return updated fireStation with 200 status code")
  void updateFireStation_ShouldReturnUpdatedFireStationWith200StatusCode_ForSuccessUpdated() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("4");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.station").value("4"))
        .andReturn();
  }


  @Test
  @DisplayName("deleteFireStation should return 404 status code")
  void deleteFireStation_ShouldReturn404StatusCode_ForNotExistFireStation() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("6");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("deleteFireStation should return 200 status code")
  void deleteFireStation_ShouldReturn200StatusCode_ForSuccessDeleted() throws Exception {
    // GIVEN
    String uri = "/firestations";
    FireStation fireStation = new FireStation("1");
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}