package com.vangasuthalam.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/bookings")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            // =====================================================
            // READ PARAMETERS
            // =====================================================

            String customerIdText =
                    request.getParameter("customerId");

            String destinationIdText =
                    request.getParameter("destinationId");

            String packageIdText =
                    request.getParameter("packageId");

            String bookingDate =
                    request.getParameter("bookingDate");

            String startTime =
                    request.getParameter("startTime");

            /*
             * IMPORTANT:
             * React sends "numberOfPeople".
             *
             * We also accept old names so the API remains compatible.
             */
            String numberOfPeopleText =
                    request.getParameter("numberOfPeople");

            if (numberOfPeopleText == null ||
                    numberOfPeopleText.trim().isEmpty()) {

                numberOfPeopleText =
                        request.getParameter("people");
            }

            if (numberOfPeopleText == null ||
                    numberOfPeopleText.trim().isEmpty()) {

                numberOfPeopleText =
                        request.getParameter("numberOfPersons");
            }

            String fishingRequiredText =
                    request.getParameter("fishingRequired");

            String foodRequiredText =
                    request.getParameter("foodRequired");

            String approvalIdText =
                    request.getParameter("approvalId");

            // =====================================================
            // BASIC VALIDATION
            // =====================================================

            if (customerIdText == null ||
                    customerIdText.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Customer ID is required."
                );
                return;
            }

            if (destinationIdText == null ||
                    destinationIdText.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Destination is required."
                );
                return;
            }

            if (packageIdText == null ||
                    packageIdText.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Package is required."
                );
                return;
            }

            if (bookingDate == null ||
                    bookingDate.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Booking date is required."
                );
                return;
            }

            if (startTime == null ||
                    startTime.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Start time is required."
                );
                return;
            }

            // =====================================================
            // NUMBER OF PEOPLE
            // =====================================================

            if (numberOfPeopleText == null ||
                    numberOfPeopleText.trim().isEmpty()) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Number of people is required."
                );
                return;
            }

            int customerId;
            int destinationId;
            int packageId;
            int numberOfPeople;

            try {

                customerId =
                        Integer.parseInt(
                                customerIdText.trim());

                destinationId =
                        Integer.parseInt(
                                destinationIdText.trim());

                packageId =
                        Integer.parseInt(
                                packageIdText.trim());

                numberOfPeople =
                        Integer.parseInt(
                                numberOfPeopleText.trim());

            } catch (NumberFormatException e) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid numeric booking information."
                );
                return;
            }

            // =====================================================
            // PEOPLE LIMIT
            // =====================================================

            if (numberOfPeople < 1 ||
                    numberOfPeople > 8) {

                sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Number of people must be between 1 and 8."
                );
                return;
            }

            // =====================================================
            // OPTIONAL SERVICES
            // =====================================================

            boolean fishingRequired =
                    Boolean.parseBoolean(
                            fishingRequiredText);

            boolean foodRequired =
                    Boolean.parseBoolean(
                            foodRequiredText);

            // =====================================================
            // APPROVAL ID
            // =====================================================

            Integer approvalId = null;

            if (approvalIdText != null &&
                    !approvalIdText.trim().isEmpty()) {

                try {

                    approvalId =
                            Integer.parseInt(
                                    approvalIdText.trim());

                } catch (NumberFormatException e) {

                    sendError(
                            response,
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid approval ID."
                    );
                    return;
                }
            }

            // =====================================================
            // DATABASE
            // =====================================================

            try (Connection con =
                         DBConnection.getConnection()) {

                // -------------------------------------------------
                // CHECK CUSTOMER
                // -------------------------------------------------

                String customerSql =
                        "SELECT customer_id " +
                        "FROM customers " +
                        "WHERE customer_id = ? " +
                        "AND status = 'ACTIVE'";

                try (PreparedStatement ps =
                             con.prepareStatement(customerSql)) {

                    ps.setInt(1, customerId);

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (!rs.next()) {

                            sendError(
                                    response,
                                    HttpServletResponse.SC_BAD_REQUEST,
                                    "Customer not found or inactive."
                            );
                            return;
                        }
                    }
                }

                // -------------------------------------------------
                // GET DESTINATION
                // -------------------------------------------------

                String destinationSql =
                        "SELECT destination_name, " +
                        "destination_type, " +
                        "trip_allowed " +
                        "FROM destinations " +
                        "WHERE destination_id = ? " +
                        "AND status = 'ACTIVE'";

                String destinationName = "";
                String destinationType = "";
                boolean tripAllowed = false;

                try (PreparedStatement ps =
                             con.prepareStatement(destinationSql)) {

                    ps.setInt(1, destinationId);

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (!rs.next()) {

                            sendError(
                                    response,
                                    HttpServletResponse.SC_BAD_REQUEST,
                                    "Destination not found."
                            );
                            return;
                        }

                        destinationName =
                                rs.getString(
                                        "destination_name");

                        destinationType =
                                rs.getString(
                                        "destination_type");

                        tripAllowed =
                                rs.getBoolean(
                                        "trip_allowed");
                    }
                }

                // -------------------------------------------------
                // ISLAND VALIDATION
                // -------------------------------------------------

                boolean islandExplorer =
                        "Island Explorer"
                                .equalsIgnoreCase(
                                        destinationType);

                if (islandExplorer &&
                        approvalId == null) {

                    sendError(
                            response,
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Island approval is required before booking this destination."
                    );
                    return;
                }

                if (!islandExplorer &&
                        !tripAllowed) {

                    sendError(
                            response,
                            HttpServletResponse.SC_BAD_REQUEST,
                            "This destination is currently not available for trips."
                    );
                    return;
                }

                // -------------------------------------------------
                // GET PACKAGE
                // -------------------------------------------------

                String packageSql =
                        "SELECT package_name, " +
                        "duration_days, " +
                        "duration_nights, " +
                        "base_price_per_person, " +
                        "fishing_included, " +
                        "food_included " +
                        "FROM trip_packages " +
                        "WHERE package_id = ? " +
                        "AND status = 'ACTIVE'";

                String packageName = "";
                int durationDays = 0;
                int durationNights = 0;
                BigDecimal basePrice =
                        BigDecimal.ZERO;

                boolean packageFishingIncluded =
                        false;

                boolean packageFoodIncluded =
                        false;

                try (PreparedStatement ps =
                             con.prepareStatement(packageSql)) {

                    ps.setInt(1, packageId);

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (!rs.next()) {

                            sendError(
                                    response,
                                    HttpServletResponse.SC_BAD_REQUEST,
                                    "Trip package not found."
                            );
                            return;
                        }

                        packageName =
                                rs.getString(
                                        "package_name");

                        durationDays =
                                rs.getInt(
                                        "duration_days");

                        durationNights =
                                rs.getInt(
                                        "duration_nights");

                        basePrice =
                                rs.getBigDecimal(
                                        "base_price_per_person");

                        packageFishingIncluded =
                                rs.getBoolean(
                                        "fishing_included");

                        packageFoodIncluded =
                                rs.getBoolean(
                                        "food_included");
                    }
                }

                // =================================================
                // OPTIONAL SERVICE PRICE
                // =================================================

                BigDecimal fishingPrice =
                        BigDecimal.ZERO;

                BigDecimal foodPrice =
                        BigDecimal.ZERO;

                /*
                 * Project pricing:
                 *
                 * Fishing = ₹500/person
                 * Food    = ₹500/person
                 */

                if (fishingRequired) {

                    fishingPrice =
                            new BigDecimal("500");
                }

                if (foodRequired) {

                    foodPrice =
                            new BigDecimal("500");
                }

                // =================================================
                // TOTAL
                // =================================================

                BigDecimal pricePerPerson =
                        basePrice
                                .add(fishingPrice)
                                .add(foodPrice);

                BigDecimal totalAmount =
                        pricePerPerson.multiply(
                                BigDecimal.valueOf(
                                        numberOfPeople));

                // =================================================
                // CHECK APPROVAL
                // =================================================

                if (islandExplorer) {

                    String approvalSql =
                            "SELECT approval_id " +
                            "FROM island_approvals " +
                            "WHERE approval_id = ? " +
                            "AND destination_id = ? " +
                            "AND approval_status = 'APPROVED' " +
                            "AND approval_start_date <= ? " +
                            "AND approval_end_date >= ?";

                    try (PreparedStatement ps =
                                 con.prepareStatement(
                                         approvalSql)) {

                        ps.setInt(1, approvalId);
                        ps.setInt(2, destinationId);
                        ps.setDate(
                                3,
                                java.sql.Date.valueOf(
                                        bookingDate));
                        ps.setDate(
                                4,
                                java.sql.Date.valueOf(
                                        bookingDate));

                        try (ResultSet rs =
                                     ps.executeQuery()) {

                            if (!rs.next()) {

                                sendError(
                                        response,
                                        HttpServletResponse.SC_BAD_REQUEST,
                                        "The island approval is invalid or does not cover the booking date."
                                );
                                return;
                            }
                        }
                    }
                }

                // =================================================
                // CREATE BOOKING
                // =================================================

                String insertSql =
                        "INSERT INTO bookings " +
                        "(customer_id, destination_id, package_id, " +
                        "boat_id, booking_date, start_time, " +
                        "number_of_people, fishing_required, " +
                        "food_required, total_amount, " +
                        "booking_status, approval_id) " +
                        "VALUES (?, ?, ?, NULL, ?, ?, ?, ?, ?, ?, 'PENDING', ?)";

                int bookingId;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     insertSql,
                                     PreparedStatement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, customerId);
                    ps.setInt(2, destinationId);
                    ps.setInt(3, packageId);

                    ps.setDate(
                            4,
                            java.sql.Date.valueOf(
                                    bookingDate));

                    ps.setTime(
                            5,
                            java.sql.Time.valueOf(
                                    startTime + ":00"));

                    ps.setInt(
                            6,
                            numberOfPeople);

                    ps.setBoolean(
                            7,
                            fishingRequired);

                    ps.setBoolean(
                            8,
                            foodRequired);

                    ps.setBigDecimal(
                            9,
                            totalAmount);

                    if (approvalId != null) {
                        ps.setInt(10, approvalId);
                    } else {
                        ps.setNull(
                                10,
                                java.sql.Types.INTEGER);
                    }

                    int rows =
                            ps.executeUpdate();

                    if (rows == 0) {

                        sendError(
                                response,
                                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                "Booking could not be created."
                        );
                        return;
                    }

                    try (ResultSet keys =
                                 ps.getGeneratedKeys()) {

                        if (!keys.next()) {

                            sendError(
                                    response,
                                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                    "Booking ID was not generated."
                            );
                            return;
                        }

                        bookingId =
                                keys.getInt(1);
                    }
                }

                // =================================================
                // RETURN SUCCESS
                // =================================================

                out.print(
                        "{"
                        + "\"success\":true,"
                        + "\"message\":\"Booking created successfully.\","
                        + "\"bookingId\":" + bookingId + ","
                        + "\"customerId\":" + customerId + ","
                        + "\"destinationId\":" + destinationId + ","
                        + "\"destinationName\":\""
                        + escapeJson(destinationName)
                        + "\","
                        + "\"destinationType\":\""
                        + escapeJson(destinationType)
                        + "\","
                        + "\"packageId\":" + packageId + ","
                        + "\"packageName\":\""
                        + escapeJson(packageName)
                        + "\","
                        + "\"durationDays\":" + durationDays + ","
                        + "\"durationNights\":" + durationNights + ","
                        + "\"numberOfPeople\":"
                        + numberOfPeople + ","
                        + "\"fishingRequired\":"
                        + fishingRequired + ","
                        + "\"foodRequired\":"
                        + foodRequired + ","
                        + "\"totalAmount\":"
                        + totalAmount + ","
                        + "\"bookingStatus\":\"PENDING\","
                        + "\"approvalId\":"
                        + (approvalId == null
                            ? "null"
                            : approvalId)
                        + "}"
                );

            }

        } catch (IllegalArgumentException e) {

            sendError(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid date or time format."
            );

        } catch (Exception e) {

            e.printStackTrace();

            sendError(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Booking server error: "
                            + e.getMessage()
            );
        }
    }

    // =============================================================
    // GET
    // =============================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();

        out.print(
                "{"
                + "\"success\":true,"
                + "\"message\":\"Booking API is working. Use POST to create a booking.\""
                + "}"
        );
    }

    // =============================================================
    // ERROR JSON
    // =============================================================

    private void sendError(
            HttpServletResponse response,
            int status,
            String message)
            throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();

        out.print(
                "{"
                + "\"success\":false,"
                + "\"message\":\""
                + escapeJson(message)
                + "\""
                + "}"
        );
    }

    // =============================================================
    // JSON ESCAPE
    // =============================================================

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}