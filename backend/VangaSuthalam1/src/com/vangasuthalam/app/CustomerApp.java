
package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.CustomerController;
import com.vangasuthalam.model.Customer;

public class CustomerApp {

    public static void start(Scanner scanner) {

        int choice;

        do {
            System.out.println("\n========== CUSTOMER MANAGEMENT ==========");
            System.out.println("1. Register Customer");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n----- CUSTOMER REGISTRATION -----");

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Mobile: ");
                    String mobile = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();

                    Customer customer = new Customer(
                            name, mobile, email, password, address);

                    CustomerController controller =
                            new CustomerController();

                    if (controller.registerCustomer(customer)) {
                        System.out.println(
                                "Customer Registration Successful!");
                    } else {
                        System.out.println(
                                "Customer Registration Failed!");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }
}

