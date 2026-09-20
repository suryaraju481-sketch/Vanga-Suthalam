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

@WebServlet("/api/safety-verification")
public class SafetyVerificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doOptions(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
            "{\"success\":true,\"message\":\"Safety Verification API is working. Use POST to verify safety.\"}"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            // --------------------------------
            // 1. Read booking ID
            // --------------------------------

            String bookingIdParam = request.getParameter("bookingId");

            if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Booking ID is required.\"}"
                );

                return;
            }

            int bookingId = Integer.parseInt(bookingIdParam);


            // --------------------------------
            // 2. Read safety check values
            // --------------------------------

            boolean captainApproved =
                    Boolean.parseBoolean(
                            request.getParameter("captainApproved")
                    );

            boolean boatAvailable =
                    Boolean.parseBoolean(
                            request.getParameter("boatAvailable")
                    );

            boolean passengerCapacityOk =
                    Boolean.parseBoolean(
                            request.getParameter("passengerCapacityOk")
                    );

            boolean lifeJacketsAvailable =
                    Boolean.parseBoolean(
                            request.getParameter("lifeJacketsAvailable")
                    );

            boolean emergencyEquipmentAvailable =
                    Boolean.parseBoolean(
                            request.getParameter("emergencyEquipmentAvailable")
                    );

            boolean communicationEquipmentAvailable =
                    Boolean.parseBoolean(
                            request.getParameter("communicationEquipmentAvailable")
                    );

            boolean weatherClearance =
                    Boolean.parseBoolean(
                            request.getParameter("weatherClearance")
                    );

            String checkedBy = request.getParameter("checkedBy");


            // --------------------------------
            // 3. Validate checkedBy
            // --------------------------------

            if (checkedBy == null || checkedBy.trim().isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                response.getWriter().write(
                    "{\"success\":false,\"message\":\"Checked by is required.\"}"
                );

                return;
            }


            // --------------------------------
            // 4. Check booking exists
            // --------------------------------

            String bookingSql =
                    "SELECT booking_id, boat_id, number_of_people, booking_status " +
                    "FROM bookings " +
                    "WHERE booking_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(bookingSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Booking not found.\"}"
                        );

                        return;
                    }

                    String bookingStatus =
                            rs.getString("booking_status");

                    Integer boatId =
                            (Integer) rs.getObject("boat_id");

                    int numberOfPeople =
                            rs.getInt("number_of_people");


                    // --------------------------------
                    // 5. Check booking status
                    // --------------------------------

                    if ("CANCELLED".equalsIgnoreCase(bookingStatus)) {

                        response.setStatus(
                                HttpServletResponse.SC_BAD_REQUEST
                        );

                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Cancelled booking cannot pass safety verification.\"}"
                        );

                        return;
                    }


                    // --------------------------------
                    // 6. Boat assignment check
                    // --------------------------------

                    if (boatId == null) {

                        response.setStatus(
                                HttpServletResponse.SC_BAD_REQUEST
                        );

                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Boat must be assigned before safety verification.\"}"
                        );

                        return;
                    }


                    // --------------------------------
                    // 7. Passenger capacity check
                    // --------------------------------

                    String capacitySql =
                            "SELECT capacity " +
                            "FROM boats " +
                            "WHERE boat_id = ?";

                    try (PreparedStatement capacityPs =
                                 con.prepareStatement(capacitySql)) {

                        capacityPs.setInt(1, boatId);

                        try (ResultSet capacityRs =
                                     capacityPs.executeQuery()) {

                            if (!capacityRs.next()) {

                                response.setStatus(
                                        HttpServletResponse.SC_BAD_REQUEST
                                );

                                response.getWriter().write(
                                    "{\"success\":false,\"message\":\"Assigned boat was not found.\"}"
                                );

                                return;
                            }

                            int boatCapacity =
                                    capacityRs.getInt("capacity");

                            // Automatically make this check false
                            // if passenger count exceeds boat capacity.

                            if (numberOfPeople > boatCapacity) {

                                passengerCapacityOk = false;
                            }
                        }
                    }
                }
            }


            // --------------------------------
            // 8. Calculate final safety status
            // --------------------------------

            boolean allChecksPassed =
                    captainApproved
                    && boatAvailable
                    && passengerCapacityOk
                    && lifeJacketsAvailable
                    && emergencyEquipmentAvailable
                    && communicationEquipmentAvailable
                    && weatherClearance;

            String safetyStatus =
                    allChecksPassed ? "APPROVED" : "REJECTED";


            // --------------------------------
            // 9. Check existing safety record
            // --------------------------------

            String checkSql =
                    "SELECT safety_check_id " +
                    "FROM safety_checks " +
                    "WHERE booking_id = ?";

            Integer safetyCheckId = null;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(checkSql)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        safetyCheckId =
                                rs.getInt("safety_check_id");
                    }
                }
            }


            // --------------------------------
            // 10. Update existing record
            // --------------------------------

            if (safetyCheckId != null) {

                String updateSql =
                        "UPDATE safety_checks SET " +
                        "captain_approved = ?, " +
                        "boat_available = ?, " +
                        "passenger_capacity_ok = ?, " +
                        "life_jackets_available = ?, " +
                        "emergency_equipment_available = ?, " +
                        "communication_equipment_available = ?, " +
                        "weather_clearance = ?, " +
                        "safety_status = ?, " +
                        "checked_by = ?, " +
                        "checked_date = ? " +
                        "WHERE safety_check_id = ?";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps =
                             con.prepareStatement(updateSql)) {

                    ps.setBoolean(1, captainApproved);
                    ps.setBoolean(2, boatAvailable);
                    ps.setBoolean(3, passengerCapacityOk);
                    ps.setBoolean(4, lifeJacketsAvailable);
                    ps.setBoolean(5, emergencyEquipmentAvailable);
                    ps.setBoolean(6, communicationEquipmentAvailable);
                    ps.setBoolean(7, weatherClearance);
                    ps.setString(8, safetyStatus);
                    ps.setString(9, checkedBy);
                    ps.setTimestamp(
                            10,
                            new Timestamp(System.currentTimeMillis())
                    );
                    ps.setInt(11, safetyCheckId);

                    ps.executeUpdate();
                }

            }

            // --------------------------------
            // 11. Insert new record
            // --------------------------------

            else {

                String insertSql =
                        "INSERT INTO safety_checks (" +
                        "booking_id, " +
                        "captain_approved, " +
                        "boat_available, " +
                        "passenger_capacity_ok, " +
                        "life_jackets_available, " +
                        "emergency_equipment_available, " +
                        "communication_equipment_available, " +
                        "weather_clearance, " +
                        "safety_status, " +
                        "checked_by, " +
                        "checked_date" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps =
                             con.prepareStatement(
                                     insertSql,
                                     PreparedStatement.RETURN_GENERATED_KEYS
                             )) {

                    ps.setInt(1, bookingId);
                    ps.setBoolean(2, captainApproved);
                    ps.setBoolean(3, boatAvailable);
                    ps.setBoolean(4, passengerCapacityOk);
                    ps.setBoolean(5, lifeJacketsAvailable);
                    ps.setBoolean(6, emergencyEquipmentAvailable);
                    ps.setBoolean(7, communicationEquipmentAvailable);
                    ps.setBoolean(8, weatherClearance);
                    ps.setString(9, safetyStatus);
                    ps.setString(10, checkedBy);
                    ps.setTimestamp(
                            11,
                            new Timestamp(System.currentTimeMillis())
                    );

                    ps.executeUpdate();

                    try (ResultSet keys =
                                 ps.getGeneratedKeys()) {

                        if (keys.next()) {

                            safetyCheckId =
                                    keys.getInt(1);
                        }
                    }
                }
            }


            // --------------------------------
            // 12. Return response
            // --------------------------------

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                "{"
                + "\"success\":true,"
                + "\"message\":\"Safety verification completed successfully.\","
                + "\"bookingId\":" + bookingId + ","
                + "\"safetyCheckId\":" + safetyCheckId + ","
                + "\"safetyStatus\":\"" + safetyStatus + "\","
                + "\"allChecksPassed\":" + allChecksPassed
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

            response.getWriter().write(
                "{"
                + "\"success\":false,"
                + "\"message\":\"Safety verification failed: "
                + e.getMessage().replace("\"", "'")
                + "\""
                + "}"
            );
        }
    }
}