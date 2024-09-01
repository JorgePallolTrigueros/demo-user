package com.shoppingcart.user.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * JwtResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class JwtResponse {

  private String jwt;

  public JwtResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public JwtResponse(String jwt) {
    this.jwt = jwt;
  }

  public JwtResponse jwt(String jwt) {
    this.jwt = jwt;
    return this;
  }

  /**
   * Get jwt
   * @return jwt
  */
  @NotNull 
  @Schema(name = "jwt", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVC.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("jwt")
  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JwtResponse jwtResponse = (JwtResponse) o;
    return Objects.equals(this.jwt, jwtResponse.jwt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JwtResponse {\n");
    sb.append("    jwt: ").append(toIndentedString(jwt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

