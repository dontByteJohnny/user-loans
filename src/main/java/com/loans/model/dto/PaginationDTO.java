package com.loans.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private Integer page;
    private Integer size;
    private Long total;

    public PaginationDTO(Integer size, Integer page, Long total) {
        this.size = size;
        this.page = page;
        this.total = total;
    }

}