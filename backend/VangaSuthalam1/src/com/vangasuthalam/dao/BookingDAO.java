package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.vangasuthalam.model.Booking;
import com.vangasuthalam.util.DBConnection;

public class BookingDAO {

    // ==========================================
    // CREATE BOOKING
    // ==========================================

    public int addBooking(Booking booking) {

        String sql =
                "INSERT INTO bookings "
                + "(customer_id, destination_id, package_id, "
                + "boat_id, booking_date, start_time, "
                + "number_of_people, fishing_required, "
                + "food_required, total_amount, approval_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            // Customer
            ps.setInt(
                    1,
                    booking.getCustomerId()
            );

            // Destination
            ps.setInt(
                    2,
                    booking.getDestinationId()
            );

            // Package
            ps.setInt(
                    3,
                    booking.getPackageId()
            );

            // Boat
            /*
             * Boat is nullable at booking creation.
             *
             * 0 means no boat assigned yet.
             */
            if (booking.getBoatId() > 0) {

                ps.setInt(
                        4,
                        booking.getBoatId()
                );

            } else {

                ps.setNull(
                        4,
                        java.sql.Types.INTEGER
                );
            }

            // Booking date
            ps.setDate(
                    5,
                    booking.getBookingDate()
            );

            // Start time
            ps.setTime(
                    6,
                    booking.getStartTime()
            );

            // Number of people
            ps.setInt(
                    7,
                    booking.getNumberOfPeople()
            );

            // Fishing
            ps.setBoolean(
                    8,
                    booking.isFishingRequired()
            );

            // Food
            ps.setBoolean(
                    9,
                    booking.isFoodRequired()
            );

            // Total amount
            ps.setDouble(
                    10,
                    booking.getTotalAmount()
            );

            // Island approval
            if (booking.getApprovalId() != null) {

                ps.setInt(
                        11,
                        booking.getApprovalId()
                );

            } else {

                ps.setNull(
                        11,
                        java.sql.Types.INTEGER
                );
            }

            // Execute
            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs =
                             ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }


    // ==========================================
    // VIEW MY BOOKINGS
    // ==========================================

    public void viewMyBookings(int customerId) {

        String sql =
                "SELECT b.booking_id, "
                + "d.destination_name, "
                + "p.package_name, "
                + "b.booking_date, "
                + "b.start_time, "
                + "b.number_of_people, "
                + "b.fishing_required, "
                + "b.food_required, "
                + "b.total_amount, "
                + "b.booking_status, "
                + "b.approval_id "
                + "FROM bookings b "
                + "JOIN destinations d "
                + "ON b.destination_id = d.destination_id "
                + "JOIN trip_packages p "
                + "ON b.package_id = p.package_id "
                + "WHERE b.customer_id = ? "
                + "ORDER BY b.booking_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    customerId
            );

            try (ResultSet rs =
                         ps.executeQuery()) {

                System.out.println();
                System.out.println(
                        "=========================================="
                );

                System.out.println(
                        "              MY BOOKINGS"
                );

                System.out.println(
                        "=========================================="
                );

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println(
                            "Booking ID       : "
                            + rs.getInt("booking_id")
                    );

                    System.out.println(
                            "Destination      : "
                            + rs.getString(
                                    "destination_name")
                    );

                    System.out.println(
                            "Package          : "
                            + rs.getString(
                                    "package_name")
                    );

                    System.out.println(
                            "Booking Date     : "
                            + rs.getDate(
                                    "booking_date")
                    );

                    System.out.println(
                            "Start Time       : "
                            + rs.getTime(
                                    "start_time")
                    );

                    System.out.println(
                            "Number of People : "
                            + rs.getInt(
                                    "number_of_people")
                    );

                    System.out.println(
                            "Fishing Required : "
                            + rs.getBoolean(
                                    "fishing_required")
                    );

                    System.out.println(
                            "Food Required    : "
                            + rs.getBoolean(
                                    "food_required")
                    );

                    System.out.println(
                            "Total Amount     : ₹"
                            + rs.getDouble(
                                    "total_amount")
                    );

                    System.out.println(
                            "Booking Status   : "
                            + rs.getString(
                                    "booking_status")
                    );

                    int approvalId =
                            rs.getInt("approval_id");

                    if (!rs.wasNull()) {

                        System.out.println(
                                "Island Approval  : "
                                + approvalId
                        );
                    }

                    System.out.println(
                            "------------------------------------------"
                    );
                }

                if (!found) {

                    System.out.println(
                            "No bookings found."
                    );
                }

                System.out.println(
                        "=========================================="
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}