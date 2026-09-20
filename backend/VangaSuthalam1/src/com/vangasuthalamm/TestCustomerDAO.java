package com.vangasuthalamm;

import com.vangasuthalam.dao.CustomerDAO;
import com.vangasuthalam.model.Customer;

public class TestCustomerDAO {

    public static void main(String[] args) {

        Customer customer = new Customer(
                "John",
                "9876543211",
                "john@gmail.com",
                "12346",
                "Ramanathapuram"
        );

        CustomerDAO dao = new CustomerDAO();

        boolean result = dao.addCustomer(customer);

        if (result) {
            System.out.println("Customer Added Successfully!");
        } else {
            System.out.println("Customer Add Failed!");
        }
    }
}