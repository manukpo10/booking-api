package com.manukpo10.booking.customer;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> findAll() {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            responses.add(CustomerResponse.from(customer));
        }
        return responses;
    }


    public CustomerResponse findById(Long id) {
        return CustomerResponse.from(findEntityById(id));
    }


    public Customer findEntityById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        return optionalCustomer.get();
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer customer = new Customer(null, request.name(), request.email(), request.phone());
        Customer saved = customerRepository.save(customer);
        return CustomerResponse.from(saved);
    }

    public CustomerResponse update(Long id, CustomerRequest request) {

        Customer customer = findEntityById(id);


        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());

        Customer saved = customerRepository.save(customer);
        return CustomerResponse.from(saved);
    }

    public void delete(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);
    }
}

