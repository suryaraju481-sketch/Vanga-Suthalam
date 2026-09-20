package com.vangasuthalam.app;

import java.util.Scanner;

import com.vangasuthalam.controller.PaymentController;
import com.vangasuthalam.model.Payment;

public class PaymentApp {

    public static void start(Scanner scanner) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             MAKE PAYMENT");
        System.out.println("==========================================");

        // ==========================================
        // BOOKING ID
        // ==========================================

        System.out.print("Enter Booking ID: ");

        int bookingId =
                scanner.nextInt();

        if (bookingId <= 0) {

            System.out.println(
                    "Invalid Booking ID."
            );

            return;
        }

        // ==========================================
        // PAYMENT AMOUNT
        // ==========================================

        System.out.print("Enter Payment Amount: ₹");

        double amount =
                scanner.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Invalid Payment Amount."
            );

            return;
        }

        // ==========================================
        // PAYMENT METHOD
        // ==========================================

        scanner.nextLine();

        System.out.print(
                "Enter Payment Method: "
        );

        String paymentMethod =
                scanner.nextLine();

        // ==========================================
        // TRANSACTION REFERENCE
        // ==========================================

        System.out.print(
                "Enter Transaction Reference: "
        );

        String transactionReference =
                scanner.nextLine();

        // ==========================================
        // CREATE PAYMENT
        // ==========================================

        Payment payment =
                new Payment();

        payment.setBookingId(
                bookingId
        );

        payment.setAmount(
                amount
        );

        payment.setPaymentMethod(
                paymentMethod
        );

        payment.setTransactionReference(
                transactionReference
        );

        payment.setPaymentStatus(
                "SUCCESS"
        );

        // ==========================================
        // SAVE PAYMENT
        // ==========================================

        PaymentController controller =
                new PaymentController();

        boolean result =
                controller.addPayment(payment);

        // ==========================================
        // RESULT
        // ==========================================

        System.out.println();

        if (result) {

            System.out.println("==========================================");
            System.out.println("          PAYMENT SUCCESSFUL!");
            System.out.println("==========================================");

            System.out.println(
                    "Booking ID       : "
                    + bookingId
            );

            System.out.println(
                    "Amount           : ₹"
                    + amount
            );

            System.out.println(
                    "Payment Method   : "
                    + paymentMethod
            );

            System.out.println(
                    "Transaction Ref  : "
                    + transactionReference
            );

            System.out.println(
                    "Payment Status   : SUCCESS"
            );

            System.out.println(
                    "Booking Status   : CONFIRMED"
            );

            System.out.println("==========================================");

        } else {

            System.out.println("==========================================");
            System.out.println("           PAYMENT FAILED!");
            System.out.println("==========================================");
        }
    }
}
