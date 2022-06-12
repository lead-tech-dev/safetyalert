package com.safetynet.alert.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.safetynet.alert.web.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import java.util.Map;

@Tag("IntegrationControllerTest")
@DisplayName("Integration controller  test logic")
class IntegrationControllerTest extends AbstractTest {

  @Test
  @DisplayName("getPersonStationList should return 200 status code for success people list " +
      "covered by the fire station")
  void getPersonStationList_ShouldReturn200StatusCode_ForSuccessGettingListPeopleCoveredByStation()
      throws Exception {
    // GIVEN
    String uri = "/firestation?stationNumber=2";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    Map data = super.mapFromJson(content, Map.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getChildList should return 200 status code for list children living at this " +
      "address")
  void getChildList_ShouldReturn200StatusCode_ForListChildrenLivingAt() throws Exception {
    // GIVEN
    String uri = "/childAlert?address=1509 Culver St";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    Map data = super.mapFromJson(content, Map.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getChildList should return 200 status code for empty list children living at this" +
      " address")
  void getChildList_ShouldReturn200StatusCode_ForEmptyListChildrenLivingAt() throws Exception {
    // GIVEN
    String uri = "/childAlert?address=644 Gershwin Cir";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    Map data = super.mapFromJson(content, Map.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertFalse(data.size() > 0);
  }

  @Test
  @DisplayName("getPhoneAlertList should return 200 status code for list phone number")
  void getPhoneAlertList_ShouldReturn200StatusCode_ForPhoneNumberList() throws Exception {
    // GIVEN
    String uri = "/phoneAlert?fireStation=1";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List data = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getPhoneAlertList should return 200 status code for map of station and residents")
  void getPersonFireAddressList_ShouldReturn200StatusCodeF_ForMapStationResident()
      throws Exception {
    // GIVEN
    String uri = "/fire?address=1509 Culver St";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    Map data = super.mapFromJson(content, Map.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getPersonFloodStationList should return 200 status code for map of address and person list")
  void getPersonFloodStationList_ShouldReturn200StatusCode_ForMapAddressPersonList()
      throws Exception {
    // GIVEN
    String uri = "/flood/stations?stations=4,3";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    Map data = super.mapFromJson(content, Map.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getPersonInfoList should return 200 status code with person List with " +
      "medicalRecords")
  void getPersonInfoList_ShouldReturn200StatusCode_ForPersonListWithMedicalRecords()
      throws Exception {
    // GIVEN
    String uri = "/personInfo?firstName=Eric&lastName=Cadigan";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List data = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getPersonInfoList should return 200 status code for empty person List with " +
      "medicalRecords")
  void getPersonInfoList_ShouldReturn200StatusCode_ForEmptyPersonListWithMedicalRecords()
      throws Exception {
    // GIVEN
    String uri = "/personInfo?firstName=John&lastName=Cartman";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List data = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertFalse(data.size() > 0);
  }

  @Test
  @DisplayName("getCommunityEmailList should return 200 status code for community email List")
  void getCommunityEmailList_ShouldReturn200StatusCode_ForCommunityEmailList() throws Exception {
    // GIVEN
    String uri = "/communityEmail?city=Culver";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List data = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertTrue(data.size() > 0);
  }

  @Test
  @DisplayName("getCommunityEmailList should return 200 status code for empty community email List")
  void getCommunityEmailList_ShouldReturn200StatusCode_ForEmptyCommunityEmailList() throws Exception {
    // GIVEN
    String uri = "/communityEmail?city=Mainvilliers";

    // WHEN
    MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    List data = super.mapFromJson(content, List.class);

    // THEN
    assertEquals(200, mvcResult.getResponse().getStatus());
    assertFalse(data.size() > 0);
  }

}