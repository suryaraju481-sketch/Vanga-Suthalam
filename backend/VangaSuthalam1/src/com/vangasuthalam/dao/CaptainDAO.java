package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Captain;
import com.vangasuthalam.util.DBConnection;

public class CaptainDAO {

    public boolean addCaptain(Captain captain) {

        String sql = "INSERT INTO captains "
                   + "(name, mobile, email, password, experience_years, qualification) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, captain.getName());
            ps.setString(2, captain.getMobile());
            ps.setString(3, captain.getEmail());
            ps.setString(4, captain.getPassword());
            ps.setInt(5, captain.getExperienceYears());
            ps.setString(6, captain.getQualification());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}