package com.loans.service;

import com.loans.utils.Constants;
import com.loans.exception.ApiException;
import com.loans.model.dto.*;
import com.loans.model.entity.LoanEntity;
import com.loans.model.entity.UserEntity;
import com.loans.repository.LoanRepository;
import com.loans.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserLoanService implements UserInterface, LoanInterface {
    private ModelMapper mapper = new ModelMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(UserLoanService.class);

    public BaseUserDTO postUser(BaseUserDTO userDTO) throws ApiException {
        try {
            logger.info("User will be created");
            Long userId = userRepository.save(mapper.map(userDTO, UserEntity.class)).getId();
            logger.info("User created with id: " + userId.toString());
            userDTO.setId(userId);
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_POSTING_USER);
            throw new ApiException(500, Constants.PROBLEM_POSTING_USER);
        }
        return userDTO;
    }

    public UserDTO getUser(Long userId) throws ApiException {
        try {
            logger.info("Checking if user exists otherwise NoSuchElementException will be thrown");
            UserEntity userEntity = userRepository.findById(userId).get();
            logger.info("User data found with id: " + userId);
            Set<LoanDTO> loanDTOS = userEntity.getLoans().stream().map(
                    loan -> mapper.map(loan, LoanDTO.class))
                    .collect(Collectors.toSet());
            return new UserDTO(userEntity.getId(), userEntity.getEmail(), userEntity.getFirstName(), userEntity.getLastName(), loanDTOS);
        } catch (NoSuchElementException noUser) {
            logger.error(Constants.USER_NOT_FOUND);
            throw new ApiException(404, Constants.USER_NOT_FOUND);
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_GETTING_USER);
            throw new ApiException(500, Constants.PROBLEM_GETTING_USER);
        }
    }

    @Transactional
    public void deleteUser(Long userId) throws ApiException {
        try {
            logger.info("Checking if user exists otherwise NoSuchElementException will be thrown");
            UserEntity userEntity = userRepository.findById(userId).get();
            userRepository.delete(userEntity);
            logger.info("Deletion success for user: " + userId);
        } catch (NoSuchElementException noUser) {
            logger.error(Constants.USER_NOT_FOUND);
            throw new ApiException(404, Constants.USER_NOT_FOUND);
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_DELETING_USER);
            throw new ApiException(500, Constants.PROBLEM_DELETING_USER);
        }
    }

    @Override
    public void postLoan(LoanDTO loanDTO) throws ApiException {
        try {
            LoanEntity loanEntity = mapper.map(loanDTO, LoanEntity.class);
            logger.info("Checking if userId exists");
            userRepository.findById(loanDTO.getUserId()).get();
            logger.info("saving loan transaction begins");
            loanRepository.save(loanEntity);
            logger.info("saving loan transaction successful");
        } catch (NoSuchElementException noUser) {
            logger.error(Constants.USER_NOT_FOUND);
            throw new ApiException(404, Constants.USER_NOT_FOUND);
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_POSTING_LOAN);
            throw new ApiException(500, Constants.PROBLEM_POSTING_LOAN);
        }
    }

    @Override
    public LoansPagingDTO getLoans(Integer page, Integer size, Long userId) throws ApiException {
        try {

            Query query;
            if(userId != null) {
                logger.info("Pagination will be with userId: " + userId);
                query = entityManager.createQuery("select L from LoanEntity L where L.userId = :userId");
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userId);
                query.setParameter("userId", userEntity);
            } else {
                query = entityManager.createQuery("from LoanEntity");
            }
            query.setFirstResult((page-1) * size);
            query.setMaxResults(size);

            logger.info("Getting loans");
            List<LoanEntity> loanEntities = query.getResultList();
            logger.info("Getting loans transaction success");

            logger.info("Counting loans");
            Query queryTotal = entityManager.createQuery
                    ("Select count(L.id) from LoanEntity L");
            Long countResult = (Long) queryTotal.getSingleResult();
            logger.info("Total loans: " + countResult.toString());

            LoansPagingDTO loansPagingDTO = new LoansPagingDTO(size, page, countResult);
            loansPagingDTO.getItems().addAll(loanEntities.stream().map(
                    loan -> mapper.map(loan, LoanDTO.class))
                    .collect(Collectors.toSet()));
            return loansPagingDTO;
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_GETTING_LOAN);
            throw new ApiException(500, Constants.PROBLEM_GETTING_LOAN);
        }
    }

    @Override
    public void deleteLoan(Long loanId) throws ApiException {
        try {
            logger.info("Checking if loan exists, otherwise NoSuchElementException will be thrown");
            loanRepository.findById(loanId).get();
            logger.info("Delete loan transaction begins");
            loanRepository.deleteById(loanId);
            logger.info("Deletion successful");
        } catch (NoSuchElementException noUser) {
            logger.error(Constants.LOAN_NOT_FOUND);
            throw new ApiException(404, Constants.LOAN_NOT_FOUND);
        } catch (Exception ex) {
            logger.error(Constants.PROBLEM_DELETING_LOAN);
            throw new ApiException(500, Constants.PROBLEM_DELETING_LOAN);
        }
    }
}
