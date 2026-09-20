package com.vangasuthalam.api;

import java.io.IOException;

import com.vangasuthalam.controller.CustomerController;
import com.vangasuthalam.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/customer/register")
public class CustomerRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CustomerController controller =
            new CustomerController();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

        // Validate required fields
        if (name == null || name.trim().isEmpty()
                || mobile == null || mobile.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    "{"
                    + "\"success\":false,"
                    + "\"message\":\"Name, mobile, email and password are required\""
                    + "}"
            );

            return;
        }

        try {

            // Create Customer object
            Customer customer = new Customer();

            customer.setName(name);
            customer.setMobile(mobile);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setAddress(address);

            // Call Controller
            boolean registered =
                    controller.registerCustomer(customer);

            if (registered) {

                response.setStatus(
                        HttpServletResponse.SC_CREATED);

                response.getWriter().write(
                        "{"
                        + "\"success\":true,"
                        + "\"message\":\"Customer registered successfully\""
                        + "}"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST);

                response.getWriter().write(
                        "{"
                        + "\"success\":false,"
                        + "\"message\":\"Customer registration failed\""
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
                    + "\"message\":\"Server error occurred during registration\""
                    + "}"
            );
        }
    }
}