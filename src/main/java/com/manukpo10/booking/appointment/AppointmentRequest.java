package com.manukpo10.booking.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull Long customerId,
        @NotNull Long professionalId,
        @NotNull @Future LocalDateTime startTime,
        @NotNull @Positive @Max(480) Integer durationMinutes
) {}
