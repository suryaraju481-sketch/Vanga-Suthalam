package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Destination;
import com.vangasuthalam.util.DBConnection;

public class DestinationDAO {

    public boolean addDestination(Destination destination) {

        String sql = "INSERT INTO destinations "
                   + "(destination_name, location, destination_type, "
                   + "description, distance_km, landing_allowed, trip_allowed) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, destination.getDestinationName());
            ps.setString(2, destination.getLocation());
            ps.setString(3, destination.getDestinationType());
            ps.setString(4, destination.getDescription());
            ps.setDouble(5, destination.getDistanceKm());
            ps.setBoolean(6, destination.isLandingAllowed());
            ps.setBoolean(7, destination.isTripAllowed());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}