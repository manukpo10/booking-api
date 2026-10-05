package com.manukpo10.booking.professional;

public class ProfessionalNotFoundException extends RuntimeException {

    public ProfessionalNotFoundException(Long id) {
        super("Professional not found with id: " + id);
    }
}
