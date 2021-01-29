package com.loans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class LoanDTO {
    private Long id;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=999999999, fraction=2)
    private BigDecimal total;
    private Long userId;

}