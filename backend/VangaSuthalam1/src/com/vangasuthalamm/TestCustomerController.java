package com.vangasuthalamm;

import com.vangasuthalam.controller.CustomerController;
import com.vangasuthalam.model.Customer;

public class TestCustomerController {

    public static void main(String[] args) {

        Customer customer = new Customer(
                "Arun Kumar",
                "9876543215",
                "arunkumar15@gmail.com",
                "Arun@123",
                "Rameswaram"
        );

        CustomerController controller = new CustomerController();

        boolean result = controller.registerCustomer(customer);

        if (result) {
            System.out.println("Customer Controller Test Successful!");
        } else {
            System.out.println("Customer Controller Test Failed!");
        }
    }
}