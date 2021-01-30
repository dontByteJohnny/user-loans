package com.loans.controller;

import com.loans.exception.ApiException;
import com.loans.model.dto.*;
import com.loans.service.UserLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/loans")
@Api(tags = "Loans account API")
@Validated
public class LoanController {
    @Autowired
    private UserLoanService userLoanService;

    private Logger logger = LoggerFactory.getLogger(LoanController.class);

    @ApiOperation(value = "Post Loans")
    @PostMapping()
    public ResponseEntity<BaseResponse> postUser(@RequestBody @Valid LoanDTO loanDTO) throws ApiException {
        logger.info("Creating loan for userId: " + loanDTO.getUserId());
        userLoanService.postLoan(loanDTO);
        logger.info("Loan created successfully for userId: " + loanDTO.getUserId());
        return new ResponseEntity<>(BaseResponse.builder().message("Loan created").build(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Users")
    @GetMapping()
    public ResponseEntity<LoansPagingDTO> getLoans(
            @Param(value = "page") @Valid @NotNull(message = "page is mandatory") @Min(message = "page must be at least 1", value = 1) Integer page,
            @Param(value = "size") @Valid @NotNull(message = "size is mandatory") @Min(message = "size must be at least 1", value = 1) Integer size,
            @Param(value = "userId") Long userId) throws ApiException {
        LoansPagingDTO loansPagingDTO = userLoanService.getLoans(page, size, userId);
        return new ResponseEntity<>(loansPagingDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteLoan(@PathVariable Long id) throws ApiException {
        logger.info("Deleting loan with id: " + id);
        userLoanService.deleteLoan(id);
        logger.info("Successfull deleting loan with id: " + id);
        return new ResponseEntity<>(BaseResponse.builder().message("user deleted'").build(), HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Echo health")
    @GetMapping("/echohealth")
    public ResponseEntity<Health> checkAvailability() {
        logger.info("Echo health check for /user controller");
        double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up();
        if (chance > 0.9) {
            status = Health.down();
        }
        return new ResponseEntity<>(status.build(), HttpStatus.OK);
    }

}