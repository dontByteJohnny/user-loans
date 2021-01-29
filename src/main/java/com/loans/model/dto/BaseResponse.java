package com.loans.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private String message;
}