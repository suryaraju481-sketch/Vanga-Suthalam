package com.vangasuthalam.app;

import java.sql.Date;
import java.util.Scanner;

import com.vangasuthalam.controller.IslandApprovalController;
import com.vangasuthalam.model.IslandApproval;

public class IslandApprovalApp {

    public static void start(Scanner scanner) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("           ISLAND APPROVAL");
        System.out.println("==========================================");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();

        System.out.println();
        System.out.println("Island Destinations:");
        System.out.println("------------------------------------------");
        System.out.println("1. Mulli Theevu");
        System.out.println("2. Desert Island");
        System.out.println("3. Appa Theevu");
        System.out.println("4. Valai Theevu");
        System.out.println("5. Muyal Theevu");
        System.out.println("------------------------------------------");

        System.out.print("Enter Island ID: ");
        int islandDisplayId = scanner.nextInt();

        int destinationId;

        if (islandDisplayId >= 1 &&
            islandDisplayId <= 5) {

            destinationId = islandDisplayId + 4;

        } else {

            System.out.println();
            System.out.println("Invalid Island ID.");
            return;
        }

        scanner.nextLine();

        System.out.print("Enter Certificate Number: ");
        String certificateNumber = scanner.nextLine();

        System.out.print("Enter Approved By: ");
        String approvedBy = scanner.nextLine();

        System.out.print("Enter Approval Start Date (YYYY-MM-DD): ");

        Date startDate;

        try {

            startDate = Date.valueOf(scanner.nextLine());

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid start date.");
            return;
        }

        System.out.print("Enter Approval End Date (YYYY-MM-DD): ");

        Date endDate;

        try {

            endDate = Date.valueOf(scanner.nextLine());

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid end date.");
            return;
        }

        System.out.print("Enter Remarks: ");
        String remarks = scanner.nextLine();

        IslandApproval approval =
                new IslandApproval(
                        bookingId,
                        destinationId,
                        certificateNumber,
                        approvedBy,
                        startDate,
                        endDate,
                        remarks
                );

        IslandApprovalController controller =
                new IslandApprovalController();

        boolean result =
                controller.addIslandApproval(approval);

        System.out.println();

        if (result) {

            System.out.println("==========================================");
            System.out.println("     ISLAND APPROVAL SUCCESSFUL!");
            System.out.println("==========================================");
            System.out.println("Booking ID       : " + bookingId);
            System.out.println("Destination ID   : " + destinationId);
            System.out.println("Certificate No   : " + certificateNumber);
            System.out.println("Approved By      : " + approvedBy);
            System.out.println("Start Date       : " + startDate);
            System.out.println("End Date         : " + endDate);
            System.out.println("Approval Status  : APPROVED");
            System.out.println("==========================================");

        } else {

            System.out.println("==========================================");
            System.out.println("       ISLAND APPROVAL FAILED!");
            System.out.println("==========================================");
        }
    }
}