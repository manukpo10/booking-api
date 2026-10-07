package com.manukpo10.booking.appointment;

import com.manukpo10.booking.common.ResourceNotFoundException;

public class AppointmentNotFoundException extends ResourceNotFoundException {
    public AppointmentNotFoundException(Long id) {
        super("Appointment not found with id: " + id);
    }
}
