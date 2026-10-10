package com.manukpo10.booking.customer;

import com.manukpo10.booking.common.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void create_whenEmailAlreadyExists_throwsConflict() {
        // Arrange (preparar): un turno recién creado
        CustomerRequest customerRequest = new CustomerRequest("Pedro",
                "pedro@gmail.com",
                "2213080532");


        // Act (actuar): ejecutar lo que queremos probar

        when(customerRepository
                .existsByEmail("pedro@gmail.com"))
                .thenReturn(true);

        assertThatThrownBy(() -> customerService.create(customerRequest))
                .isInstanceOf(ConflictException.class);

        // Y además: NO tiene que haber guardado nada
        verify(customerRepository, never()).save(any());
    }

    @Test
    void create_whenEmailIsNew_savesCustomer() {
        // Arrange
        CustomerRequest request = new CustomerRequest("Pedro", "pedro@gmail.com", "2213080532");
        Customer saved = new Customer(1L, "Pedro", "pedro@gmail.com", "2213080532");

        when(customerRepository.existsByEmail("pedro@gmail.com")).thenReturn(false);
        when(customerRepository.save(any())).thenReturn(saved);

        // Act
        CustomerResponse response = customerService.create(request);

        // Assert
        assertThat(response.name()).isEqualTo("Pedro");
        verify(customerRepository).save(any());
    }
}
