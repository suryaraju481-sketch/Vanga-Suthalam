package com.vangasuthalam.api;

import com.vangasuthalam.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

@WebServlet("/api/island-approval")
public class IslandApprovalServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String customerIdText =
                request.getParameter("customerId");

        String destinationIdText =
                request.getParameter("destinationId");

        String certificateNumber =
                request.getParameter("certificateNumber");

        String approvedBy =
                request.getParameter("approvedBy");

        String startDateText =
                request.getParameter("startDate");

        String endDateText =
                request.getParameter("endDate");

        String remarks =
                request.getParameter("remarks");

        try {

            // ==========================================
            // VALIDATE REQUIRED FIELDS
            // ==========================================

            if (customerIdText == null ||
                destinationIdText == null ||
                certificateNumber == null ||
                approvedBy == null ||
                startDateText == null ||
                endDateText == null) {

                sendJson(
                    out,
                    false,
                    "Required fields are missing."
                );

                return;
            }

            int customerId =
                    Integer.parseInt(customerIdText);

            int destinationId =
                    Integer.parseInt(destinationIdText);

            Date startDate =
                    Date.valueOf(startDateText);

            Date endDate =
                    Date.valueOf(endDateText);

            // ==========================================
            // VALIDATE CUSTOMER
            // ==========================================

            String customerSql =
                    "SELECT customer_id " +
                    "FROM customers " +
                    "WHERE customer_id = ? " +
                    "AND status = 'ACTIVE'";

            try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(customerSql)
            ) {

                ps.setInt(1, customerId);

                try (ResultSet rs =
                        ps.executeQuery()) {

                    if (!rs.next()) {

                        sendJson(
                            out,
                            false,
                            "Customer not found."
                        );

                        return;
                    }
                }
            }

            // ==========================================
            // VALIDATE ISLAND
            // ==========================================

            String destinationSql =
                    "SELECT destination_id, " +
                    "destination_name " +
                    "FROM destinations " +
                    "WHERE destination_id = ? " +
                    "AND destination_type = 'Island Explorer' " +
                    "AND status = 'ACTIVE'";

            String islandName = "";

            try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(destinationSql)
            ) {

                ps.setInt(1, destinationId);

                try (ResultSet rs =
                        ps.executeQuery()) {

                    if (!rs.next()) {

                        sendJson(
                            out,
                            false,
                            "Invalid island destination."
                        );

                        return;
                    }

                    islandName =
                            rs.getString(
                                "destination_name"
                            );
                }
            }

            // ==========================================
            // VALIDATE DATES
            // ==========================================

            long difference =
                    endDate.getTime()
                    - startDate.getTime();

            long days =
                    (difference /
                    (1000L * 60 * 60 * 24)) + 1;

            if (days <= 0) {

                sendJson(
                    out,
                    false,
                    "End date must be after start date."
                );

                return;
            }

            if (days > 2) {

                sendJson(
                    out,
                    false,
                    "Island approval cannot exceed 2 days."
                );

                return;
            }

            // ==========================================
            // CHECK EXISTING APPROVAL
            // ==========================================

            String existingSql =
                    "SELECT approval_id " +
                    "FROM island_approvals " +
                    "WHERE destination_id = ? " +
                    "AND approval_status = 'APPROVED' " +
                    "AND approval_end_date >= ? " +
                    "AND approval_start_date <= ? " +
                    "AND booking_id IS NULL " +
                    "ORDER BY approval_id DESC " +
                    "LIMIT 1";

            try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(existingSql)
            ) {

                ps.setInt(1, destinationId);
                ps.setDate(2, startDate);
                ps.setDate(3, endDate);

                try (ResultSet rs =
                        ps.executeQuery()) {

                    if (rs.next()) {

                        sendJson(
                            out,
                            false,
                            "An active approval already exists for this island and date range."
                        );

                        return;
                    }
                }
            }

            // ==========================================
            // INSERT APPROVAL
            // ==========================================

            String insertSql =
                    "INSERT INTO island_approvals " +
                    "(booking_id, destination_id, " +
                    "certificate_number, approved_by, " +
                    "approval_start_date, approval_end_date, " +
                    "approval_status, remarks) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, 'APPROVED', ?)";

            int approvalId = 0;

            try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                            insertSql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                        )
            ) {

                ps.setInt(1, destinationId);

                ps.setString(
                    2,
                    certificateNumber.trim()
                );

                ps.setString(
                    3,
                    approvedBy.trim()
                );

                ps.setDate(
                    4,
                    startDate
                );

                ps.setDate(
                    5,
                    endDate
                );

                ps.setString(
                    6,
                    remarks
                );

                int rows =
                        ps.executeUpdate();

                if (rows == 0) {

                    sendJson(
                        out,
                        false,
                        "Approval could not be saved."
                    );

                    return;
                }

                try (
                    ResultSet keys =
                            ps.getGeneratedKeys()
                ) {

                    if (keys.next()) {
                        approvalId =
                                keys.getInt(1);
                    }
                }
            }

            // ==========================================
            // SUCCESS
            // ==========================================

            out.print(
                "{"
                + "\"success\":true,"
                + "\"message\":\"Island approval confirmed successfully.\","
                + "\"approvalId\":" + approvalId + ","
                + "\"customerId\":" + customerId + ","
                + "\"destinationId\":" + destinationId + ","
                + "\"islandName\":\""
                + escapeJson(islandName)
                + "\""
                + "}"
            );

        } catch (NumberFormatException e) {

            sendJson(
                out,
                false,
                "Invalid customer ID or destination ID."
            );

        } catch (IllegalArgumentException e) {

            sendJson(
                out,
                false,
                "Invalid date format."
            );

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(
                out,
                false,
                "Server error while processing island approval."
            );
        }
    }

    // ==============================================
    // CHECK APPROVAL
    // ==============================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();

        String approvalIdText =
                request.getParameter("approvalId");

        if (approvalIdText == null) {

            sendJson(
                out,
                false,
                "Approval ID is required."
            );

            return;
        }

        try {

            int approvalId =
                    Integer.parseInt(
                        approvalIdText
                    );

            String sql =
                    "SELECT a.approval_id, " +
                    "a.destination_id, " +
                    "d.destination_name, " +
                    "a.certificate_number, " +
                    "a.approved_by, " +
                    "a.approval_start_date, " +
                    "a.approval_end_date, " +
                    "a.approval_status, " +
                    "a.remarks " +
                    "FROM island_approvals a " +
                    "JOIN destinations d " +
                    "ON a.destination_id = d.destination_id " +
                    "WHERE a.approval_id = ?";

            try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
            ) {

                ps.setInt(1, approvalId);

                try (
                    ResultSet rs =
                            ps.executeQuery()
                ) {

                    if (!rs.next()) {

                        sendJson(
                            out,
                            false,
                            "Approval not found."
                        );

                        return;
                    }

                    boolean approved =
                            "APPROVED".equalsIgnoreCase(
                                rs.getString(
                                    "approval_status"
                                )
                            );

                    out.print(
                        "{"
                        + "\"success\":true,"
                        + "\"approved\":" + approved + ","
                        + "\"approvalId\":"
                        + rs.getInt("approval_id")
                        + ","
                        + "\"destinationId\":"
                        + rs.getInt("destination_id")
                        + ","
                        + "\"destinationName\":\""
                        + escapeJson(
                            rs.getString(
                                "destination_name"
                            )
                        )
                        + "\","
                        + "\"certificateNumber\":\""
                        + escapeJson(
                            rs.getString(
                                "certificate_number"
                            )
                        )
                        + "\","
                        + "\"approvedBy\":\""
                        + escapeJson(
                            rs.getString(
                                "approved_by"
                            )
                        )
                        + "\","
                        + "\"startDate\":\""
                        + rs.getDate(
                            "approval_start_date"
                        )
                        + "\","
                        + "\"endDate\":\""
                        + rs.getDate(
                            "approval_end_date"
                        )
                        + "\","
                        + "\"status\":\""
                        + escapeJson(
                            rs.getString(
                                "approval_status"
                            )
                        )
                        + "\""
                        + "}"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(
                out,
                false,
                "Unable to check approval."
            );
        }
    }

    // ==============================================
    // JSON HELPER
    // ==============================================

    private void sendJson(
            PrintWriter out,
            boolean success,
            String message) {

        out.print(
            "{"
            + "\"success\":"
            + success
            + ","
            + "\"message\":\""
            + escapeJson(message)
            + "\""
            + "}"
        );
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