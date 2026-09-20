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

@WebServlet("/api/available-boats")
public class AvailableBoatsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String sql =
            "SELECT b.boat_id, b.boat_name, b.boat_number, " +
            "b.boat_type, b.capacity, b.engine_details, " +
            "b.safety_equipment, b.registration_details, " +
            "b.captain_id, c.name AS captain_name, " +
            "c.mobile AS captain_mobile, c.experience_years " +
            "FROM boats b " +
            "JOIN captains c ON b.captain_id = c.captain_id " +
            "WHERE b.status = 'AVAILABLE' " +
            "AND c.status = 'ACTIVE' " +
            "ORDER BY b.boat_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            StringBuilder json = new StringBuilder();

            json.append("{");
            json.append("\"success\":true,");
            json.append("\"boats\":[");

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                json.append("{");

                json.append("\"boatId\":")
                    .append(rs.getInt("boat_id"))
                    .append(",");

                json.append("\"boatName\":\"")
                    .append(escapeJson(rs.getString("boat_name")))
                    .append("\",");

                json.append("\"boatNumber\":\"")
                    .append(escapeJson(rs.getString("boat_number")))
                    .append("\",");

                json.append("\"boatType\":\"")
                    .append(escapeJson(rs.getString("boat_type")))
                    .append("\",");

                json.append("\"capacity\":")
                    .append(rs.getInt("capacity"))
                    .append(",");

                json.append("\"engineDetails\":\"")
                    .append(escapeJson(rs.getString("engine_details")))
                    .append("\",");

                json.append("\"safetyEquipment\":\"")
                    .append(escapeJson(rs.getString("safety_equipment")))
                    .append("\",");

                json.append("\"registrationDetails\":\"")
                    .append(escapeJson(rs.getString("registration_details")))
                    .append("\",");

                json.append("\"captainId\":")
                    .append(rs.getInt("captain_id"))
                    .append(",");

                json.append("\"captainName\":\"")
                    .append(escapeJson(rs.getString("captain_name")))
                    .append("\",");

                json.append("\"captainMobile\":\"")
                    .append(escapeJson(rs.getString("captain_mobile")))
                    .append("\",");

                json.append("\"experienceYears\":")
                    .append(rs.getInt("experience_years"));

                json.append("}");
            }

            json.append("]");
            json.append("}");

            out.print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            out.print("""
                {
                    "success": false,
                    "message": "Unable to load available boats."
                }
                """);
        }
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