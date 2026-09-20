package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.model.Destination;
import com.vangasuthalam.util.DBConnection;

public class DestinationDAO {

    // Add Destination
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


    // View normal available destinations
    public void viewAllDestinations() {

        String sql = "SELECT * FROM destinations "
                   + "WHERE status = 'ACTIVE' "
                   + "AND trip_allowed = TRUE "
                   + "AND destination_type <> 'Island Explorer'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("        AVAILABLE DESTINATIONS");
            System.out.println("==========================================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Destination ID  : "
                        + rs.getInt("destination_id"));

                System.out.println("Name            : "
                        + rs.getString("destination_name"));

                System.out.println("Location        : "
                        + rs.getString("location"));

                System.out.println("Type            : "
                        + rs.getString("destination_type"));

                System.out.println("Description     : "
                        + rs.getString("description"));

                System.out.println("Distance (KM)   : "
                        + rs.getDouble("distance_km"));

                System.out.println("Landing Allowed : "
                        + rs.getBoolean("landing_allowed"));

                System.out.println("------------------------------------------");
            }

            if (!found) {
                System.out.println("No destinations available.");
            }

            System.out.println("==========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // View protected Island Explorer destinations
    public void viewIslandDestinations() {

        String sql = "SELECT * FROM destinations "
                   + "WHERE status = 'ACTIVE' "
                   + "AND destination_type = 'Island Explorer'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("          ISLAND EXPLORER");
            System.out.println("==========================================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Island ID       : "
                        + rs.getInt("destination_id"));

                System.out.println("Island Name     : "
                        + rs.getString("destination_name"));

                System.out.println("Location        : "
                        + rs.getString("location"));

                System.out.println("Type            : "
                        + rs.getString("destination_type"));

                System.out.println("Description     : "
                        + rs.getString("description"));

                System.out.println("Distance (KM)   : "
                        + rs.getDouble("distance_km"));

                System.out.println("Landing Allowed : "
                        + rs.getBoolean("landing_allowed"));

                System.out.println("Trip Allowed    : "
                        + rs.getBoolean("trip_allowed"));

                System.out.println("Approval        : REQUIRED");

                System.out.println("------------------------------------------");
            }

            if (!found) {
                System.out.println("No Island Explorer destinations available.");
            }

            System.out.println("==========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}