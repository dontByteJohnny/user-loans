package com.loans.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private Integer page;
    private Integer size;
    private Integer total;

}