
package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.DestinationController;
import com.vangasuthalam.model.Destination;

public class DestinationApp {

    public static void start(Scanner scanner) {

        int choice;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("      DESTINATION MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Add Destination");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println();
                    System.out.println("----- ADD DESTINATION -----");

                    System.out.print("Enter Destination Name: ");
                    String destinationName = scanner.nextLine();

                    System.out.print("Enter Location: ");
                    String location = scanner.nextLine();

                    System.out.print("Enter Destination Type: ");
                    String destinationType = scanner.nextLine();

                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter Distance (KM): ");
                    double distanceKm = scanner.nextDouble();

                    System.out.print("Is Landing Allowed? (true/false): ");
                    boolean landingAllowed = scanner.nextBoolean();

                    System.out.print("Is Trip Allowed? (true/false): ");
                    boolean tripAllowed = scanner.nextBoolean();

                    Destination destination = new Destination(
                            destinationName,
                            location,
                            destinationType,
                            description,
                            distanceKm,
                            landingAllowed,
                            tripAllowed
                    );

                    DestinationController controller =
                            new DestinationController();

                    boolean result =
                            controller.addDestination(destination);

                    if (result) {

                        System.out.println();
                        System.out.println(
                                "Destination Added Successfully!"
                        );

                    } else {

                        System.out.println();
                        System.out.println(
                                "Destination Addition Failed!"
                        );
                    }

                    break;

                case 0:

                    System.out.println(
                            "Returning to Main Menu..."
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }

        } while (choice != 0);
    }
}

