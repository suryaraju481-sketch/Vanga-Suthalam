package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vangasuthalam.model.Boat;
import com.vangasuthalam.util.DBConnection;

public class BoatDAO {

    public boolean addBoat(Boat boat) {

        String sql = "INSERT INTO boats "
                   + "(captain_id, boat_name, boat_number, boat_type, "
                   + "capacity, engine_details, safety_equipment, "
                   + "registration_details) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, boat.getCaptainId());
            ps.setString(2, boat.getBoatName());
            ps.setString(3, boat.getBoatNumber());
            ps.setString(4, boat.getBoatType());
            ps.setInt(5, boat.getCapacity());
            ps.setString(6, boat.getEngineDetails());
            ps.setString(7, boat.getSafetyEquipment());
            ps.setString(8, boat.getRegistrationDetails());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewAllBoats() {

        String sql = "SELECT * FROM boats";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("              BOAT LIST");
            System.out.println("==========================================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Boat ID             : "
                        + rs.getInt("boat_id"));

                System.out.println("Captain ID          : "
                        + rs.getInt("captain_id"));

                System.out.println("Boat Name           : "
                        + rs.getString("boat_name"));

                System.out.println("Boat Number         : "
                        + rs.getString("boat_number"));

                System.out.println("Boat Type           : "
                        + rs.getString("boat_type"));

                System.out.println("Capacity            : "
                        + rs.getInt("capacity"));

                System.out.println("Engine Details      : "
                        + rs.getString("engine_details"));

                System.out.println("Safety Equipment    : "
                        + rs.getString("safety_equipment"));

                System.out.println("Registration Details: "
                        + rs.getString("registration_details"));

                System.out.println("Status              : "
                        + rs.getString("status"));

                System.out.println("------------------------------------------");
            }

            if (!found) {
                System.out.println("No boats found.");
            }

            System.out.println("==========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check boat availability and capacity
    public boolean isBoatAvailable(int boatId, int numberOfPeople) {

        String sql = "SELECT capacity, status "
                   + "FROM boats "
                   + "WHERE boat_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, boatId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {

                    System.out.println();
                    System.out.println("Boat ID not found.");

                    return false;
                }

                int capacity =
                        rs.getInt("capacity");

                String status =
                        rs.getString("status");

                if (!"AVAILABLE".equalsIgnoreCase(status)) {

                    System.out.println();
                    System.out.println(
                            "Selected boat is not available."
                    );

                    return false;
                }

                if (numberOfPeople > capacity) {

                    System.out.println();
                    System.out.println(
                            "Passenger count exceeds boat capacity."
                    );

                    System.out.println(
                            "Boat Capacity : " + capacity
                    );

                    System.out.println(
                            "Passengers    : " + numberOfPeople
                    );

                    return false;
                }

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}