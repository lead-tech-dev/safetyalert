package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.dto.firestation.FireStationDto;
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
  private final String uri = "/firestations";

  @Test
  @DisplayName("getFireStation should return list of fireStation")
  void getFireStation_ShouldReturnFireStationList() throws Exception {
    // GIVEN

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
  @DisplayName("saveFireStation should return  404 Status code for not exit address")
  void saveFireStation_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    FireStationDto addressFireStation = new FireStationDto(
        "12 rue des oliviers",
        "1"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("saveFireStation should return  404 Status code for not exit station")
  void saveFireStation_ShouldReturn404StatusCode_ForNotExistStation() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "748 Townings Dr",
        "6"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("saveFireStation should return  404 Status code for already exist mapping")
  void saveFireStation_ShouldReturn400StatusCode_ForAlreadyExistMapping() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "947 E. Rose Dr",
        "1"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("saveFireStation should return added addressFireStation with 201 Status code")
  void saveFireStation_ShouldReturnAddedFireStationWith201StatusCode_ForSuccessAdded()
      throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "112 Steppes Pl",
        "1"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.address").value("112 Steppes Pl"))
        .andExpect(jsonPath("$.station").value("1"))
        .andReturn();
  }


  @Test
  @DisplayName("updateFireStation should return 404 status code for not exit " +
      "mapping address/station")
  void updateFireStation_ShouldReturn404StatusCode_ForNotExistMappingAddressStation()
      throws Exception {
    // GIVEN
    FireStationDto addressFireStation = new FireStationDto(
        "748 Townings ",
        "4"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updateFireStation should return 404 status code for not exit " +
      "station")
  void updateFireStation_ShouldReturn404StatusCode_ForNotExistStation() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "748 Townings Dr",
        "7"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("updateFireStation should return updated fireStation with 200 status" +
      " code")
  void updateFireStation_ShouldReturnUpdatedFireStationWith200StatusCode_ForSuccessUpdated()
      throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "644 Gershwin Cir",
        "1"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.address").value("644 Gershwin Cir"))
        .andExpect(jsonPath("$.station").value("1"))
        .andReturn();
  }

  @Test
  @DisplayName("deleteFireStation should return 404 status code")
  void deleteFireStation_ShouldReturn404StatusCode_ForNotExistAddressAndFireStation()
      throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "748 Townings ",
        "9"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("deleteFireStation should return 200 status code for exist address")
  void deleteFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistAddress() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "489 Manchester St",
        "9"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

  @Test
  @DisplayName("deleteFireStation should return 200 status code for exist station")
  void deleteFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistStation() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "748 Townings",
        "2"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

  @Test
  @DisplayName("deleteFireStation should return 200 status code for exist address " +
      "and station")
  void deleteFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistAddressAndStation() throws Exception {
    // GIVEN
    FireStationDto fireStation = new FireStationDto(
        "908 73rd St",
        "3"
    );
    String inputJson = super.mapToJson(fireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}
