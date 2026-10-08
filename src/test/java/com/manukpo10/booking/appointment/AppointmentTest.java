package com.manukpo10.booking.appointment;

import com.manukpo10.booking.common.ConflictException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AppointmentTest {

    @Test
    void cancel_whenBooked_changesStatusToCancelled() {
        // Arrange (preparar): un turno recién creado
        Appointment appointment = new Appointment(null, null,
                LocalDateTime.of(2026, 10, 20, 15, 0),
                LocalDateTime.of(2026, 10, 20, 15, 30));

        // Act (actuar): ejecutar lo que queremos probar
        appointment.cancel();

        // Assert (verificar): ¿quedó como esperábamos?
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
    }

    @Test
    void cancel_whenAlreadyCancelled_throwsConflict() {
        // Arrange: un turno que YA está cancelado
        Appointment appointment = new Appointment(null, null,
                LocalDateTime.of(2026, 10, 20, 15, 0),
                LocalDateTime.of(2026, 10, 20, 15, 30));
        appointment.cancel();

        // Act + Assert: cancelarlo de nuevo tiene que lanzar la excepción
         assertThatThrownBy(() -> appointment.cancel())
                .isInstanceOf(ConflictException.class)
                .hasMessage("Appointment is already cancelled");
    }
}