package org.example.dto;

import lombok.*;
import org.example.model.User;

@Getter
@Setter
@Builder
@ToString
public class UserResponseDto {

    private String message;

    private User user;
}
