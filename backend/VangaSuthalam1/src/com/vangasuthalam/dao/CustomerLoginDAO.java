
package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.util.DBConnection;

public class CustomerLoginDAO {

    public int loginCustomer(String email, String password) {

        String sql = "SELECT customer_id FROM customers "
                   + "WHERE email = ? AND password = ? "
                   + "AND status = 'ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("customer_id");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}

