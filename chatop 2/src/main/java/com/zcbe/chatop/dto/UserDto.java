package com.zcbe.chatop.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;

}