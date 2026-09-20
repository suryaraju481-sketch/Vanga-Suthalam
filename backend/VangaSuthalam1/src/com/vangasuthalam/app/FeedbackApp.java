package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.FeedbackController;
import com.vangasuthalam.model.Feedback;

public class FeedbackApp {

    public static void start(Scanner scanner, int customerId) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             GIVE FEEDBACK");
        System.out.println("==========================================");

        System.out.println("Customer ID: " + customerId);

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();

        System.out.print("Captain Rating (1-5): ");
        int captainRating = scanner.nextInt();

        System.out.print("Boat Rating (1-5): ");
        int boatRating = scanner.nextInt();

        System.out.print("Food Rating (1-5): ");
        int foodRating = scanner.nextInt();

        System.out.print("Safety Rating (1-5): ");
        int safetyRating = scanner.nextInt();

        System.out.print("Experience Rating (1-5): ");
        int experienceRating = scanner.nextInt();

        System.out.print("Overall Rating (1-5): ");
        int overallRating = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Comments: ");
        String comments = scanner.nextLine();

        Feedback feedback = new Feedback();

        feedback.setBookingId(bookingId);
        feedback.setCustomerId(customerId);
        feedback.setCaptainRating(captainRating);
        feedback.setBoatRating(boatRating);
        feedback.setFoodRating(foodRating);
        feedback.setSafetyRating(safetyRating);
        feedback.setExperienceRating(experienceRating);
        feedback.setOverallRating(overallRating);
        feedback.setComments(comments);

        FeedbackController controller =
                new FeedbackController();

        boolean result = controller.addFeedback(feedback);

        System.out.println();

        if (result) {

            System.out.println("==========================================");
            System.out.println("       FEEDBACK ADDED SUCCESSFULLY!");
            System.out.println("==========================================");

        } else {

            System.out.println("==========================================");
            System.out.println("          FEEDBACK FAILED!");
            System.out.println("==========================================");
        }
    }
}