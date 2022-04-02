package com.realestate.springbootrealestate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for messages to the user
 * (for example: "User name already in use")
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
