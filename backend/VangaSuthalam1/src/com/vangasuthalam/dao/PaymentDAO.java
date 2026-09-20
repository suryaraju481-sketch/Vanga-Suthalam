package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.model.Payment;
import com.vangasuthalam.util.DBConnection;

public class PaymentDAO {

    public boolean addPayment(Payment payment) {

        try (Connection con = DBConnection.getConnection()) {

            // ==========================================
            // GET BOOKING DETAILS
            // ==========================================

            String bookingSql =
                    "SELECT b.total_amount, "
                  + "b.booking_status, "
                  + "d.destination_type "
                  + "FROM bookings b "
                  + "JOIN destinations d "
                  + "ON b.destination_id = d.destination_id "
                  + "WHERE b.booking_id = ?";

            double bookingAmount = 0;
            String bookingStatus = "";
            String destinationType = "";

            try (PreparedStatement ps =
                    con.prepareStatement(bookingSql)) {

                ps.setInt(1, payment.getBookingId());

                try (ResultSet rs =
                        ps.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println();
                        System.out.println("Booking not found.");

                        return false;
                    }

                    bookingAmount =
                            rs.getDouble("total_amount");

                    bookingStatus =
                            rs.getString("booking_status");

                    destinationType =
                            rs.getString("destination_type");
                }
            }

            // ==========================================
            // CHECK BOOKING STATUS
            // ==========================================

            if ("CONFIRMED".equalsIgnoreCase(
                    bookingStatus)) {

                System.out.println();
                System.out.println(
                        "Payment already completed for this booking."
                );

                return false;
            }

            // ==========================================
            // CHECK PAYMENT AMOUNT
            // ==========================================

            if (Math.abs(
                    payment.getAmount() - bookingAmount
                ) > 0.01) {

                System.out.println();
                System.out.println("==========================================");
                System.out.println("        PAYMENT AMOUNT INCORRECT");
                System.out.println("==========================================");

                System.out.println(
                        "Booking Amount : ₹"
                        + bookingAmount
                );

                System.out.println(
                        "Payment Amount : ₹"
                        + payment.getAmount()
                );

                System.out.println("------------------------------------------");

                System.out.println(
                        "Please enter the correct amount."
                );

                System.out.println("==========================================");

                return false;
            }

            // ==========================================
            // ISLAND APPROVAL CHECK
            // ==========================================

            if ("Island Explorer".equalsIgnoreCase(
                    destinationType)) {

                String islandSql =
                        "SELECT approval_id "
                      + "FROM island_approvals "
                      + "WHERE booking_id = ? "
                      + "AND approval_status = 'APPROVED'";

                try (PreparedStatement islandPs =
                        con.prepareStatement(islandSql)) {

                    islandPs.setInt(
                            1,
                            payment.getBookingId()
                    );

                    try (ResultSet islandRs =
                            islandPs.executeQuery()) {

                        if (!islandRs.next()) {

                            System.out.println();
                            System.out.println(
                                    "=========================================="
                            );

                            System.out.println(
                                    "       ISLAND APPROVAL REQUIRED"
                            );

                            System.out.println(
                                    "=========================================="
                            );

                            System.out.println(
                                    "Payment cannot be completed."
                            );

                            System.out.println(
                                    "Island approval is not APPROVED."
                            );

                            System.out.println(
                                    "=========================================="
                            );

                            return false;
                        }
                    }
                }
            }

            // ==========================================
            // INSERT PAYMENT
            // ==========================================

            String paymentSql =
                    "INSERT INTO payments "
                  + "(booking_id, amount, payment_method, "
                  + "transaction_reference, payment_status) "
                  + "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps =
                    con.prepareStatement(paymentSql)) {

                ps.setInt(
                        1,
                        payment.getBookingId()
                );

                ps.setDouble(
                        2,
                        payment.getAmount()
                );

                ps.setString(
                        3,
                        payment.getPaymentMethod()
                );

                ps.setString(
                        4,
                        payment.getTransactionReference()
                );

                ps.setString(
                        5,
                        payment.getPaymentStatus()
                );

                int rows =
                        ps.executeUpdate();

                if (rows > 0) {

                    // ==========================================
                    // CONFIRM BOOKING
                    // ==========================================

                    String updateSql =
                            "UPDATE bookings "
                          + "SET booking_status = 'CONFIRMED' "
                          + "WHERE booking_id = ?";

                    try (PreparedStatement updatePs =
                            con.prepareStatement(updateSql)) {

                        updatePs.setInt(
                                1,
                                payment.getBookingId()
                        );

                        updatePs.executeUpdate();
                    }

                    return true;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}