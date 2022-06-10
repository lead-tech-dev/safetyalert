package com.safetynet.alert.model;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Address class implements a address
 * entity.
 *
 */

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
    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Address)) {
      return false;
    }

    Address other = (Address) obj;

    return Objects.equals(this.street, other.street);
  }

  @Override
  public int hashCode() {
    return street.length();
  }

}
