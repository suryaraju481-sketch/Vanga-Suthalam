package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.BoatController;
import com.vangasuthalam.model.Boat;

public class BoatApp {

    public static void start(Scanner scanner) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("            BOAT MANAGEMENT");
        System.out.println("==========================================");

        System.out.println("1. Register Boat");
        System.out.println("2. View All Boats");
        System.out.println("0. Back");

        System.out.println("------------------------------------------");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();

        switch (choice) {

        case 1:

            scanner.nextLine();

            System.out.println();
            System.out.println("==========================================");
            System.out.println("             REGISTER BOAT");
            System.out.println("==========================================");

            System.out.print("Enter Captain ID: ");
            int captainId = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter Boat Name: ");
            String boatName = scanner.nextLine();

            System.out.print("Enter Boat Number: ");
            String boatNumber = scanner.nextLine();

            System.out.print("Enter Boat Type: ");
            String boatType = scanner.nextLine();

            System.out.print("Enter Capacity: ");
            int capacity = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter Engine Details: ");
            String engineDetails = scanner.nextLine();

            System.out.print("Enter Safety Equipment: ");
            String safetyEquipment = scanner.nextLine();

            System.out.print("Enter Registration Details: ");
            String registrationDetails = scanner.nextLine();

            Boat boat = new Boat();

            boat.setCaptainId(captainId);
            boat.setBoatName(boatName);
            boat.setBoatNumber(boatNumber);
            boat.setBoatType(boatType);
            boat.setCapacity(capacity);
            boat.setEngineDetails(engineDetails);
            boat.setSafetyEquipment(safetyEquipment);
            boat.setRegistrationDetails(registrationDetails);

            BoatController controller =
                    new BoatController();

            if (controller.addBoat(boat)) {

                System.out.println();
                System.out.println("==========================================");
                System.out.println("       BOAT REGISTRATION SUCCESSFUL!");
                System.out.println("==========================================");

            } else {

                System.out.println();
                System.out.println("==========================================");
                System.out.println("        BOAT REGISTRATION FAILED!");
                System.out.println("==========================================");
            }

            break;

        case 2:

            BoatController viewController =
                    new BoatController();

            viewController.viewAllBoats();

            break;

        case 0:

            System.out.println("Returning to previous menu.");

            break;

        default:

            System.out.println("Invalid choice.");

            break;
        }
    }
}