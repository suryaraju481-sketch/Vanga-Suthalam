package com.vangasuthalam.app;

import java.util.Scanner;
import com.vangasuthalam.controller.CustomerLoginController;

public class CustomerLoginApp {

    public static void start(Scanner scanner) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("          CUSTOMER LOGIN");
        System.out.println("======================================");

        scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        CustomerLoginController controller =
                new CustomerLoginController();

        int customerId =
                controller.loginCustomer(email, password);

        if (customerId > 0) {

            System.out.println();
            System.out.println("Login Successful!");
            System.out.println("Customer ID: " + customerId);

            // Open Customer Menu
            CustomerMenuApp.start(scanner, customerId);

        } else {

            System.out.println();
            System.out.println("Invalid Email or Password!");
        }
    }
}