package com.safetynet.alert.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@lombok.Generated
public class PersonFireAddressListDto {

  private String lastname;

  private String phone;

  private int age;

  private List<String> medications = new ArrayList<>();

  private List<String> allergies = new ArrayList<>();

}
