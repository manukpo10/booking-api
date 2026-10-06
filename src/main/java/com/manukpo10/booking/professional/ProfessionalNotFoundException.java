package com.manukpo10.booking.professional;

import com.manukpo10.booking.common.ResourceNotFoundException;

public class ProfessionalNotFoundException extends ResourceNotFoundException {

    public ProfessionalNotFoundException(Long id) {
        super("Professional not found with id: " + id);
    }
}
