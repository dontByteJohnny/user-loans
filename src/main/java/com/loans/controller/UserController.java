package com.loans.controller;

import com.loans.exception.ApiException;
import com.loans.model.dto.BaseResponse;
import com.loans.model.dto.BaseUserDTO;
import com.loans.model.dto.UserDTO;
import com.loans.service.UserLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/users")
@Api(tags = "Users account API")
public class UserController {
    @Autowired
    private UserLoanService userLoanService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "Post Users")
    @PostMapping()
    public ResponseEntity<BaseUserDTO> postUser(@RequestBody @Valid BaseUserDTO userDTO) throws ApiException {
        logger.info("Posting user begins");
        userDTO = userLoanService.postUser(userDTO);
        logger.info("User created with id: " + userDTO.getId());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Users")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws ApiException {
        logger.info("Getting user data for id: " + id);
        UserDTO userDTO = userLoanService.getUser(id);
        logger.info("Getting user data success for id: " + id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable Long id) throws ApiException {
        logger.info("Deletion of user: " + id);
        userLoanService.deleteUser(id);
        logger.info("Deletion success of user: " + id);
        return new ResponseEntity<>(BaseResponse.builder().message("user deleted").build(), HttpStatus.NO_CONTENT);
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