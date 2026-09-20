package com.vangasuthalam.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.vangasuthalam.util.DBConnection;

@WebServlet("/api/payments")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==========================================
    // POST PAYMENT
    // ==========================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {

            // ----------------------------------
            // GET REQUEST DATA
            // ----------------------------------

            String bookingIdText =
                    request.getParameter("bookingId");

            String amountText =
                    request.getParameter("amount");

            String paymentMethod =
                    request.getParameter("paymentMethod");

            String transactionReference =
                    request.getParameter("transactionReference");


            // ----------------------------------
            // VALIDATION
            // ----------------------------------

            if (bookingIdText == null ||
                    bookingIdText.trim().isEmpty()) {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST);

                out.print(
                    "{\"success\":false,\"message\":\"Booking ID is required.\"}"
                );

                return;
            }


            if (amountText == null ||
                    amountText.trim().isEmpty()) {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST);

                out.print(
                    "{\"success\":false,\"message\":\"Payment amount is required.\"}"
                );

                return;
            }


            if (paymentMethod == null ||
                    paymentMethod.trim().isEmpty()) {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST);

                out.print(
                    "{\"success\":false,\"message\":\"Payment method is required.\"}"
                );

                return;
            }


            // ----------------------------------
            // CONVERT VALUES
            // ----------------------------------

            int bookingId =
                    Integer.parseInt(
                            bookingIdText);

            double amount =
                    Double.parseDouble(
                            amountText);


            // ----------------------------------
            // CHECK BOOKING
            // ----------------------------------

            String bookingSql =
                    "SELECT booking_id, total_amount, booking_status "
                  + "FROM bookings "
                  + "WHERE booking_id = ?";


            try (Connection con =
                        DBConnection.getConnection();

                 PreparedStatement ps =
                        con.prepareStatement(
                                bookingSql)) {


                ps.setInt(
                        1,
                        bookingId);


                try (ResultSet rs =
                        ps.executeQuery()) {


                    if (!rs.next()) {

                        response.setStatus(
                                HttpServletResponse.SC_NOT_FOUND);

                        out.print(
                            "{\"success\":false,\"message\":\"Booking not found.\"}"
                        );

                        return;
                    }


                    double bookingAmount =
                            rs.getDouble(
                                    "total_amount");


                    // ----------------------------------
                    // AMOUNT VALIDATION
                    // ----------------------------------

                    if (Math.abs(
                            bookingAmount - amount)
                            > 0.01) {

                        response.setStatus(
                                HttpServletResponse.SC_BAD_REQUEST);

                        out.print(
                            "{\"success\":false,\"message\":\"Payment amount does not match booking amount.\"}"
                        );

                        return;
                    }
                }
            }


            // ----------------------------------
            // CHECK EXISTING PAYMENT
            // ----------------------------------

            String existingPaymentSql =
                    "SELECT payment_id, payment_status "
                  + "FROM payments "
                  + "WHERE booking_id = ? "
                  + "AND payment_status = 'CONFIRMED' "
                  + "LIMIT 1";


            try (Connection con =
                        DBConnection.getConnection();

                 PreparedStatement ps =
                        con.prepareStatement(
                                existingPaymentSql)) {


                ps.setInt(
                        1,
                        bookingId);


                try (ResultSet rs =
                        ps.executeQuery()) {


                    if (rs.next()) {

                        int existingPaymentId =
                                rs.getInt(
                                        "payment_id");


                        response.setStatus(
                                HttpServletResponse.SC_CONFLICT);

                        out.print(
                            "{\"success\":false,"
                          + "\"message\":\"Payment already confirmed for this booking.\","
                          + "\"paymentId\":"
                          + existingPaymentId
                          + "}"
                        );

                        return;
                    }
                }
            }


            // ----------------------------------
            // CREATE PAYMENT
            // ----------------------------------

            String paymentSql =
                    "INSERT INTO payments "
                  + "(booking_id, amount, payment_method, "
                  + "transaction_reference, payment_status) "
                  + "VALUES (?, ?, ?, ?, 'CONFIRMED')";


            int paymentId = 0;


            try (Connection con =
                        DBConnection.getConnection();

                 PreparedStatement ps =
                        con.prepareStatement(
                                paymentSql,
                                java.sql.Statement.RETURN_GENERATED_KEYS)) {


                ps.setInt(
                        1,
                        bookingId);

                ps.setDouble(
                        2,
                        amount);

                ps.setString(
                        3,
                        paymentMethod);

                ps.setString(
                        4,
                        transactionReference);


                int rows =
                        ps.executeUpdate();


                if (rows > 0) {

                    try (ResultSet rs =
                            ps.getGeneratedKeys()) {

                        if (rs.next()) {

                            paymentId =
                                    rs.getInt(1);
                        }
                    }
                }
            }


            // ----------------------------------
            // PAYMENT FAILED
            // ----------------------------------

            if (paymentId <= 0) {

                response.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                out.print(
                    "{\"success\":false,\"message\":\"Payment could not be created.\"}"
                );

                return;
            }


            // ----------------------------------
            // UPDATE BOOKING STATUS
            // ----------------------------------

            String updateBookingSql =
                    "UPDATE bookings "
                  + "SET booking_status = 'CONFIRMED' "
                  + "WHERE booking_id = ?";


            try (Connection con =
                        DBConnection.getConnection();

                 PreparedStatement ps =
                        con.prepareStatement(
                                updateBookingSql)) {


                ps.setInt(
                        1,
                        bookingId);

                ps.executeUpdate();
            }


            // ----------------------------------
            // SUCCESS RESPONSE
            // ----------------------------------

            response.setStatus(
                    HttpServletResponse.SC_OK);


            out.print(
                "{"
              + "\"success\":true,"
              + "\"message\":\"Payment successful.\","
              + "\"paymentId\":"
              + paymentId
              + ","
              + "\"bookingId\":"
              + bookingId
              + ","
              + "\"amount\":"
              + amount
              + ","
              + "\"paymentMethod\":\""
              + escapeJson(paymentMethod)
              + "\","
              + "\"paymentStatus\":\"CONFIRMED\""
              + "}"
            );


        } catch (NumberFormatException e) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            out.print(
                "{\"success\":false,"
              + "\"message\":\"Invalid booking ID or payment amount.\"}"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            out.print(
                "{\"success\":false,"
              + "\"message\":\"Payment server error: "
              + escapeJson(
                    e.getMessage() == null
                        ? "Unknown error"
                        : e.getMessage())
              + "\"}"
            );

        } finally {

            out.flush();
        }
    }


    // ==========================================
    // GET PAYMENT
    // ==========================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();


        String paymentIdText =
                request.getParameter(
                        "paymentId");


        if (paymentIdText == null ||
                paymentIdText.trim().isEmpty()) {

            out.print(
                "{\"success\":false,"
              + "\"message\":\"Payment ID is required.\"}"
            );

            return;
        }


        try {

            int paymentId =
                    Integer.parseInt(
                            paymentIdText);


            String sql =
                    "SELECT payment_id, booking_id, amount, "
                  + "payment_method, transaction_reference, "
                  + "payment_status, payment_date "
                  + "FROM payments "
                  + "WHERE payment_id = ?";


            try (Connection con =
                        DBConnection.getConnection();

                 PreparedStatement ps =
                        con.prepareStatement(sql)) {


                ps.setInt(
                        1,
                        paymentId);


                try (ResultSet rs =
                        ps.executeQuery()) {


                    if (!rs.next()) {

                        response.setStatus(
                                HttpServletResponse.SC_NOT_FOUND);

                        out.print(
                            "{\"success\":false,"
                          + "\"message\":\"Payment not found.\"}"
                        );

                        return;
                    }


                    out.print(
                        "{"
                      + "\"success\":true,"
                      + "\"payment\":{"
                      + "\"paymentId\":"
                      + rs.getInt("payment_id")
                      + ","
                      + "\"bookingId\":"
                      + rs.getInt("booking_id")
                      + ","
                      + "\"amount\":"
                      + rs.getDouble("amount")
                      + ","
                      + "\"paymentMethod\":\""
                      + escapeJson(
                            rs.getString(
                                "payment_method"))
                      + "\","
                      + "\"transactionReference\":\""
                      + escapeJson(
                            rs.getString(
                                "transaction_reference"))
                      + "\","
                      + "\"paymentStatus\":\""
                      + escapeJson(
                            rs.getString(
                                "payment_status"))
                      + "\","
                      + "\"paymentDate\":\""
                      + escapeJson(
                            String.valueOf(
                                rs.getTimestamp(
                                    "payment_date")))
                      + "\""
                      + "}"
                      + "}"
                    );
                }
            }


        } catch (NumberFormatException e) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            out.print(
                "{\"success\":false,"
              + "\"message\":\"Invalid payment ID.\"}"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            out.print(
                "{\"success\":false,"
              + "\"message\":\"Unable to retrieve payment.\"}"
            );
        }
    }


    // ==========================================
    // JSON ESCAPE
    // ==========================================

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}