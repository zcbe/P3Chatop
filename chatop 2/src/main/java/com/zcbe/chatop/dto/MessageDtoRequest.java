package com.zcbe.chatop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDtoRequest {
    @NotEmpty
    private String message;
    @NotNull
    private Long user_id;
    @NotNull
    private Long rental_id;
}