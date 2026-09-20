package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.BookingController;
import com.vangasuthalam.controller.DestinationController;
import com.vangasuthalam.controller.TripPackageController;

public class CustomerMenuApp {

    public static void start(Scanner scanner, int customerId) {

        int choice;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("           CUSTOMER MENU");
            System.out.println("======================================");
            System.out.println("Customer ID: " + customerId);
            System.out.println("--------------------------------------");
            System.out.println("1. View Sea Destinations");
            System.out.println("2. View Island Explorer");
            System.out.println("3. View Trip Packages");
            System.out.println("4. Create Booking");
            System.out.println("5. Make Payment");
            System.out.println("6. View My Bookings");
            System.out.println("7. Give Feedback");
            System.out.println("0. Logout");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

            case 1:

                DestinationController destinationController =
                        new DestinationController();

                destinationController.viewAllDestinations();

                break;

            case 2:

                DestinationController islandController =
                        new DestinationController();

                islandController.viewIslandDestinations();

                break;

            case 3:

                TripPackageController tripPackageController =
                        new TripPackageController();

                tripPackageController.viewAllPackages();

                break;

            case 4:

                BookingApp.start(scanner, customerId);

                break;

            case 5:

                PaymentApp.start(scanner);

                break;

            case 6:

                BookingController bookingController =
                        new BookingController();

                bookingController.viewMyBookings(customerId);

                break;

            case 7:

                FeedbackApp.start(scanner, customerId);

                break;

            case 0:

                System.out.println();
                System.out.println("======================================");
                System.out.println("     CUSTOMER LOGGED OUT SUCCESSFULLY");
                System.out.println("======================================");

                break;

            default:

                System.out.println();
                System.out.println("Invalid choice. Please try again.");

                break;
            }

        } while (choice != 0);
    }
}