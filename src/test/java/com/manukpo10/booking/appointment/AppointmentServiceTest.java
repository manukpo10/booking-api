package com.manukpo10.booking.appointment;

import com.manukpo10.booking.common.ConflictException;
import com.manukpo10.booking.customer.Customer;
import com.manukpo10.booking.customer.CustomerService;
import com.manukpo10.booking.professional.Professional;
import com.manukpo10.booking.professional.ProfessionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.manukpo10.booking.appointment.AppointmentStatus.BOOKED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProfessionalService professionalService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Customer customer;
    private Professional professional;
    private LocalDateTime start;
    private AppointmentRequest request;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "Juan", "juan@mail.com", "123");
        professional = new Professional(2L, "Laura", "Corte");
        start = LocalDateTime.of(2026, 10, 20, 15, 0);
        request = new AppointmentRequest(1L, 2L, start, 30);
    }

    @Test
    void create_whenSlotOverlaps_throwsConflict() {
        // Arrange: los datos y el guion de los actores

        when(customerService.findEntityById(1L)).thenReturn(customer);
        when(professionalService.findEntityById(2L)).thenReturn(professional);
        when(appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        2L, BOOKED, start.plusMinutes(30), start))
                .thenReturn(true);

        // Act + Assert: el service tiene que rechazar el turno
        assertThatThrownBy(() -> appointmentService.create(request))
                .isInstanceOf(ConflictException.class);

        // Y además: NO tiene que haber guardado nada
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void create_whenSlotIsFree_savesAppointment() {
        // Arrange: los mismos datos del test anterior (copiá esas cuatro líneas)

        // El guion: esta vez NO hay choque, y el save devuelve el turno guardado
        when(customerService.findEntityById(1L)).thenReturn(customer);
        when(professionalService.findEntityById(2L)).thenReturn(professional);
        when(appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        2L, BOOKED, start.plusMinutes(30), start))
                .thenReturn(false);
        when(appointmentRepository.save(any()))
                .thenReturn(new Appointment(customer, professional, start, start.plusMinutes(30)));

        // Act: esta vez no esperamos excepción, así que guardamos la respuesta
        AppointmentResponse response = appointmentService.create(request);

        // Assert
        assertThat(response.customerName()).isEqualTo("Juan");
        assertThat(response.status()).isEqualTo(BOOKED);
        verify(appointmentRepository).save(any());     // esta vez SÍ tiene que haber guardado
    }
}