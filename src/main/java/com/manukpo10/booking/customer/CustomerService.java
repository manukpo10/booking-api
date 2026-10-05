package com.manukpo10.booking.customer;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {

        return customerRepository.findAll();


    }

    public Customer findById(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);


        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }


        return optionalCustomer.get();
    }

    public Customer create(CustomerRequest request) {

        Customer customer = new Customer(null, request.name(), request.email(), request.phone());
        return customerRepository.save(customer);

    }

    public Customer update(Long id, CustomerRequest request) {

        Customer customer = findById(id);


        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());


        return customerRepository.save(customer);
    }

    public void delete(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);
    }
}

