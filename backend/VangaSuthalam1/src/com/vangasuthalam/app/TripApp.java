
package com.vangasuthalam.app;

import java.sql.Timestamp;
import java.util.Scanner;

import com.vangasuthalam.controller.TripController;
import com.vangasuthalam.model.Trip;

public class TripApp {

    public static void start(Scanner scanner) {

        int choice;

        do {
            System.out.println();
            System.out.println("======================================");
            System.out.println("           TRIP MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Create Trip");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println();
                    System.out.println("----- CREATE TRIP -----");

                    System.out.print("Enter Booking ID: ");
                    int bookingId = scanner.nextInt();

                    System.out.print("Enter Captain ID: ");
                    int captainId = scanner.nextInt();

                    System.out.print("Enter Boat ID: ");
                    int boatId = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print(
                            "Enter Trip Start (YYYY-MM-DD HH:MM:SS): ");
                    Timestamp start =
                            Timestamp.valueOf(scanner.nextLine());

                    System.out.print(
                            "Enter Trip End (YYYY-MM-DD HH:MM:SS): ");
                    Timestamp end =
                            Timestamp.valueOf(scanner.nextLine());

                    Trip trip = new Trip(
                            bookingId,
                            captainId,
                            boatId,
                            start,
                            end
                    );

                    TripController controller =
                            new TripController();

                    boolean result =
                            controller.createTrip(trip);

                    if (result) {
                        System.out.println(
                                "Trip Created Successfully!");
                    } else {
                        System.out.println(
                                "Trip Creation Failed!");
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

