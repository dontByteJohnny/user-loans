package com.loans.service;

import com.loans.exception.ApiException;
import com.loans.model.dto.BaseUserDTO;
import com.loans.model.dto.LoanDTO;
import com.loans.model.dto.LoansPagingDTO;
import com.loans.model.dto.UserDTO;
import com.loans.model.entity.LoanEntity;
import com.loans.model.entity.UserEntity;
import com.loans.repository.LoanRepository;
import com.loans.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserLoanService implements UserInterface, LoanInterface {
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;

    public BaseUserDTO postUser(BaseUserDTO userDTO) throws ApiException {
        Long userId = userRepository.save(mapper.map(userDTO, UserEntity.class)).getId();
        userDTO.setId(userId);
        return userDTO;
    }

    public UserDTO getUser(Long userId) throws ApiException {
        UserEntity userEntity = userRepository.findById(userId).get();
        UserDTO userDTO = new UserDTO(userId, userEntity.getEmail(), userEntity.getFirstName(), userEntity.getLastName());
        userDTO.setLoans(userEntity.getLoans().stream().map(
                loan -> mapper.map(loan, LoanDTO.class))
                .collect(Collectors.toSet()));
        return userDTO;
    }

    public void deleteUser(Long userId) throws ApiException {
        userRepository.deleteById(userId);
    }

    @Override
    public void postLoan(LoanDTO loanDTO) throws ApiException {
        UserEntity userEntity = userRepository.findById(loanDTO.getUserId()).get();
        if (userEntity != null) {
            LoanEntity loanEntity = mapper.map(loanDTO, LoanEntity.class);
            loanRepository.save(loanEntity);
        } else {
            throw new ApiException(400, "User does not exists");
        }

    }

    @Override
    public LoansPagingDTO getLoan(Long userId) throws ApiException {
        return null;
    }

    @Override
    public void deleteLoan(Long loanId) throws ApiException {

    }
}
