package com.loans.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO extends BaseUserDTO{
    private Set<LoanDTO> loans;

    public UserDTO(Long id, String email, String firstName, String lastName, Set<LoanDTO> loanSet) {
        super(id, email, firstName, lastName);
        loans = new HashSet<>(loanSet);
    }
}