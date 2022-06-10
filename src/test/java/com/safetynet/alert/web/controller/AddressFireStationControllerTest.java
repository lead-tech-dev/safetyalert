package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.dto.AddressFirestation.AddressFireStationDto;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

@Tag("AddressFireStationControllerTest")
@DisplayName("AddressFireStation controller test logic")
class AddressFireStationControllerTest extends AbstractTest {

  @Test
  @DisplayName("getAddressFireStation should return list of addressFireStation")
  void getAddressFireStation_ShouldReturnAddressFireStationList() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List addressFireStations = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(addressFireStations.size() > 0);
  }

  @Test
  @DisplayName("saveAddressFireStation should return  404 Status code for not exit address")
  void saveAddressFireStation_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
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
  @DisplayName("saveAddressFireStation should return  404 Status code for not exit fireStation")
  void saveAddressFireStation_ShouldReturn404StatusCode_ForNotExistFireStation() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "748 Townings Dr",
        "6"
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
  @DisplayName("saveAddressFireStation should return  404 Status code for already exist mapping")
  void saveAddressFireStation_ShouldReturn400StatusCode_ForAlreadyExistMapping() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "947 E. Rose Dr",
        "1"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("saveAddressFireStation should return added addressFireStation with 201 Status code")
  void saveAddressFireStation_ShouldReturnAddedAddressFireStationWith201StatusCode_ForSuccessAdded()
      throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "112 Steppes Pl",
        "1"
    );
    String inputJson = super.mapToJson(addressFireStation);

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
  @DisplayName("updateAddressFireStation should return 404 status code for not exit " +
      "mapping address/station")
  void updateAddressFireStation_ShouldReturn404StatusCode_ForNotExistMappingAddressStation()
      throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
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
  @DisplayName("updateAddressFireStation should return 404 status code for not exit " +
      "station")
  void updateAddressFireStation_ShouldReturn404StatusCode_ForNotExistStation() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "748 Townings Dr",
        "7"
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
  @DisplayName("updateAddressFireStation should return updated addressFireStation with 200 status" +
      " code")
  void updateAddressFireStation_ShouldReturnUpdatedAddressFireStationWith200StatusCode_ForSuccessUpdated()
      throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "644 Gershwin Cir",
        "1"
    );
    String inputJson = super.mapToJson(addressFireStation);

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
  @DisplayName("deleteAddressFireStation should return 404 status code")
  void deleteAddressFireStation_ShouldReturn404StatusCode_ForNotExistAddressAndFireStation()
      throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "748 Townings ",
        "9"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }


  @Test
  @DisplayName("deleteAddressFireStation should return 200 status code for exist address")
  void deleteAddressFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistAddress() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "489 Manchester St",
        "9"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

  @Test
  @DisplayName("deleteAddressFireStation should return 200 status code for exist station")
  void deleteAddressFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistStation() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "748 Townings",
        "2"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

  @Test
  @DisplayName("deleteAddressFireStation should return 200 status code for exist address " +
      "and station")
  void deleteAddressFireStation_ShouldReturn200StatusCode_ForSuccessDeletedForExistAddressAndStation() throws Exception {
    // GIVEN
    String uri = "/addressfirestations";
    AddressFireStationDto addressFireStation = new AddressFireStationDto(
        "908 73rd St",
        "3"
    );
    String inputJson = super.mapToJson(addressFireStation);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }

}
