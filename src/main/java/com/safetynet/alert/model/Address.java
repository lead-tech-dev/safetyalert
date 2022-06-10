package com.safetynet.alert.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.Generated
public class Address {

  @NotBlank
  private String street;

  private String city;

  private String zip;

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;

    if( ! (obj instanceof Address) ) return false;

    Address other = (Address) obj;

    return Objects.equals(this.street, other.street);
  }

  @Override
  public int hashCode() {
    return street.length();
  }

}
