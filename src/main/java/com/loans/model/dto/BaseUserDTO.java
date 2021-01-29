package com.loans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class BaseUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public BaseUserDTO(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}