package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.model.TripPackage;
import com.vangasuthalam.util.DBConnection;

public class TripPackageDAO {

    public boolean addTripPackage(TripPackage tripPackage) {

        String sql = "INSERT INTO trip_packages "
                   + "(package_name, duration_days, duration_nights, "
                   + "description, base_price_per_person, "
                   + "fishing_included, food_included) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tripPackage.getPackageName());
            ps.setInt(2, tripPackage.getDurationDays());
            ps.setInt(3, tripPackage.getDurationNights());
            ps.setString(4, tripPackage.getDescription());
            ps.setDouble(5, tripPackage.getBasePricePerPerson());
            ps.setBoolean(6, tripPackage.isFishingIncluded());
            ps.setBoolean(7, tripPackage.isFoodIncluded());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewAllPackages() {

        String sql = "SELECT * FROM trip_packages "
                   + "WHERE status = 'ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("          AVAILABLE TRIP PACKAGES");
            System.out.println("==========================================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Package ID       : "
                        + rs.getInt("package_id"));

                System.out.println("Package Name     : "
                        + rs.getString("package_name"));

                System.out.println("Duration         : "
                        + rs.getInt("duration_days")
                        + " Day(s) / "
                        + rs.getInt("duration_nights")
                        + " Night(s)");

                System.out.println("Description      : "
                        + rs.getString("description"));

                System.out.println("Price / Person   : ₹"
                        + rs.getDouble("base_price_per_person"));

                System.out.println("Fishing Included : "
                        + rs.getBoolean("fishing_included"));

                System.out.println("Food Included    : "
                        + rs.getBoolean("food_included"));

                System.out.println("------------------------------------------");
            }

            if (!found) {
                System.out.println("No trip packages available.");
            }

            System.out.println("==========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get package price from database
    public double getPackagePrice(int packageId) {

        String sql = "SELECT base_price_per_person "
                   + "FROM trip_packages "
                   + "WHERE package_id = ? "
                   + "AND status = 'ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, packageId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getDouble("base_price_per_person");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}