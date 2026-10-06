package com.manukpo10.booking.customer;


import com.manukpo10.booking.common.ResourceNotFoundException;

public class CustomerNotFoundException extends ResourceNotFoundException {

    public CustomerNotFoundException(Long id) {
        super("Customer not found with id: " + id);
    }
}