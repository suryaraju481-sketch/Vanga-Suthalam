package com.vangasuthalam.controller;

import com.vangasuthalam.model.Customer;
import com.vangasuthalam.service.CustomerService;

public class CustomerController {

    private CustomerService customerService;

    public CustomerController() {
        customerService = new CustomerService();
    }

    public boolean registerCustomer(Customer customer) {

        return customerService.registerCustomer(customer);
    }
}
