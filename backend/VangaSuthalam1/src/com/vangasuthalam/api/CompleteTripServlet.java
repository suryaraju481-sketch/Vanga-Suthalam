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

@WebServlet("/api/complete-trip")
public class CompleteTripServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==============================
    // GET - TEST API
    // ==============================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        out.print("""
        {
            "success": true,
            "message": "Complete Trip API is working. Use POST to complete a trip."
        }
        """);

        out.flush();
    }

    // ==============================
    // POST - COMPLETE TRIP
    // ==============================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String tripIdParameter = request.getParameter("tripId");

        if (tripIdParameter == null || tripIdParameter.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            out.print("""
            {
                "success": false,
                "message": "Trip ID is required."
            }
            """);

            return;
        }

        try {

            int tripId = Integer.parseInt(tripIdParameter);

            try (Connection con = DBConnection.getConnection()) {

                // ==========================================
                // 1. FIND TRIP
                // ==========================================

                String findTripSql = """
                    SELECT trip_id,
                           booking_id,
                           captain_id,
                           boat_id,
                           trip_status
                    FROM trips
                    WHERE trip_id = ?
                """;

                int bookingId;
                int captainId;
                int boatId;
                String tripStatus;

                try (PreparedStatement ps =
                        con.prepareStatement(findTripSql)) {

                    ps.setInt(1, tripId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {

                            response.setStatus(
                                HttpServletResponse.SC_NOT_FOUND
                            );

                            out.print("""
                            {
                                "success": false,
                                "message": "Trip not found."
                            }
                            """);

                            return;
                        }

                        bookingId = rs.getInt("booking_id");
                        captainId = rs.getInt("captain_id");
                        boatId = rs.getInt("boat_id");
                        tripStatus = rs.getString("trip_status");
                    }
                }

                // ==========================================
                // 2. CHECK TRIP STATUS
                // ==========================================

                if (!"IN_PROGRESS".equalsIgnoreCase(tripStatus)) {

                    response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST
                    );

                    out.print("""
                    {
                        "success": false,
                        "message": "Trip cannot be completed because it is not IN_PROGRESS."
                    }
                    """);

                    return;
                }

                // ==========================================
                // 3. UPDATE TRIP
                // ==========================================

                String updateTripSql = """
                    UPDATE trips
                    SET trip_status = 'COMPLETED',
                        trip_end_datetime = CURRENT_TIMESTAMP
                    WHERE trip_id = ?
                """;

                try (PreparedStatement ps =
                        con.prepareStatement(updateTripSql)) {

                    ps.setInt(1, tripId);
                    ps.executeUpdate();
                }

                // ==========================================
                // 4. UPDATE BOOKING
                // ==========================================

                String updateBookingSql = """
                    UPDATE bookings
                    SET booking_status = 'COMPLETED'
                    WHERE booking_id = ?
                """;

                try (PreparedStatement ps =
                        con.prepareStatement(updateBookingSql)) {

                    ps.setInt(1, bookingId);
                    ps.executeUpdate();
                }

                // ==========================================
                // 5. MAKE BOAT AVAILABLE AGAIN
                // ==========================================

                String updateBoatSql = """
                    UPDATE boats
                    SET status = 'AVAILABLE'
                    WHERE boat_id = ?
                """;

                try (PreparedStatement ps =
                        con.prepareStatement(updateBoatSql)) {

                    ps.setInt(1, boatId);
                    ps.executeUpdate();
                }

                // ==========================================
                // 6. SUCCESS RESPONSE
                // ==========================================

                response.setStatus(
                    HttpServletResponse.SC_OK
                );

                out.print("""
                {
                    "success": true,
                    "message": "Trip completed successfully.",
                    "tripId": %d,
                    "bookingId": %d,
                    "captainId": %d,
                    "boatId": %d,
                    "tripStatus": "COMPLETED"
                }
                """.formatted(
                    tripId,
                    bookingId,
                    captainId,
                    boatId
                ));
            }

        } catch (NumberFormatException e) {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );

            out.print("""
            {
                "success": false,
                "message": "Invalid Trip ID."
            }
            """);

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            out.print("""
            {
                "success": false,
                "message": "Server error while completing trip."
            }
            """);
        }

        out.flush();
    }
}