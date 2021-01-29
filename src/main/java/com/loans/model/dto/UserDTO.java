package com.loans.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
public class UserDTO extends BaseUserDTO{
    private Set<LoanDTO> loans;

    public UserDTO(Long id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
    }
}