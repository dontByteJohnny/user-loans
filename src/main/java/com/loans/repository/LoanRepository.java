package com.loans.repository;

import com.loans.model.entity.LoanEntity;
import com.loans.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<LoanEntity, Long> {
    Integer deleteByUserId(UserEntity userId);
    List<LoanEntity> findByUserId(UserEntity userId);

}