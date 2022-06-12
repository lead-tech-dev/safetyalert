package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.model.Station;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

@Tag("StationControllerTest")
@DisplayName("Station controller test logic")
class StationControllerTest extends AbstractTest {
  private final String uri = "/station";

  @Test
  @DisplayName("getStation should return list of station")
  void getStation_ShouldReturnStationList() throws Exception {
    // GIVEN

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List stations = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(stations.size() > 0);
  }


  @Test
  @DisplayName("saveStation should return added station with 201 Status code")
  void saveStation_ShouldReturnAddedStationWith201StatusCode_ForSuccessAdded() throws Exception {
    // GIVEN
    Station station = new Station("5");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.station").value("5"))
        .andReturn();
  }

  @Test
  @DisplayName("saveStation should return 400 status code for already exist station")
  void saveStation_ShouldReturn40OStatusCode_ForAlreadyExistStation() throws Exception {
    // GIVEN
    Station station = new Station("4");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("updateStation should return 400 status code for not exist station")
  void updateStation_ShouldReturn404StatusCode_ForNotExistStation() throws Exception {
    // GIVEN
    Station station = new Station("7");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updateStation should return updated station with 200 status code")
  void updateStation_ShouldReturnUpdatedStationWith200StatusCode_ForSuccessUpdated() throws Exception {
    // GIVEN
    Station station = new Station("4");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.station").value("4"))
        .andReturn();
  }


  @Test
  @DisplayName("deleteStation should return 404 status code")
  void deleteStation_ShouldReturn404StatusCode_ForNotExistStation() throws Exception {
    // GIVEN
    Station station = new Station("6");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("deleteStation should return 200 status code")
  void deleteStation_ShouldReturn200StatusCode_ForSuccessDeleted() throws Exception {
    // GIVEN
    Station station = new Station("4");
    String inputJson = super.mapToJson(station);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}