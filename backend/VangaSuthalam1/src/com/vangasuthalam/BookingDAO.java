package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Booking;
import com.vangasuthalam.util.DBConnection;

public class BookingDAO {

    public boolean addBooking(Booking booking) {

        String sql = "INSERT INTO bookings "
                   + "(customer_id, destination_id, package_id, boat_id, "
                   + "booking_date, start_time, number_of_people, "
                   + "fishing_required, food_required, total_amount) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, booking.getCustomerId());
            ps.setInt(2, booking.getDestinationId());
            ps.setInt(3, booking.getPackageId());
            ps.setInt(4, booking.getBoatId());
            ps.setDate(5, booking.getBookingDate());
            ps.setTime(6, booking.getStartTime());
            ps.setInt(7, booking.getNumberOfPeople());
            ps.setBoolean(8, booking.isFishingRequired());
            ps.setBoolean(9, booking.isFoodRequired());
            ps.setDouble(10, booking.getTotalAmount());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}