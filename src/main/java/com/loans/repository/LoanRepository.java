package com.loans.repository;

import com.loans.model.entity.LoanEntity;
import com.loans.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<LoanEntity, Long> {
}