
package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.SafetyCheckController;
import com.vangasuthalam.model.SafetyCheck;

public class SafetyApp {

    public static void start(Scanner scanner) {

        int choice;

        do {
            System.out.println();
            System.out.println("======================================");
            System.out.println("          SAFETY MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Perform Safety Check");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println();
                    System.out.println("----- SAFETY CHECK -----");

                    System.out.print("Enter Booking ID: ");
                    int bookingId = scanner.nextInt();

                    System.out.print("Captain Approved? (true/false): ");
                    boolean captainApproved = scanner.nextBoolean();

                    System.out.print("Boat Available? (true/false): ");
                    boolean boatAvailable = scanner.nextBoolean();

                    System.out.print("Passenger Capacity OK? (true/false): ");
                    boolean capacityOk = scanner.nextBoolean();

                    System.out.print("Life Jackets Available? (true/false): ");
                    boolean lifeJackets = scanner.nextBoolean();

                    System.out.print(
                            "Emergency Equipment Available? (true/false): ");
                    boolean emergencyEquipment =
                            scanner.nextBoolean();

                    System.out.print(
                            "Communication Equipment Available? (true/false): ");
                    boolean communicationEquipment =
                            scanner.nextBoolean();

                    System.out.print(
                            "Weather Clearance? (true/false): ");
                    boolean weatherClearance =
                            scanner.nextBoolean();

                    scanner.nextLine();

                    System.out.print("Checked By: ");
                    String checkedBy = scanner.nextLine();

                    SafetyCheck safetyCheck = new SafetyCheck(
                            bookingId,
                            captainApproved,
                            boatAvailable,
                            capacityOk,
                            lifeJackets,
                            emergencyEquipment,
                            communicationEquipment,
                            weatherClearance,
                            checkedBy
                    );

                    SafetyCheckController controller =
                            new SafetyCheckController();

                    boolean result =
                            controller.performSafetyCheck(safetyCheck);

                    if (result) {
                        System.out.println(
                                "Safety Check Successful!");
                    } else {
                        System.out.println(
                                "Safety Check Failed - Trip Not Cleared!");
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

