package com.vangasuthalam.service;

import com.vangasuthalam.dao.CustomerDAO;
import com.vangasuthalam.model.Customer;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
        customerDAO = new CustomerDAO();
    }

    public boolean registerCustomer(Customer customer) {

        if (customer == null) {
            return false;
        }

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return false;
        }

        if (customer.getMobile() == null || customer.getMobile().trim().isEmpty()) {
            return false;
        }

        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            return false;
        }

        return customerDAO.addCustomer(customer);
    }
}
