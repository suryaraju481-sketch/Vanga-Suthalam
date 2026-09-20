package com.vangasuthalam.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/assign-boat")
public class BoatAssignmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String bookingIdParam = request.getParameter("bookingId");
        String boatIdParam = request.getParameter("boatId");

        if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {
            out.print("""
                {
                    "success": false,
                    "message": "Booking ID is required."
                }
                """);
            return;
        }

        if (boatIdParam == null || boatIdParam.trim().isEmpty()) {
            out.print("""
                {
                    "success": false,
                    "message": "Boat ID is required."
                }
                """);
            return;
        }

        int bookingId;
        int boatId;

        try {
            bookingId = Integer.parseInt(bookingIdParam);
            boatId = Integer.parseInt(boatIdParam);
        } catch (NumberFormatException e) {

            out.print("""
                {
                    "success": false,
                    "message": "Booking ID and Boat ID must be numbers."
                }
                """);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            // 1. Get booking passenger count
            String bookingSql =
                    "SELECT number_of_people, booking_status " +
                    "FROM bookings " +
                    "WHERE booking_id = ?";

            int numberOfPeople;
            String bookingStatus;

            try (PreparedStatement ps = con.prepareStatement(bookingSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        out.print("""
                            {
                                "success": false,
                                "message": "Booking not found."
                            }
                            """);
                        return;
                    }

                    numberOfPeople = rs.getInt("number_of_people");
                    bookingStatus = rs.getString("booking_status");
                }
            }

            // 2. Check booking status
            if ("CANCELLED".equalsIgnoreCase(bookingStatus)) {

                out.print("""
                    {
                        "success": false,
                        "message": "Cancelled booking cannot be assigned."
                    }
                    """);
                return;
            }

            // 3. Get boat information
            String boatSql =
                    "SELECT b.boat_id, b.boat_name, b.capacity, " +
                    "b.status, b.captain_id, c.name AS captain_name " +
                    "FROM boats b " +
                    "JOIN captains c ON b.captain_id = c.captain_id " +
                    "WHERE b.boat_id = ?";

            int capacity;
            String boatName;
            String boatStatus;
            int captainId;
            String captainName;

            try (PreparedStatement ps = con.prepareStatement(boatSql)) {

                ps.setInt(1, boatId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        out.print("""
                            {
                                "success": false,
                                "message": "Boat not found."
                            }
                            """);
                        return;
                    }

                    boatName = rs.getString("boat_name");
                    capacity = rs.getInt("capacity");
                    boatStatus = rs.getString("status");
                    captainId = rs.getInt("captain_id");
                    captainName = rs.getString("captain_name");
                }
            }

            // 4. Check boat availability
            if (!"AVAILABLE".equalsIgnoreCase(boatStatus)) {

                out.print("""
                    {
                        "success": false,
                        "message": "Selected boat is not available."
                    }
                    """);
                return;
            }

            // 5. Check passenger capacity
            if (numberOfPeople > capacity) {

                out.print("""
                    {
                        "success": false,
                        "message": "Boat capacity is insufficient for this booking."
                    }
                    """);
                return;
            }

            // 6. Assign boat to booking
            String updateBooking =
                    "UPDATE bookings " +
                    "SET boat_id = ? " +
                    "WHERE booking_id = ?";

            try (PreparedStatement ps = con.prepareStatement(updateBooking)) {

                ps.setInt(1, boatId);
                ps.setInt(2, bookingId);

                ps.executeUpdate();
            }

            // 7. Create trip record
            String tripSql =
                    "INSERT INTO trips " +
                    "(booking_id, captain_id, boat_id, trip_status) " +
                    "VALUES (?, ?, ?, 'BOOKED')";

            int tripId;

            try (PreparedStatement ps = con.prepareStatement(
                    tripSql,
                    java.sql.Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, bookingId);
                ps.setInt(2, captainId);
                ps.setInt(3, boatId);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        tripId = rs.getInt(1);
                    } else {
                        tripId = 0;
                    }
                }
            }

            // 8. Change boat status
            String updateBoat =
                    "UPDATE boats " +
                    "SET status = 'ASSIGNED' " +
                    "WHERE boat_id = ?";

            try (PreparedStatement ps = con.prepareStatement(updateBoat)) {

                ps.setInt(1, boatId);
                ps.executeUpdate();
            }

            // 9. Return success
            out.print("""
                {
                    "success": true,
                    "message": "Captain and boat assigned successfully.",
                    "bookingId": %d,
                    "tripId": %d,
                    "boatId": %d,
                    "boatName": "%s",
                    "captainId": %d,
                    "captainName": "%s",
                    "passengers": %d,
                    "capacity": %d
                }
                """.formatted(
                    bookingId,
                    tripId,
                    boatId,
                    boatName,
                    captainId,
                    captainName,
                    numberOfPeople,
                    capacity
                ));

        } catch (Exception e) {

            e.printStackTrace();

            out.print("""
                {
                    "success": false,
                    "message": "Server error while assigning boat."
                }
                """);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print("""
            {
                "success": true,
                "message": "Boat Assignment API is working. Use POST to assign a boat."
            }
            """);
    }
}