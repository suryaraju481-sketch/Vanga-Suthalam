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

@WebServlet("/api/captain/dashboard")
public class CaptainDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String captainIdParam = request.getParameter("captainId");

        // Health check
        if (captainIdParam == null || captainIdParam.trim().isEmpty()) {
            out.print("""
                    {
                      "success": false,
                      "message": "Captain ID is required."
                    }
                    """);
            return;
        }

        int captainId;

        try {
            captainId = Integer.parseInt(captainIdParam);
        } catch (NumberFormatException e) {
            out.print("""
                    {
                      "success": false,
                      "message": "Invalid captain ID."
                    }
                    """);
            return;
        }

        String sql =
                "SELECT " +
                "t.trip_id, " +
                "t.booking_id, " +
                "t.trip_status, " +
                "t.trip_start_datetime, " +
                "t.trip_end_datetime, " +

                "c.captain_id, " +
                "c.name AS captain_name, " +
                "c.mobile AS captain_mobile, " +
                "c.experience_years, " +

                "cu.customer_id, " +
                "cu.name AS customer_name, " +
                "cu.mobile AS customer_mobile, " +
                "cu.email AS customer_email, " +

                "b.boat_id, " +
                "b.boat_name, " +
                "b.boat_number, " +
                "b.boat_type, " +
                "b.capacity, " +

                "bk.destination_id, " +
                "d.destination_name, " +
                "d.location, " +
                "d.destination_type, " +

                "bk.package_id, " +
                "p.package_name, " +
                "p.duration_days, " +
                "p.duration_nights, " +

                "bk.booking_date, " +
                "bk.start_time, " +
                "bk.number_of_people, " +
                "bk.fishing_required, " +
                "bk.food_required, " +
                "bk.total_amount, " +
                "bk.booking_status, " +

                "pay.payment_status, " +
                "pay.transaction_reference, " +

                "sc.safety_status " +

                "FROM trips t " +

                "JOIN captains c " +
                "ON t.captain_id = c.captain_id " +

                "JOIN bookings bk " +
                "ON t.booking_id = bk.booking_id " +

                "JOIN customers cu " +
                "ON bk.customer_id = cu.customer_id " +

                "JOIN boats b " +
                "ON t.boat_id = b.boat_id " +

                "JOIN destinations d " +
                "ON bk.destination_id = d.destination_id " +

                "JOIN trip_packages p " +
                "ON bk.package_id = p.package_id " +

                "LEFT JOIN payments pay " +
                "ON bk.booking_id = pay.booking_id " +

                "LEFT JOIN safety_checks sc " +
                "ON bk.booking_id = sc.booking_id " +

                "WHERE t.captain_id = ? " +

                "ORDER BY t.trip_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, captainId);

            try (ResultSet rs = ps.executeQuery()) {

                StringBuilder json = new StringBuilder();

                json.append("{");
                json.append("\"success\":true,");
                json.append("\"captainId\":").append(captainId).append(",");
                json.append("\"trips\":[");

                boolean first = true;

                while (rs.next()) {

                    if (!first) {
                        json.append(",");
                    }

                    first = false;

                    json.append("{");

                    json.append("\"tripId\":")
                        .append(rs.getInt("trip_id")).append(",");

                    json.append("\"bookingId\":")
                        .append(rs.getInt("booking_id")).append(",");

                    json.append("\"tripStatus\":\"")
                        .append(safe(rs.getString("trip_status"))).append("\",");

                    json.append("\"tripStart\":\"")
                        .append(safe(rs.getString("trip_start_datetime"))).append("\",");

                    json.append("\"tripEnd\":\"")
                        .append(safe(rs.getString("trip_end_datetime"))).append("\",");

                    // Captain
                    json.append("\"captain\":{");

                    json.append("\"id\":")
                        .append(rs.getInt("captain_id")).append(",");

                    json.append("\"name\":\"")
                        .append(safe(rs.getString("captain_name"))).append("\",");

                    json.append("\"mobile\":\"")
                        .append(safe(rs.getString("captain_mobile"))).append("\",");

                    json.append("\"experienceYears\":")
                        .append(rs.getInt("experience_years"));

                    json.append("},");

                    // Customer
                    json.append("\"customer\":{");

                    json.append("\"id\":")
                        .append(rs.getInt("customer_id")).append(",");

                    json.append("\"name\":\"")
                        .append(safe(rs.getString("customer_name"))).append("\",");

                    json.append("\"mobile\":\"")
                        .append(safe(rs.getString("customer_mobile"))).append("\",");

                    json.append("\"email\":\"")
                        .append(safe(rs.getString("customer_email"))).append("\"");

                    json.append("},");

                    // Boat
                    json.append("\"boat\":{");

                    json.append("\"id\":")
                        .append(rs.getInt("boat_id")).append(",");

                    json.append("\"name\":\"")
                        .append(safe(rs.getString("boat_name"))).append("\",");

                    json.append("\"number\":\"")
                        .append(safe(rs.getString("boat_number"))).append("\",");

                    json.append("\"type\":\"")
                        .append(safe(rs.getString("boat_type"))).append("\",");

                    json.append("\"capacity\":")
                        .append(rs.getInt("capacity"));

                    json.append("},");

                    // Destination
                    json.append("\"destination\":{");

                    json.append("\"id\":")
                        .append(rs.getInt("destination_id")).append(",");

                    json.append("\"name\":\"")
                        .append(safe(rs.getString("destination_name"))).append("\",");

                    json.append("\"location\":\"")
                        .append(safe(rs.getString("location"))).append("\",");

                    json.append("\"type\":\"")
                        .append(safe(rs.getString("destination_type"))).append("\"");

                    json.append("},");

                    // Package
                    json.append("\"package\":{");

                    json.append("\"id\":")
                        .append(rs.getInt("package_id")).append(",");

                    json.append("\"name\":\"")
                        .append(safe(rs.getString("package_name"))).append("\",");

                    json.append("\"durationDays\":")
                        .append(rs.getInt("duration_days")).append(",");

                    json.append("\"durationNights\":")
                        .append(rs.getInt("duration_nights"));

                    json.append("},");

                    // Booking
                    json.append("\"booking\":{");

                    json.append("\"date\":\"")
                        .append(safe(rs.getString("booking_date"))).append("\",");

                    json.append("\"startTime\":\"")
                        .append(safe(rs.getString("start_time"))).append("\",");

                    json.append("\"passengers\":")
                        .append(rs.getInt("number_of_people")).append(",");

                    json.append("\"fishingRequired\":")
                        .append(rs.getBoolean("fishing_required")).append(",");

                    json.append("\"foodRequired\":")
                        .append(rs.getBoolean("food_required")).append(",");

                    json.append("\"totalAmount\":")
                        .append(rs.getBigDecimal("total_amount")).append(",");

                    json.append("\"status\":\"")
                        .append(safe(rs.getString("booking_status"))).append("\"");

                    json.append("},");

                    // Payment
                    json.append("\"payment\":{");

                    json.append("\"status\":\"")
                        .append(safe(rs.getString("payment_status"))).append("\",");

                    json.append("\"transactionReference\":\"")
                        .append(safe(rs.getString("transaction_reference"))).append("\"");

                    json.append("},");

                    // Safety
                    json.append("\"safety\":{");

                    json.append("\"status\":\"")
                        .append(safe(rs.getString("safety_status"))).append("\"");

                    json.append("}");

                    json.append("}");
                }

                json.append("]");
                json.append("}");

                out.print(json.toString());
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.print("""
                    {
                      "success": false,
                      "message": "Unable to load captain dashboard."
                    }
                    """);
        }
    }

    private String safe(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}