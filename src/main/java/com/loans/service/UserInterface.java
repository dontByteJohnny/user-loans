package com.loans.service;

import com.loans.exception.ApiException;
import com.loans.model.dto.BaseUserDTO;
import com.loans.model.dto.UserDTO;

public interface UserInterface {
    BaseUserDTO postUser(BaseUserDTO userDTO) throws ApiException;
    UserDTO getUser(Long userId) throws ApiException;
    void deleteUser(Long userId) throws ApiException;

}
