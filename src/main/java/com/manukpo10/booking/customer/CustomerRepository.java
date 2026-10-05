package com.manukpo10.booking.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class CustomerRepository {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Customer> findAll() {

        return new ArrayList<>(customers.values());

    }

    public Optional<Customer> findById(Long id) {

        return Optional.ofNullable(customers.get(id));
    }

    public Customer save(Customer customer) {


        if (customer.getId() == null) {
            customer.setId(nextId.getAndIncrement());
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    public boolean existsById(Long id) {

        return customers.containsKey(id);


    }

    public void deleteById(Long id) {

        customers.remove(id);
    }
}