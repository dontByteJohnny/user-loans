package com.loans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.loans.controller.UserController;
import com.loans.exception.ApiException;
import com.loans.model.dto.BaseUserDTO;
import com.loans.model.dto.LoanDTO;
import com.loans.model.dto.UserDTO;
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
@WebMvcTest(value = UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoanService userLoanService;

    @Test
    public void postingUser_ok() throws Exception {
        BaseUserDTO baseUserDTO = new BaseUserDTO(null, "johnlewis@gmail.com", "John", "Lewis");
        Mockito.when(userLoanService.postUser(baseUserDTO)).thenReturn(baseUserDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(baseUserDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void getUser_ok() throws Exception {
        LoanDTO loanDTO = new LoanDTO(1L, BigDecimal.valueOf(2000.33), 1L);
        Set<LoanDTO> loanDTOS = new HashSet<>();
        loanDTOS.add(loanDTO);
        UserDTO userDTO = new UserDTO(1L, "johnlewis@gmail.com", "John", "Lewis", loanDTOS);
        Mockito.when(userLoanService.getUser(1L)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getUser_userNotFound() throws Exception {
        Mockito.when(userLoanService.getUser(100L)).thenThrow(new ApiException(404, "User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/100"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void deleteUser_ok() throws Exception {
        Mockito.doNothing().when(userLoanService).deleteUser(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteUser_userNotFound() throws Exception {
        Mockito.doThrow(new ApiException(404, "User not found")).when(userLoanService).deleteUser(100L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/100"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
