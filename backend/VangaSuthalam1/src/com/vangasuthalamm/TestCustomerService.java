package com.vangasuthalamm;

import com.vangasuthalam.model.Customer;
import com.vangasuthalam.service.CustomerService;

public class TestCustomerService {

    public static void main(String[] args) {

        Customer customer = new Customer(
                "Vijay Kumar",
                "9876543299",
                "vijaykumar99@gmail.com",
                "vijay123",
                "Rameswaram"
        );

        CustomerService service = new CustomerService();

        boolean result = service.registerCustomer(customer);

        if (result) {
            System.out.println("Customer Service Test Successful!");
        } else {
            System.out.println("Customer Service Test Failed!");
        }
    }
}