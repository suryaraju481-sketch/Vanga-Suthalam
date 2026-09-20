package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Trip;
import com.vangasuthalam.util.DBConnection;

public class TripDAO {

    public boolean addTrip(Trip trip) {

        String sql = "INSERT INTO trips "
                   + "(booking_id, captain_id, boat_id, "
                   + "trip_start_datetime, trip_end_datetime) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trip.getBookingId());
            ps.setInt(2, trip.getCaptainId());
            ps.setInt(3, trip.getBoatId());
            ps.setTimestamp(4, trip.getTripStartDatetime());
            ps.setTimestamp(5, trip.getTripEndDatetime());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}