package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.SafetyCheck;
import com.vangasuthalam.util.DBConnection;

public class SafetyCheckDAO {

    public boolean addSafetyCheck(SafetyCheck safetyCheck) {

        String sql = "INSERT INTO safety_checks "
                   + "(booking_id, captain_approved, boat_available, "
                   + "passenger_capacity_ok, life_jackets_available, "
                   + "emergency_equipment_available, "
                   + "communication_equipment_available, "
                   + "weather_clearance, checked_by) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, safetyCheck.getBookingId());
            ps.setBoolean(2, safetyCheck.isCaptainApproved());
            ps.setBoolean(3, safetyCheck.isBoatAvailable());
            ps.setBoolean(4, safetyCheck.isPassengerCapacityOk());
            ps.setBoolean(5, safetyCheck.isLifeJacketsAvailable());
            ps.setBoolean(6, safetyCheck.isEmergencyEquipmentAvailable());
            ps.setBoolean(7, safetyCheck.isCommunicationEquipmentAvailable());
            ps.setBoolean(8, safetyCheck.isWeatherClearance());
            ps.setString(9, safetyCheck.getCheckedBy());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
