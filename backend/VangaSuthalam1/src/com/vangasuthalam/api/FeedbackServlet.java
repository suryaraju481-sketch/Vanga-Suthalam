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

@WebServlet("/api/feedback")
public class FeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // GET - API TEST
    // =========================================================

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
                "message": "Feedback API is working. Use POST to submit feedback."
            }
            """);

        out.flush();
    }

    // =========================================================
    // POST - SUBMIT FEEDBACK
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        Connection con = null;

        try {

            // -------------------------------------------------
            // 1. Read parameters
            // -------------------------------------------------

            String bookingIdParam =
                    request.getParameter("bookingId");

            String customerIdParam =
                    request.getParameter("customerId");

            String captainRatingParam =
                    request.getParameter("captainRating");

            String boatRatingParam =
                    request.getParameter("boatRating");

            String foodRatingParam =
                    request.getParameter("foodRating");

            String safetyRatingParam =
                    request.getParameter("safetyRating");

            String experienceRatingParam =
                    request.getParameter("experienceRating");

            String overallRatingParam =
                    request.getParameter("overallRating");

            String comments =
                    request.getParameter("comments");


            // -------------------------------------------------
            // 2. Check required values
            // -------------------------------------------------

            if (bookingIdParam == null ||
                customerIdParam == null ||
                captainRatingParam == null ||
                boatRatingParam == null ||
                foodRatingParam == null ||
                safetyRatingParam == null ||
                experienceRatingParam == null ||
                overallRatingParam == null) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                out.print("""
                    {
                        "success": false,
                        "message": "Required feedback information is missing."
                    }
                    """);

                return;
            }


            // -------------------------------------------------
            // 3. Convert numeric values
            // -------------------------------------------------

            int bookingId =
                    Integer.parseInt(bookingIdParam);

            int customerId =
                    Integer.parseInt(customerIdParam);

            int captainRating =
                    Integer.parseInt(captainRatingParam);

            int boatRating =
                    Integer.parseInt(boatRatingParam);

            int foodRating =
                    Integer.parseInt(foodRatingParam);

            int safetyRating =
                    Integer.parseInt(safetyRatingParam);

            int experienceRating =
                    Integer.parseInt(experienceRatingParam);

            int overallRating =
                    Integer.parseInt(overallRatingParam);


            // -------------------------------------------------
            // 4. Validate ratings
            // -------------------------------------------------

            if (captainRating < 1 || captainRating > 5 ||
                boatRating < 1 || boatRating > 5 ||
                foodRating < 1 || foodRating > 5 ||
                safetyRating < 1 || safetyRating > 5 ||
                experienceRating < 1 || experienceRating > 5 ||
                overallRating < 1 || overallRating > 5) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                out.print("""
                    {
                        "success": false,
                        "message": "All ratings must be between 1 and 5."
                    }
                    """);

                return;
            }


            // -------------------------------------------------
            // 5. Comments validation
            // -------------------------------------------------

            if (comments == null) {
                comments = "";
            }

            comments = comments.trim();

            if (comments.length() > 500) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                out.print("""
                    {
                        "success": false,
                        "message": "Comments cannot exceed 500 characters."
                    }
                    """);

                return;
            }


            // -------------------------------------------------
            // 6. Database connection
            // -------------------------------------------------

            con = DBConnection.getConnection();

            if (con == null) {

                response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );

                out.print("""
                    {
                        "success": false,
                        "message": "Database connection failed."
                    }
                    """);

                return;
            }


            // -------------------------------------------------
            // 7. Check booking
            // -------------------------------------------------

            String bookingSQL =
                """
                SELECT booking_id,
                       customer_id,
                       booking_status
                FROM bookings
                WHERE booking_id = ?
                """;

            int bookingCustomerId;
            String bookingStatus;

            try (PreparedStatement ps =
                    con.prepareStatement(bookingSQL)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {

                        response.setStatus(
                            HttpServletResponse.SC_NOT_FOUND
                        );

                        out.print("""
                            {
                                "success": false,
                                "message": "Booking not found."
                            }
                            """);

                        return;
                    }

                    bookingCustomerId =
                            rs.getInt("customer_id");

                    bookingStatus =
                            rs.getString("booking_status");
                }
            }


            // -------------------------------------------------
            // 8. Check customer owns booking
            // -------------------------------------------------

            if (bookingCustomerId != customerId) {

                response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
                );

                out.print("""
                    {
                        "success": false,
                        "message": "This booking does not belong to the selected customer."
                    }
                    """);

                return;
            }


            // -------------------------------------------------
            // 9. Feedback only after completed trip
            // -------------------------------------------------

            if (!"COMPLETED".equalsIgnoreCase(bookingStatus)) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                out.print(
                    "{"
                    + "\"success\":false,"
                    + "\"message\":\"Feedback can be submitted only after the trip is completed.\""
                    + "}"
                );

                return;
            }


            // -------------------------------------------------
            // 10. Check duplicate feedback
            // -------------------------------------------------

            String duplicateSQL =
                """
                SELECT feedback_id
                FROM feedback
                WHERE booking_id = ?
                """;

            try (PreparedStatement ps =
                    con.prepareStatement(duplicateSQL)) {

                ps.setInt(1, bookingId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        response.setStatus(
                            HttpServletResponse.SC_BAD_REQUEST
                        );

                        out.print("""
                            {
                                "success": false,
                                "message": "Feedback has already been submitted for this booking."
                            }
                            """);

                        return;
                    }
                }
            }


            // -------------------------------------------------
            // 11. INSERT FEEDBACK
            // -------------------------------------------------

            String insertSQL =
                """
                INSERT INTO feedback
                (
                    booking_id,
                    customer_id,
                    captain_rating,
                    boat_rating,
                    food_rating,
                    safety_rating,
                    experience_rating,
                    overall_rating,
                    comments
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;


            int generatedFeedbackId = 0;

            try (PreparedStatement ps =
                    con.prepareStatement(
                        insertSQL,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                    )) {

                ps.setInt(1, bookingId);
                ps.setInt(2, customerId);
                ps.setInt(3, captainRating);
                ps.setInt(4, boatRating);
                ps.setInt(5, foodRating);
                ps.setInt(6, safetyRating);
                ps.setInt(7, experienceRating);
                ps.setInt(8, overallRating);
                ps.setString(9, comments);

                int rowsInserted =
                        ps.executeUpdate();

                if (rowsInserted == 0) {

                    response.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                    );

                    out.print("""
                        {
                            "success": false,
                            "message": "Feedback was not inserted."
                        }
                        """);

                    return;
                }


                // ---------------------------------------------
                // Get generated feedback ID
                // ---------------------------------------------

                try (ResultSet keys =
                        ps.getGeneratedKeys()) {

                    if (keys.next()) {

                        generatedFeedbackId =
                                keys.getInt(1);
                    }
                }
            }


            // -------------------------------------------------
            // 12. SUCCESS
            // -------------------------------------------------

            response.setStatus(
                HttpServletResponse.SC_OK
            );

            out.print(
                "{"
                + "\"success\":true,"
                + "\"message\":\"Feedback submitted successfully. Thank you!\","
                + "\"feedbackId\":" + generatedFeedbackId + ","
                + "\"bookingId\":" + bookingId + ","
                + "\"customerId\":" + customerId
                + "}"
            );


        } catch (NumberFormatException e) {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );

            out.print("""
                {
                    "success": false,
                    "message": "Invalid numeric value in feedback request."
                }
                """);


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            String error =
                    e.getMessage();

            if (error == null) {
                error = "Unknown database error.";
            }

            error = error
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", " ")
                    .replace("\r", " ");

            out.print(
                "{"
                + "\"success\":false,"
                + "\"message\":\"Database error: "
                + error
                + "\""
                + "}"
            );

        } finally {

            if (con != null) {

                try {
                    con.close();
                } catch (Exception ignored) {
                }
            }
        }

        out.flush();
    }
}