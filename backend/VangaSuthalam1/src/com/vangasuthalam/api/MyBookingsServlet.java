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

@WebServlet("/api/my-bookings")
public class MyBookingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String customerIdParam = request.getParameter("customerId");

        if (customerIdParam == null || customerIdParam.trim().isEmpty()) {
            out.print("""
                    {
                      "success": false,
                      "message": "Customer ID is required."
                    }
                    """);
            return;
        }

        int customerId;

        try {
            customerId = Integer.parseInt(customerIdParam);
        } catch (NumberFormatException e) {

            out.print("""
                    {
                      "success": false,
                      "message": "Invalid customer ID."
                    }
                    """);
            return;
        }

        String sql = """
                SELECT
                    b.booking_id,
                    b.customer_id,

                    b.destination_id,
                    d.destination_name,
                    d.location,
                    d.destination_type,

                    b.package_id,
                    p.package_name,
                    p.duration_days,
                    p.duration_nights,

                    b.booking_date,
                    b.start_time,
                    b.number_of_people,
                    b.fishing_required,
                    b.food_required,
                    b.total_amount,
                    b.booking_status,

                    b.boat_id,
                    bt.boat_name,
                    bt.boat_number,

                    (
                        SELECT t.trip_id
                        FROM trips t
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS trip_id,

                    (
                        SELECT t.trip_status
                        FROM trips t
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS trip_status,

                    (
                        SELECT t.trip_start_datetime
                        FROM trips t
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS trip_start_datetime,

                    (
                        SELECT t.trip_end_datetime
                        FROM trips t
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS trip_end_datetime,

                    (
                        SELECT c.captain_id
                        FROM trips t
                        JOIN captains c
                            ON t.captain_id = c.captain_id
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS captain_id,

                    (
                        SELECT c.name
                        FROM trips t
                        JOIN captains c
                            ON t.captain_id = c.captain_id
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS captain_name,

                    (
                        SELECT c.mobile
                        FROM trips t
                        JOIN captains c
                            ON t.captain_id = c.captain_id
                        WHERE t.booking_id = b.booking_id
                        ORDER BY t.trip_id DESC
                        LIMIT 1
                    ) AS captain_mobile,

                    (
                        SELECT pay.payment_id
                        FROM payments pay
                        WHERE pay.booking_id = b.booking_id
                        ORDER BY pay.payment_id DESC
                        LIMIT 1
                    ) AS payment_id,

                    (
                        SELECT pay.payment_status
                        FROM payments pay
                        WHERE pay.booking_id = b.booking_id
                        ORDER BY pay.payment_id DESC
                        LIMIT 1
                    ) AS payment_status,

                    (
                        SELECT pay.payment_method
                        FROM payments pay
                        WHERE pay.booking_id = b.booking_id
                        ORDER BY pay.payment_id DESC
                        LIMIT 1
                    ) AS payment_method,

                    (
                        SELECT pay.transaction_reference
                        FROM payments pay
                        WHERE pay.booking_id = b.booking_id
                        ORDER BY pay.payment_id DESC
                        LIMIT 1
                    ) AS transaction_reference,

                    (
                        SELECT f.feedback_id
                        FROM feedback f
                        WHERE f.booking_id = b.booking_id
                        ORDER BY f.feedback_id DESC
                        LIMIT 1
                    ) AS feedback_id

                FROM bookings b

                LEFT JOIN destinations d
                    ON b.destination_id = d.destination_id

                LEFT JOIN trip_packages p
                    ON b.package_id = p.package_id

                LEFT JOIN boats bt
                    ON b.boat_id = bt.boat_id

                WHERE b.customer_id = ?

                ORDER BY b.booking_id DESC
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            if (con == null) {
                out.print("""
                        {
                          "success": false,
                          "message": "Database connection failed."
                        }
                        """);
                return;
            }

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                StringBuilder json = new StringBuilder();

                json.append("{");
                json.append("\"success\":true,");
                json.append("\"customerId\":").append(customerId).append(",");
                json.append("\"bookings\":[");

                boolean first = true;

                while (rs.next()) {

                    if (!first) {
                        json.append(",");
                    }

                    first = false;

                    json.append("{");

                    // Booking
                    json.append("\"bookingId\":")
                        .append(rs.getInt("booking_id")).append(",");

                    json.append("\"customerId\":")
                        .append(rs.getInt("customer_id")).append(",");

                    // Destination
                    json.append("\"destination\":{");

                    json.append("\"destinationId\":")
                        .append(rs.getInt("destination_id")).append(",");

                    json.append("\"name\":\"")
                        .append(escape(rs.getString("destination_name")))
                        .append("\",");

                    json.append("\"location\":\"")
                        .append(escape(rs.getString("location")))
                        .append("\",");

                    json.append("\"type\":\"")
                        .append(escape(rs.getString("destination_type")))
                        .append("\"");

                    json.append("},");

                    // Package
                    json.append("\"package\":{");

                    json.append("\"packageId\":")
                        .append(rs.getInt("package_id")).append(",");

                    json.append("\"name\":\"")
                        .append(escape(rs.getString("package_name")))
                        .append("\",");

                    json.append("\"durationDays\":")
                        .append(rs.getInt("duration_days")).append(",");

                    json.append("\"durationNights\":")
                        .append(rs.getInt("duration_nights"));

                    json.append("},");

                    // Booking details
                    json.append("\"bookingDate\":\"")
                        .append(rs.getDate("booking_date"))
                        .append("\",");

                    json.append("\"startTime\":\"")
                        .append(rs.getTime("start_time"))
                        .append("\",");

                    json.append("\"numberOfPeople\":")
                        .append(rs.getInt("number_of_people"))
                        .append(",");

                    json.append("\"fishingRequired\":")
                        .append(rs.getBoolean("fishing_required"))
                        .append(",");

                    json.append("\"foodRequired\":")
                        .append(rs.getBoolean("food_required"))
                        .append(",");

                    BigDecimal totalAmount =
                            rs.getBigDecimal("total_amount");

                    json.append("\"totalAmount\":")
                        .append(totalAmount != null
                                ? totalAmount
                                : "0")
                        .append(",");

                    json.append("\"bookingStatus\":\"")
                        .append(escape(rs.getString("booking_status")))
                        .append("\",");

                    // Boat
                    Integer boatId = getNullableInt(rs, "boat_id");

                    json.append("\"boat\":{");

                    if (boatId != null) {

                        json.append("\"boatId\":")
                            .append(boatId).append(",");

                        json.append("\"boatName\":\"")
                            .append(escape(rs.getString("boat_name")))
                            .append("\",");

                        json.append("\"boatNumber\":\"")
                            .append(escape(rs.getString("boat_number")))
                            .append("\"");

                    } else {

                        json.append("\"boatId\":null,");
                        json.append("\"boatName\":null,");
                        json.append("\"boatNumber\":null");
                    }

                    json.append("},");

                    // Captain
                    Integer captainId =
                            getNullableInt(rs, "captain_id");

                    json.append("\"captain\":{");

                    if (captainId != null) {

                        json.append("\"captainId\":")
                            .append(captainId).append(",");

                        json.append("\"name\":\"")
                            .append(escape(rs.getString("captain_name")))
                            .append("\",");

                        json.append("\"mobile\":\"")
                            .append(escape(rs.getString("captain_mobile")))
                            .append("\"");

                    } else {

                        json.append("\"captainId\":null,");
                        json.append("\"name\":null,");
                        json.append("\"mobile\":null");
                    }

                    json.append("},");

                    // Trip
                    Integer tripId =
                            getNullableInt(rs, "trip_id");

                    json.append("\"trip\":{");

                    if (tripId != null) {

                        json.append("\"tripId\":")
                            .append(tripId).append(",");

                        json.append("\"status\":\"")
                            .append(escape(rs.getString("trip_status")))
                            .append("\",");

                        json.append("\"start\":\"")
                            .append(String.valueOf(
                                    rs.getTimestamp(
                                            "trip_start_datetime")))
                            .append("\",");

                        json.append("\"end\":\"")
                            .append(String.valueOf(
                                    rs.getTimestamp(
                                            "trip_end_datetime")))
                            .append("\"");

                    } else {

                        json.append("\"tripId\":null,");
                        json.append("\"status\":null,");
                        json.append("\"start\":null,");
                        json.append("\"end\":null");
                    }

                    json.append("},");

                    // Payment
                    Integer paymentId =
                            getNullableInt(rs, "payment_id");

                    json.append("\"payment\":{");

                    if (paymentId != null) {

                        json.append("\"paymentId\":")
                            .append(paymentId).append(",");

                        json.append("\"status\":\"")
                            .append(escape(rs.getString("payment_status")))
                            .append("\",");

                        json.append("\"method\":\"")
                            .append(escape(rs.getString("payment_method")))
                            .append("\",");

                        json.append("\"transactionReference\":\"")
                            .append(escape(
                                    rs.getString(
                                            "transaction_reference")))
                            .append("\"");

                    } else {

                        json.append("\"paymentId\":null,");
                        json.append("\"status\":null,");
                        json.append("\"method\":null,");
                        json.append("\"transactionReference\":null");
                    }

                    json.append("},");

                    // Feedback
                    Integer feedbackId =
                            getNullableInt(rs, "feedback_id");

                    json.append("\"feedbackSubmitted\":")
                        .append(feedbackId != null);

                    if (feedbackId != null) {

                        json.append(",");
                        json.append("\"feedbackId\":")
                            .append(feedbackId);
                    }

                    json.append("}");
                }

                json.append("]");
                json.append("}");

                out.print(json);
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            out.print("""
                    {
                      "success": false,
                      "message": "Unable to load booking history."
                    }
                    """);
        }
    }

    private Integer getNullableInt(ResultSet rs, String column)
            throws Exception {

        int value = rs.getInt(column);

        if (rs.wasNull()) {
            return null;
        }

        return value;
    }

    private String escape(String value) {

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