package com.loans.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class LoansPagingDTO {
    private Set<LoanDTO> items;
    private PaginationDTO paging;
}
