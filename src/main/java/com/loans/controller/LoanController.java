package com.loans.controller;

import com.loans.exception.ApiException;
import com.loans.model.dto.BaseResponse;
import com.loans.model.dto.BaseUserDTO;
import com.loans.model.dto.LoanDTO;
import com.loans.model.dto.UserDTO;
import com.loans.service.UserLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/loans")
@Api(tags = "Loans account API")
public class LoanController {
    @Autowired
    private UserLoanService userLoanService;

    @ApiOperation(value = "Post Loans")
    @PostMapping()
    public ResponseEntity<BaseResponse> postUser(@RequestBody @Valid LoanDTO loanDTO) throws ApiException {
        userLoanService.postLoan(loanDTO);
        return new ResponseEntity<>(BaseResponse.builder().message("Loan created").build(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Users")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws ApiException {
        return new ResponseEntity<>(userLoanService.getUser(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ApiException {
        userLoanService.deleteUser(id);
        return new ResponseEntity<>("'message':'user deleted'", HttpStatus.OK);
    }

    @ApiOperation(value = "Echo health")
    @GetMapping("/echohealth")
    public ResponseEntity<Health> checkAvailability() {
        double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up();
        if (chance > 0.9) {
            status = Health.down();
        }
        return new ResponseEntity<>(status.build(), HttpStatus.OK);
    }

}