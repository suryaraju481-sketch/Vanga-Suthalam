package com.vangasuthalam.api;

import java.io.IOException;

import com.vangasuthalam.controller.CustomerLoginController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/customer/login")
public class CustomerLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CustomerLoginController controller =
            new CustomerLoginController();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Email and password are required\"}"
            );

            return;
        }

        try {

            int customerId =
                    controller.loginCustomer(email, password);

            if (customerId > 0) {

                response.setStatus(
                        HttpServletResponse.SC_OK);

                response.getWriter().write(
                        "{"
                        + "\"success\":true,"
                        + "\"message\":\"Login successful\","
                        + "\"customerId\":" + customerId
                        + "}"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter().write(
                        "{"
                        + "\"success\":false,"
                        + "\"message\":\"Invalid email or password\""
                        + "}"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().write(
                    "{"
                    + "\"success\":false,"
                    + "\"message\":\"Server error occurred\""
                    + "}"
            );
        }
    }
}