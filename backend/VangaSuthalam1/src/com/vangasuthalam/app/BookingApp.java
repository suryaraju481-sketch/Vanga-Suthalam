package com.vangasuthalam.app;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

import com.vangasuthalam.controller.BookingController;
import com.vangasuthalam.controller.BoatController;
import com.vangasuthalam.controller.TripPackageController;
import com.vangasuthalam.model.Booking;

public class BookingApp {

    public static void start(Scanner scanner, int customerId) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             CREATE BOOKING");
        System.out.println("==========================================");

        System.out.println("Customer ID : " + customerId);

        // ==========================================
        // DESTINATION TYPE
        // ==========================================

        System.out.println();
        System.out.println("Select Destination Type");
        System.out.println("------------------------------------------");
        System.out.println("1. Sea Destination");
        System.out.println("2. Island Explorer");
        System.out.println("------------------------------------------");

        System.out.print("Enter choice: ");
        int destinationType = scanner.nextInt();

        int destinationId;

        // ==========================================
        // SEA DESTINATION
        // ==========================================

        if (destinationType == 1) {

            System.out.print("Enter Sea Destination ID: ");
            destinationId = scanner.nextInt();

            if (destinationId <= 0) {

                System.out.println();
                System.out.println("Invalid Destination ID.");
                return;
            }

        }

        // ==========================================
        // ISLAND DESTINATION
        // ==========================================

        else if (destinationType == 2) {

            System.out.println();
            System.out.println("Island Explorer");
            System.out.println("------------------------------------------");
            System.out.println("1. Mulli Theevu");
            System.out.println("2. Desert Island");
            System.out.println("3. Appa Theevu");
            System.out.println("4. Valai Theevu");
            System.out.println("5. Muyal Theevu");
            System.out.println("------------------------------------------");

            System.out.print("Enter Island ID: ");
            int islandDisplayId = scanner.nextInt();

            if (islandDisplayId >= 1 &&
                islandDisplayId <= 5) {

                // Display ID 1-5
                // Database ID 5-9

                destinationId =
                        islandDisplayId + 4;

            } else {

                System.out.println();
                System.out.println("Invalid Island ID.");
                return;
            }

            System.out.println();
            System.out.println("------------------------------------------");
            System.out.println("Island approval is required.");
            System.out.println("------------------------------------------");

        }

        // ==========================================
        // INVALID DESTINATION TYPE
        // ==========================================

        else {

            System.out.println();
            System.out.println("Invalid Destination Type.");
            return;
        }

        // ==========================================
        // PACKAGE
        // ==========================================

        System.out.println();
        System.out.println("==========================================");
        System.out.println("          AVAILABLE PACKAGES");
        System.out.println("==========================================");

        TripPackageController packageController =
                new TripPackageController();

        packageController.viewAllPackages();

        System.out.println("------------------------------------------");

        System.out.print("Enter Package ID: ");
        int packageId = scanner.nextInt();

        // ==========================================
        // GET PACKAGE PRICE FROM DATABASE
        // ==========================================

        double packagePrice =
                packageController.getPackagePrice(packageId);

        if (packagePrice <= 0) {

            System.out.println();
            System.out.println("Invalid Package ID.");
            System.out.println("Package price not found.");

            return;
        }

        // ==========================================
        // BOAT
        // ==========================================

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              BOAT DETAILS");
        System.out.println("==========================================");

        System.out.print("Enter Boat ID: ");
        int boatId = scanner.nextInt();

        if (boatId <= 0) {

            System.out.println();
            System.out.println("Invalid Boat ID.");
            return;
        }

        // ==========================================
        // BOOKING DATE
        // ==========================================

        scanner.nextLine();

        System.out.println();

        System.out.print(
                "Enter Booking Date (YYYY-MM-DD): "
        );

        String dateInput =
                scanner.nextLine();

        Date bookingDate;

        try {

            bookingDate =
                    Date.valueOf(dateInput);

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "Invalid date format."
            );

            System.out.println(
                    "Use YYYY-MM-DD."
            );

            return;
        }

        // ==========================================
        // START TIME
        // ==========================================

        System.out.print(
                "Enter Start Time (HH:MM:SS): "
        );

        String timeInput =
                scanner.nextLine();

        Time startTime;

        try {

            startTime =
                    Time.valueOf(timeInput);

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "Invalid time format."
            );

            System.out.println(
                    "Use HH:MM:SS."
            );

            return;
        }

        // ==========================================
        // NUMBER OF PEOPLE
        // ==========================================

        System.out.print(
                "Enter Number of People: "
        );

        int numberOfPeople =
                scanner.nextInt();

        if (numberOfPeople <= 0) {

            System.out.println();
            System.out.println(
                    "Number of people must be greater than 0."
            );

            return;
        }

        // ==========================================
        // BOAT AVAILABILITY + CAPACITY CHECK
        // ==========================================

        BoatController boatController =
                new BoatController();

        boolean boatAvailable =
                boatController.isBoatAvailable(
                        boatId,
                        numberOfPeople
                );

        if (!boatAvailable) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("          BOAT SELECTION FAILED");
            System.out.println("==========================================");
            System.out.println(
                    "Please select another available boat."
            );
            System.out.println("==========================================");

            return;
        }

        // ==========================================
        // FISHING
        // ==========================================

        System.out.println();

        System.out.print(
                "Fishing Required? (true/false): "
        );

        boolean fishingRequired =
                scanner.nextBoolean();

        // ==========================================
        // FOOD
        // ==========================================

        System.out.print(
                "Food Required? (true/false): "
        );

        boolean foodRequired =
                scanner.nextBoolean();

        // ==========================================
        // OPTIONAL SERVICE CHARGES
        // ==========================================

        double fishingCharge = 0;

        if (fishingRequired) {

            fishingCharge = 500;
        }

        double foodCharge = 0;

        if (foodRequired) {

            foodCharge = 500;
        }

        // ==========================================
        // PRICE CALCULATION
        // ==========================================

        double pricePerPerson =
                packagePrice
                + fishingCharge
                + foodCharge;

        double totalAmount =
                pricePerPerson
                * numberOfPeople;

        // ==========================================
        // PRICE SUMMARY
        // ==========================================

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             PRICE SUMMARY");
        System.out.println("==========================================");

        System.out.println(
                "Package Price / Person : ₹"
                + packagePrice
        );

        System.out.println(
                "Fishing / Person       : ₹"
                + fishingCharge
        );

        System.out.println(
                "Food / Person          : ₹"
                + foodCharge
        );

        System.out.println("------------------------------------------");

        System.out.println(
                "Price / Person         : ₹"
                + pricePerPerson
        );

        System.out.println(
                "Number of People       : "
                + numberOfPeople
        );

        System.out.println("------------------------------------------");

        System.out.println(
                "TOTAL AMOUNT           : ₹"
                + totalAmount
        );

        System.out.println("==========================================");

        // ==========================================
        // CREATE BOOKING OBJECT
        // ==========================================

        Booking booking =
                new Booking();

        booking.setCustomerId(customerId);

        booking.setDestinationId(
                destinationId
        );

        booking.setPackageId(
                packageId
        );

        booking.setBoatId(
                boatId
        );

        booking.setBookingDate(
                bookingDate
        );

        booking.setStartTime(
                startTime
        );

        booking.setNumberOfPeople(
                numberOfPeople
        );

        booking.setFishingRequired(
                fishingRequired
        );

        booking.setFoodRequired(
                foodRequired
        );

        booking.setTotalAmount(
                totalAmount
        );

        // ==========================================
        // SAVE BOOKING
        // ==========================================

        BookingController bookingController =
                new BookingController();

        int bookingId =
                bookingController.addBooking(
                        booking
                );

        // ==========================================
        // BOOKING RESULT
        // ==========================================

        System.out.println();

        if (bookingId > 0) {

            System.out.println("==========================================");
            System.out.println("       BOOKING CREATED SUCCESSFULLY!");
            System.out.println("==========================================");

            System.out.println(
                    "Booking ID      : "
                    + bookingId
            );

            System.out.println(
                    "Customer ID     : "
                    + customerId
            );

            System.out.println(
                    "Destination ID  : "
                    + destinationId
            );

            System.out.println(
                    "Package ID      : "
                    + packageId
            );

            System.out.println(
                    "Boat ID         : "
                    + boatId
            );

            System.out.println(
                    "Booking Date    : "
                    + bookingDate
            );

            System.out.println(
                    "Start Time      : "
                    + startTime
            );

            System.out.println(
                    "People          : "
                    + numberOfPeople
            );

            System.out.println(
                    "Fishing         : "
                    + fishingRequired
            );

            System.out.println(
                    "Food            : "
                    + foodRequired
            );

            System.out.println(
                    "Price / Person  : ₹"
                    + pricePerPerson
            );

            System.out.println(
                    "Total Amount    : ₹"
                    + totalAmount
            );

            if (destinationType == 2) {

                System.out.println(
                        "Island Approval : REQUIRED"
                );
            }

            System.out.println(
                    "Booking Status  : PENDING"
            );

            System.out.println("==========================================");

        } else {

            System.out.println("==========================================");
            System.out.println("       BOOKING CREATION FAILED!");
            System.out.println("==========================================");
        }
    }
}