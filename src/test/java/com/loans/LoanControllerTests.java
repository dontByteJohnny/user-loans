package com.loans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.loans.controller.LoanController;
import com.loans.exception.ApiException;
import com.loans.model.dto.LoanDTO;
import com.loans.model.dto.LoansPagingDTO;
import com.loans.service.UserLoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoanController.class)
public class LoanControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoanService userLoanService;

    @Test
    public void postingLoan_ok() throws Exception {
        LoanDTO loanDTO = new LoanDTO(1L, BigDecimal.valueOf(2000.33), 1L);

        Mockito.doNothing().when(userLoanService).postLoan(loanDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(loanDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/loans")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void getLoan_ok() throws Exception {
        LoansPagingDTO loansPagingDTO = new LoansPagingDTO(3, 1, 1500L);
        Set<LoanDTO> loanDTOS = new HashSet<>();
        LoanDTO loanDTO = new LoanDTO(1L, BigDecimal.valueOf(2000.33), 1L);
        loanDTOS.add(loanDTO);

        Mockito.when(userLoanService.getLoans(1, 3,1L)).thenReturn(loansPagingDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/loans?page=1&size=3&userId=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getLoan_userNotFound() throws Exception {
        Mockito.when(userLoanService.getLoans(1, 3,100L)).thenThrow(new ApiException(404, "User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/loans?page=1&size=3&userId=100"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void deleteLoan_ok() throws Exception {
        Mockito.doNothing().when(userLoanService).deleteLoan(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/loans/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteLoan_userNotFound() throws Exception {
        Mockito.doThrow(new ApiException(404, "User not found")).when(userLoanService).deleteLoan(100L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/loans/100"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
