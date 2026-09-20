package com.vangasuthalam.app;

import java.util.Scanner;

public class VangaSuthalamApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("          VANGA SUTHALAM");
            System.out.println("======================================");
            System.out.println("1. Customer Login");
            System.out.println("2. Customer Management");
            System.out.println("3. Captain Management");
            System.out.println("4. Boat Management");
            System.out.println("5. Destination Management");
            System.out.println("6. Trip Package Management");
            System.out.println("7. Booking Management");
            System.out.println("8. Payment Management");
            System.out.println("9. Safety Management");
            System.out.println("10. Trip Management");
            System.out.println("11. Feedback Management");
            System.out.println("12. Island Approval Management");
            System.out.println("0. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

            case 1:
                CustomerLoginApp.start(scanner);
                break;

            case 2:
                CustomerApp.start(scanner);
                break;

            case 3:
                CaptainApp.start(scanner);
                break;

            case 4:
                BoatApp.start(scanner);
                break;

            case 5:
                DestinationApp.start(scanner);
                break;

            case 6:
                PackageApp.start(scanner);
                break;

            case 7:
                System.out.println();
                System.out.println("======================================");
                System.out.println("       BOOKING MANAGEMENT");
                System.out.println("======================================");
                System.out.println(
                        "Please use Customer Login to create a booking."
                );
                break;

            case 8:
                PaymentApp.start(scanner);
                break;

            case 9:
                SafetyApp.start(scanner);
                break;

            case 10:
                TripApp.start(scanner);
                break;

            case 11:
                System.out.println();
                System.out.println("======================================");
                System.out.println("       FEEDBACK MANAGEMENT");
                System.out.println("======================================");
                System.out.println(
                        "Please use Customer Login to give feedback."
                );
                break;

            case 12:
                IslandApprovalApp.start(scanner);
                break;

            case 0:
                System.out.println();
                
                System.out.println("======================================");
                System.out.println("     Thank You for Using");
                System.out.println("          VANGA SUTHALAM");
                System.out.println("======================================");
                break;

            default:
                System.out.println();
                System.out.println("Invalid choice. Please try again.");
                break;
            }

        } while (choice != 0);

        scanner.close();
    }
}