package com.shoppingcart.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private Date createdAt;
    private Date tokenPasswordCreatedAt;
    private String token;
}
