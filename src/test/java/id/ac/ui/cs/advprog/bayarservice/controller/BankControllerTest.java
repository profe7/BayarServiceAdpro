package id.ac.ui.cs.advprog.bayarservice.controller;

import id.ac.ui.cs.advprog.bayarservice.Util;
import id.ac.ui.cs.advprog.bayarservice.dto.Bank.BankRequest;
import id.ac.ui.cs.advprog.bayarservice.model.bank.Bank;
import id.ac.ui.cs.advprog.bayarservice.service.bank.BankServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BankController.class)
@AutoConfigureMockMvc
public class BankControllerTest {
    private static final String END_POINT_PATH = "/api/v1/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankServiceImpl bankService;

    @Test
    void testCreateBankShouldReturn200OK() throws Exception {
        String requestURI = END_POINT_PATH + "addBank";

        Bank bank = Bank.builder()
                .id(1)
                .name("BCA")
                .adminFee(6500)
                .build();

        String requestBody = Util.mapToJson(bank);

        when(bankService.create(any(BankRequest.class))).thenReturn(bank);

        mockMvc.perform(post(requestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("BCA"))
                .andExpect(jsonPath("$.adminFee").value(6500))
                .andExpect(handler().methodName("addBank"))
                .andDo(print());
        verify(bankService, times(1)).create(any(BankRequest.class));
    }

    @Test
    void testCreateBankShouldReturn400BadRequest () throws Exception {
        String requestURI = END_POINT_PATH + "addBank";

        Bank bank = Bank.builder().build();

        String requestBody = Util.mapToJson(bank);

        mockMvc.perform(post(requestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("addBank"))
                .andDo(print());
        verify(bankService, times(1)).create(any(BankRequest.class));
    }

    @Test
    void testCreateBankShouldReturn405MethodNotAllowed () throws Exception {
        String requestURI = END_POINT_PATH + "addBank";

        Bank bank = Bank.builder()
                .id(1)
                .name("BCA")
                .adminFee(6500)
                .build();

        String requestBody = Util.mapToJson(bank);

        mockMvc.perform(get(requestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());

        mockMvc.perform(put(requestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());

        mockMvc.perform(delete(requestURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }
}