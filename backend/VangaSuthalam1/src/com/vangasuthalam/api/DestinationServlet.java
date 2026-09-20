package com.vangasuthalam.api;

import java.io.IOException;
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

@WebServlet("/api/destinations")
public class DestinationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /*
         * IMPORTANT:
         *
         * Do NOT filter out Island Explorer destinations.
         *
         * The Destination page must display:
         *
         * 1. Mulli Theevu
         * 2. Desert Island
         * 3. Appa Theevu
         * 4. Valai Theevu
         * 5. Muyal Theevu
         *
         * Island destinations can be displayed even when
         * trip_allowed = false.
         *
         * The frontend will show "Approval Required"
         * instead of normal booking.
         */

        String sql =
                "SELECT destination_id, "
              + "destination_name, "
              + "location, "
              + "destination_type, "
              + "description, "
              + "distance_km, "
              + "landing_allowed, "
              + "trip_allowed, "
              + "status "
              + "FROM destinations "
              + "WHERE status = 'ACTIVE' "
              + "ORDER BY "
              + "CASE "
              + "WHEN destination_type = 'Island Explorer' THEN 1 "
              + "ELSE 0 "
              + "END, "
              + "destination_id";

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"success\":true,");
        json.append("\"destinations\":[");

        boolean first = true;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                int destinationId =
                        rs.getInt("destination_id");

                String destinationName =
                        rs.getString("destination_name");

                String location =
                        rs.getString("location");

                String destinationType =
                        rs.getString("destination_type");

                String description =
                        rs.getString("description");

                BigDecimal distanceKm =
                        rs.getBigDecimal("distance_km");

                boolean landingAllowed =
                        rs.getBoolean("landing_allowed");

                boolean tripAllowed =
                        rs.getBoolean("trip_allowed");

                /*
                 * Identify Island Explorer destinations.
                 */
                boolean islandExplorer =
                        "Island Explorer".equalsIgnoreCase(
                                destinationType
                        );

                json.append("{");

                json.append("\"destinationId\":")
                    .append(destinationId)
                    .append(",");

                json.append("\"destinationName\":\"")
                    .append(escapeJson(destinationName))
                    .append("\",");

                json.append("\"location\":\"")
                    .append(escapeJson(location))
                    .append("\",");

                json.append("\"destinationType\":\"")
                    .append(escapeJson(destinationType))
                    .append("\",");

                json.append("\"description\":\"")
                    .append(escapeJson(description))
                    .append("\",");

                /*
                 * Distance can be NULL in database.
                 */
                json.append("\"distanceKm\":");

                if (distanceKm != null) {
                    json.append(distanceKm);
                } else {
                    json.append("null");
                }

                json.append(",");

                json.append("\"landingAllowed\":")
                    .append(landingAllowed)
                    .append(",");

                json.append("\"tripAllowed\":")
                    .append(tripAllowed)
                    .append(",");

                /*
                 * Extra field for React.
                 *
                 * true  = Island Explorer
                 * false = Normal destination
                 */
                json.append("\"islandExplorer\":")
                    .append(islandExplorer);

                json.append("}");
            }

            json.append("]");
            json.append("}");

            response.setStatus(
                    HttpServletResponse.SC_OK
            );

            response.getWriter().write(
                    json.toString()
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().write(
                    "{"
                    + "\"success\":false,"
                    + "\"message\":\"Unable to load destinations\""
                    + "}"
            );
        }
    }

    /*
     * Escape special characters so the generated
     * JSON remains valid.
     */
    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }
}