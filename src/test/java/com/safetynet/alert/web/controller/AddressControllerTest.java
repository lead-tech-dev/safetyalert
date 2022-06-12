package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.alert.model.Address;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;


@Tag("AddressControllerTest")
@DisplayName("Address controller test logic")
class AddressControllerTest extends  AbstractTest {
  private final String uri = "/address";
  @Test
  @DisplayName("getAddress should return list of address")
  void getAddress_ShouldReturnAddressList() throws Exception {
    // GIVEN

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List addresses = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(addresses.size() > 0);
  }

  @Test
  @DisplayName("saveAddress should return added address with 201 Status code")
  void saveAddress_ShouldReturnAddedAddressWith201StatusCode_ForSuccessAdded() throws Exception {
    // GIVEN
    Address address = new Address("22 rue des oliviers", "Mainvilliers", "28300");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
        mvc.perform(MockMvcRequestBuilders.post(uri)
              .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
              .andExpect(status().is(201))
              .andExpect(jsonPath("$.street").value("22 rue des oliviers"))
              .andExpect(jsonPath("$.city").value("Mainvilliers"))
              .andExpect(jsonPath("$.zip").value("28300"))
              .andReturn();
  }

  @Test
  @DisplayName("saveAddress should return 400 Status code for already exist address")
  void saveAddress_ShouldReturnStatusCode_ForAlreadyExist() throws Exception {
    // GIVEN
    Address address = new Address("1509 Culver St", "Culver", "97451");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(400))
        .andReturn();
  }

  @Test
  @DisplayName("updateAddress should return 404 status code for not exit address")
  void updateAddress_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    Address address = new Address("7 rue lucien deneau", "Mainvilliers", "28300");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("updateAddress should return updated address with 200 status code")
  void updateAddress_ShouldReturnUpdatedAddressWith200StatusCode_ForSuccessUpdated() throws Exception {
    // GIVEN
    Address address = new Address("1509 Culver St", "Mainvilliers", "28300");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.street").value("1509 Culver St"))
        .andExpect(jsonPath("$.city").value("Mainvilliers"))
        .andExpect(jsonPath("$.zip").value("28300"))
        .andReturn();
  }

  @Test
  @DisplayName("deleteAddress should return 404 status code")
  void deleteAddress_ShouldReturn404StatusCode_ForNotExistAddress() throws Exception {
    // GIVEN
    Address address = new Address("1509 Culver", "Culver", "97451");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(404))
        .andReturn();
  }

  @Test
  @DisplayName("deleteAddress should return 200 status code")
  void deleteAddress_ShouldReturn200StatusCode_ForSuccessDeleted() throws Exception {
    // GIVEN
    Address address = new Address("748 Townings Dr", "Culver", "97451");
    String inputJson = super.mapToJson(address);

    // WHEN
    // THEN
    mvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
        .andExpect(status().is(200))
        .andReturn();
  }
}