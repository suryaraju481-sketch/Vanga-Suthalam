
package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.CaptainController;
import com.vangasuthalam.model.Captain;

public class CaptainApp {

    public static void start(Scanner scanner) {

        int choice;

        do {
            System.out.println("\n========== CAPTAIN MANAGEMENT ==========");
            System.out.println("1. Register Captain");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n----- CAPTAIN REGISTRATION -----");

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Mobile: ");
                    String mobile = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter Experience Years: ");
                    int experience = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Qualification: ");
                    String qualification = scanner.nextLine();

                    Captain captain = new Captain(
                            name,
                            mobile,
                            email,
                            password,
                            experience,
                            qualification);

                    CaptainController controller =
                            new CaptainController();

                    if (controller.registerCaptain(captain)) {
                        System.out.println(
                                "Captain Registration Successful!");
                    } else {
                        System.out.println(
                                "Captain Registration Failed!");
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

