package com.manukpo10.booking.customer;


import com.manukpo10.booking.common.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void findById_whenCustomerDoesNotExist_returns404WithApiError() throws Exception {
        // Arrange: el service (doble) lanza la alarma
        when(customerService.findById(99L)).thenThrow(new CustomerNotFoundException(99L));

        // Act + Assert: pedimos GET /api/customers/99 y revisamos la respuesta
        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Customer not found with id: 99"));
    }

    @Test
    void create_whenNameIsBlank_returns400() throws Exception {
        String body = """
                { "name": "", "email": "pedro@gmail.com", "phone": "221 555-1234" }
                """;


        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));

        verify(customerService, never()).create(any());
    }

    @Test
    void create_whenEmailAlreadyExists_returns409() throws Exception {
        // Arrange: el service (doble) lanza la alarma
        when(customerService.create(any()))
                .thenThrow(new ConflictException("Email already registered: pedro@gmail.com"));

        String body = """
                { "name": "Pedro", "email": "pedro@gmail.com", "phone": "221 555-1234" }
                """;

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already registered: pedro@gmail.com"));
    }


}