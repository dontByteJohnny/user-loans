package com.loans.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LoansPagingDTO {
    private Set<LoanDTO> items;
    private PaginationDTO paging;

    public LoansPagingDTO(Integer size, Integer page, Long count) {
        this.paging = new PaginationDTO(size, page, count);
        this.items = new HashSet<>();
    }
}
