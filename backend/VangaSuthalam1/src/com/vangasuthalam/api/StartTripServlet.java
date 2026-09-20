package com.vangasuthalam.api;

import com.vangasuthalam.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

@WebServlet("/api/start-trip")
public class StartTripServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
            "{\"success\":true,\"message\":\"Start Trip API is working. Use POST to start a trip.\"}"
        );
    }

    @Override
    protected void doOptions(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader(
            "Access-Control-Allow-Origin",
            "http://localhost:5173"
        );

        response.setHeader(
            "Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS"
        );

        response.setHeader(
            "Access-Control-Allow-Headers",
            "Content-Type"
        );

        response.setHeader(
            "Access-Control-Allow-Credentials",
            "true"
        );

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            // ---------------------------------------
            // 1. Get Booking ID
            // ---------------------------------------

            String bookingIdParam =
                    request.getParameter("bookingId");

            if (bookingIdParam == null ||
                bookingIdParam.trim().isEmpty()) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Booking ID is required.\"}"
                );

                return;
            }

            int bookingId =
                    Integer.parseInt(bookingIdParam);


            // ---------------------------------------
            // 2. Find Trip
            // ---------------------------------------

            String tripSql =
                    "SELECT trip_id, captain_id, boat_id, " +
                    "trip_status " +
                    "FROM trips " +
                    "WHERE booking_id = ? " +
                    "ORDER BY trip_id DESC " +
                    "LIMIT 1";

            int tripId;
            int captainId;
            int boatId;
            String tripStatus;

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(tripSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (!rs.next()) {

                        response.setStatus(
                            HttpServletResponse.SC_BAD_REQUEST
                        );

                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Trip not found. Please assign a captain and boat first.\"}"
                        );

                        return;
                    }

                    tripId =
                        rs.getInt("trip_id");

                    captainId =
                        rs.getInt("captain_id");

                    boatId =
                        rs.getInt("boat_id");

                    tripStatus =
                        rs.getString("trip_status");
                }
            }


            // ---------------------------------------
            // 3. Check Captain + Boat
            // ---------------------------------------

            if (captainId <= 0 || boatId <= 0) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Captain and boat must be assigned before starting the trip.\"}"
                );

                return;
            }


            // ---------------------------------------
            // 4. Check Payment
            // ---------------------------------------

            String paymentSql =
                    "SELECT payment_status " +
                    "FROM payments " +
                    "WHERE booking_id = ? " +
                    "ORDER BY payment_id DESC " +
                    "LIMIT 1";

            String paymentStatus = null;

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(paymentSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (rs.next()) {

                        paymentStatus =
                            rs.getString("payment_status");
                    }
                }
            }


            if (paymentStatus == null ||
                !"CONFIRMED".equalsIgnoreCase(paymentStatus)) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Payment must be confirmed before starting the trip.\"}"
                );

                return;
            }


            // ---------------------------------------
            // 5. Check Safety
            // ---------------------------------------

            String safetySql =
                    "SELECT safety_status " +
                    "FROM safety_checks " +
                    "WHERE booking_id = ? " +
                    "ORDER BY safety_check_id DESC " +
                    "LIMIT 1";

            String safetyStatus = null;

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(safetySql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (rs.next()) {

                        safetyStatus =
                            rs.getString("safety_status");
                    }
                }
            }


            if (safetyStatus == null ||
                !"APPROVED".equalsIgnoreCase(safetyStatus)) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Safety verification must be APPROVED before starting the trip.\"}"
                );

                return;
            }


            // ---------------------------------------
            // 6. Check Trip Status
            // ---------------------------------------

            if ("IN_PROGRESS".equalsIgnoreCase(tripStatus)) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Trip has already started.\"}"
                );

                return;
            }


            if ("COMPLETED".equalsIgnoreCase(tripStatus)) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Trip is already completed.\"}"
                );

                return;
            }


            // ---------------------------------------
            // 7. Start Trip
            // ---------------------------------------

            String updateSql =
                    "UPDATE trips SET " +
                    "trip_status = 'IN_PROGRESS', " +
                    "trip_start_datetime = ? " +
                    "WHERE trip_id = ?";

            Timestamp startTime =
                    new Timestamp(
                        System.currentTimeMillis()
                    );

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(updateSql)) {

                ps.setTimestamp(1, startTime);
                ps.setInt(2, tripId);

                int updated =
                        ps.executeUpdate();

                if (updated == 0) {

                    response.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                    );

                    response.getWriter().write(
                        "{\"success\":false,\"message\":\"Unable to start trip.\"}"
                    );

                    return;
                }
            }


            // ---------------------------------------
            // 8. Update Booking Status
            // ---------------------------------------

            String bookingUpdate =
                    "UPDATE bookings SET " +
                    "booking_status = 'IN_PROGRESS' " +
                    "WHERE booking_id = ?";

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(bookingUpdate)) {

                ps.setInt(1, bookingId);

                ps.executeUpdate();
            }


            // ---------------------------------------
            // 9. Success Response
            // ---------------------------------------

            response.setStatus(
                HttpServletResponse.SC_OK
            );

            response.getWriter().write(
                "{"
                + "\"success\":true,"
                + "\"message\":\"Trip started successfully.\","
                + "\"tripId\":" + tripId + ","
                + "\"bookingId\":" + bookingId + ","
                + "\"captainId\":" + captainId + ","
                + "\"boatId\":" + boatId + ","
                + "\"tripStatus\":\"IN_PROGRESS\","
                + "\"tripStart\":\"" + startTime + "\""
                + "}"
            );

        } catch (NumberFormatException e) {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Booking ID must be a valid number.\"}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            String error =
                    e.getMessage() == null
                    ? "Unknown server error"
                    : e.getMessage()
                        .replace("\"", "'");

            response.getWriter().write(
                "{"
                + "\"success\":false,"
                + "\"message\":\"Start Trip failed: "
                + error
                + "\""
                + "}"
            );
        }
    }
}