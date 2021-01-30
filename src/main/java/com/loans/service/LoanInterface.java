package com.loans.service;

import com.loans.exception.ApiException;
import com.loans.model.dto.LoanDTO;
import com.loans.model.dto.LoansPagingDTO;

public interface LoanInterface {
    void postLoan(LoanDTO loanDTO) throws ApiException;
    LoansPagingDTO getLoans(Integer page, Integer size, Long userId) throws ApiException;
    void deleteLoan(Long loanId) throws ApiException;
}