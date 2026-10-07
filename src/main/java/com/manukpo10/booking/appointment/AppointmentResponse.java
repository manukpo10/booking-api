package com.manukpo10.booking.appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long customerId,
        String customerName,
        Long professionalId,
        String professionalName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getCustomer().getId(),
                appointment.getCustomer().getName(),
                appointment.getProfessional().getId(),
                appointment.getProfessional().getName(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus()
        );
    }
}