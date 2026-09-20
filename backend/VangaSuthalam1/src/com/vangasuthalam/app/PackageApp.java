
package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.TripPackageController;
import com.vangasuthalam.model.TripPackage;

public class PackageApp {

    public static void start(Scanner scanner) {

        int choice;

        do {
            System.out.println();
            System.out.println("======================================");
            System.out.println("       TRIP PACKAGE MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Add Trip Package");
            System.out.println("0. Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println();
                    System.out.println("----- ADD TRIP PACKAGE -----");

                    System.out.print("Enter Package Name: ");
                    String packageName = scanner.nextLine();

                    System.out.print("Enter Duration Days: ");
                    int durationDays = scanner.nextInt();

                    System.out.print("Enter Duration Nights: ");
                    int durationNights = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter Base Price Per Person: ");
                    double price = scanner.nextDouble();

                    System.out.print("Is Fishing Included? (true/false): ");
                    boolean fishingIncluded = scanner.nextBoolean();

                    System.out.print("Is Food Included? (true/false): ");
                    boolean foodIncluded = scanner.nextBoolean();

                    TripPackage tripPackage = new TripPackage(
                            packageName,
                            durationDays,
                            durationNights,
                            description,
                            price,
                            fishingIncluded,
                            foodIncluded
                    );

                    TripPackageController controller =
                            new TripPackageController();

                    boolean result =
                            controller.addTripPackage(tripPackage);

                    if (result) {
                        System.out.println(
                                "Trip Package Added Successfully!");
                    } else {
                        System.out.println(
                                "Trip Package Addition Failed!");
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

