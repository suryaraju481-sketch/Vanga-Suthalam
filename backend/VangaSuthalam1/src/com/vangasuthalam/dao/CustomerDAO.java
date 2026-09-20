package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Customer;
import com.vangasuthalam.util.DBConnection;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {

        String sql = "INSERT INTO customers "
                   + "(name, mobile, email, password, address) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMobile());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getAddress());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}