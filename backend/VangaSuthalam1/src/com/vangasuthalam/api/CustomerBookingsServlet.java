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

@WebServlet("/api/customer/bookings")
public class CustomerBookingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String customerIdText =
                request.getParameter("customerId");

        if (customerIdText == null ||
                customerIdText.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

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
            customerId =
                    Integer.parseInt(customerIdText.trim());

        } catch (NumberFormatException e) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            out.print("""
                    {
                      "success": false,
                      "message": "Invalid customer ID."
                    }
                    """);
            return;
        }

        String sql =
                "SELECT " +
                "b.booking_id, " +
                "b.customer_id, " +
                "b.destination_id, " +
                "d.destination_name, " +
                "d.destination_type, " +
                "b.package_id, " +
                "p.package_name, " +
                "p.duration_days, " +
                "p.duration_nights, " +
                "b.booking_date, " +
                "b.start_time, " +
                "b.number_of_people, " +
                "b.fishing_required, " +
                "b.food_required, " +
                "b.total_amount, " +
                "b.booking_status, " +
                "b.approval_id, " +

                "pay.payment_id, " +
                "pay.payment_method, " +
                "pay.transaction_reference, " +
                "pay.payment_status, " +

                "bt.boat_id, " +
                "bt.boat_name, " +
                "bt.boat_number, " +

                "c.captain_id, " +
                "c.name AS captain_name, " +
                "c.mobile AS captain_mobile, " +

                "t.trip_id, " +
                "t.trip_start_datetime, " +
                "t.trip_end_datetime, " +
                "t.trip_status " +

                "FROM bookings b " +

                "LEFT JOIN destinations d " +
                "ON b.destination_id = d.destination_id " +

                "LEFT JOIN trip_packages p " +
                "ON b.package_id = p.package_id " +

                "LEFT JOIN ( " +
                "SELECT p1.* " +
                "FROM payments p1 " +
                "INNER JOIN ( " +
                "SELECT booking_id, " +
                "MAX(payment_id) AS payment_id " +
                "FROM payments " +
                "GROUP BY booking_id " +
                ") latest " +
                "ON latest.payment_id = p1.payment_id " +
                ") pay " +
                "ON pay.booking_id = b.booking_id " +

                "LEFT JOIN boats bt " +
                "ON b.boat_id = bt.boat_id " +

                "LEFT JOIN captains c " +
                "ON bt.captain_id = c.captain_id " +

                "LEFT JOIN trips t " +
                "ON t.booking_id = b.booking_id " +

                "WHERE b.customer_id = ? " +
                "ORDER BY b.booking_id DESC";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                StringBuilder json =
                        new StringBuilder();

                json.append("{");
                json.append("\"success\":true,");
                json.append("\"customerId\":")
                    .append(customerId)
                    .append(",");
                json.append("\"bookings\":[");

                boolean first = true;

                while (rs.next()) {

                    if (!first) {
                        json.append(",");
                    }

                    first = false;

                    json.append("{");

                    addInt(json,
                            "bookingId",
                            rs.getInt("booking_id"));

                    addInt(json,
                            "customerId",
                            rs.getInt("customer_id"));

                    addInt(json,
                            "destinationId",
                            rs.getInt("destination_id"));

                    addString(json,
                            "destination",
                            rs.getString(
                                    "destination_name"));

                    addString(json,
                            "destinationType",
                            rs.getString(
                                    "destination_type"));

                    addInt(json,
                            "packageId",
                            rs.getInt("package_id"));

                    addString(json,
                            "packageName",
                            rs.getString(
                                    "package_name"));

                    addInt(json,
                            "durationDays",
                            rs.getInt(
                                    "duration_days"));

                    addInt(json,
                            "durationNights",
                            rs.getInt(
                                    "duration_nights"));

                    addString(json,
                            "bookingDate",
                            rs.getString(
                                    "booking_date"));

                    addString(json,
                            "startTime",
                            rs.getString(
                                    "start_time"));

                    addInt(json,
                            "numberOfPeople",
                            rs.getInt(
                                    "number_of_people"));

                    addBoolean(json,
                            "fishingRequired",
                            rs.getBoolean(
                                    "fishing_required"));

                    addBoolean(json,
                            "foodRequired",
                            rs.getBoolean(
                                    "food_required"));

                    addDecimal(json,
                            "totalAmount",
                            rs.getBigDecimal(
                                    "total_amount"));

                    addString(json,
                            "bookingStatus",
                            rs.getString(
                                    "booking_status"));

                    addNullableInt(json,
                            "approvalId",
                            rs.getObject(
                                    "approval_id"));

                    addNullableInt(json,
                            "paymentId",
                            rs.getObject(
                                    "payment_id"));

                    addString(json,
                            "paymentMethod",
                            rs.getString(
                                    "payment_method"));

                    addString(json,
                            "transactionReference",
                            rs.getString(
                                    "transaction_reference"));

                    addString(json,
                            "paymentStatus",
                            rs.getString(
                                    "payment_status"));

                    addNullableInt(json,
                            "boatId",
                            rs.getObject(
                                    "boat_id"));

                    addString(json,
                            "boatName",
                            rs.getString(
                                    "boat_name"));

                    addString(json,
                            "boatNumber",
                            rs.getString(
                                    "boat_number"));

                    addNullableInt(json,
                            "captainId",
                            rs.getObject(
                                    "captain_id"));

                    addString(json,
                            "captainName",
                            rs.getString(
                                    "captain_name"));

                    addString(json,
                            "captainMobile",
                            rs.getString(
                                    "captain_mobile"));

                    addNullableInt(json,
                            "tripId",
                            rs.getObject(
                                    "trip_id"));

                    addString(json,
                            "tripStart",
                            rs.getString(
                                    "trip_start_datetime"));

                    addString(json,
                            "tripEnd",
                            rs.getString(
                                    "trip_end_datetime"));

                    addString(json,
                            "tripStatus",
                            rs.getString(
                                    "trip_status"));

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

            out.print(
                    "{"
                    + "\"success\":false,"
                    + "\"message\":\""
                    + escapeJson(e.getMessage())
                    + "\""
                    + "}"
            );
        }
    }

    private void addString(
            StringBuilder json,
            String key,
            String value) {

        json.append("\"")
            .append(key)
            .append("\":");

        if (value == null) {
            json.append("null");
        } else {
            json.append("\"")
                .append(escapeJson(value))
                .append("\"");
        }

        json.append(",");
    }

    private void addInt(
            StringBuilder json,
            String key,
            int value) {

        json.append("\"")
            .append(key)
            .append("\":")
            .append(value)
            .append(",");
    }

    private void addNullableInt(
            StringBuilder json,
            String key,
            Object value) {

        json.append("\"")
            .append(key)
            .append("\":");

        if (value == null) {
            json.append("null");
        } else {
            json.append(value);
        }

        json.append(",");
    }

    private void addBoolean(
            StringBuilder json,
            String key,
            boolean value) {

        json.append("\"")
            .append(key)
            .append("\":")
            .append(value)
            .append(",");
    }

    private void addDecimal(
            StringBuilder json,
            String key,
            Object value) {

        json.append("\"")
            .append(key)
            .append("\":");

        if (value == null) {
            json.append("null");
        } else {
            json.append(value);
        }

        json.append(",");
    }

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