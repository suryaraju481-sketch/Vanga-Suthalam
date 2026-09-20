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

@WebServlet("/api/packages")
public class PackageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sql =
                "SELECT package_id, "
              + "package_name, "
              + "duration_days, "
              + "duration_nights, "
              + "description, "
              + "base_price_per_person, "
              + "fishing_included, "
              + "food_included "
              + "FROM trip_packages "
              + "WHERE status = 'ACTIVE' "
              + "ORDER BY package_id";

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"success\":true,");
        json.append("\"packages\":[");

        boolean first = true;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                first = false;

                int packageId =
                        rs.getInt("package_id");

                String packageName =
                        rs.getString("package_name");

                int durationDays =
                        rs.getInt("duration_days");

                int durationNights =
                        rs.getInt("duration_nights");

                String description =
                        rs.getString("description");

                BigDecimal price =
                        rs.getBigDecimal(
                                "base_price_per_person"
                        );

                boolean fishingIncluded =
                        rs.getBoolean(
                                "fishing_included"
                        );

                boolean foodIncluded =
                        rs.getBoolean(
                                "food_included"
                        );

                json.append("{");

                json.append("\"packageId\":")
                    .append(packageId)
                    .append(",");

                json.append("\"packageName\":\"")
                    .append(escapeJson(packageName))
                    .append("\",");

                json.append("\"durationDays\":")
                    .append(durationDays)
                    .append(",");

                json.append("\"durationNights\":")
                    .append(durationNights)
                    .append(",");

                json.append("\"description\":\"")
                    .append(escapeJson(description))
                    .append("\",");

                json.append("\"basePricePerPerson\":")
                    .append(price)
                    .append(",");

                json.append("\"fishingIncluded\":")
                    .append(fishingIncluded)
                    .append(",");

                json.append("\"foodIncluded\":")
                    .append(foodIncluded);

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
                    + "\"message\":\"Unable to load trip packages\""
                    + "}"
            );
        }
    }

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