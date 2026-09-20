package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.Feedback;
import com.vangasuthalam.util.DBConnection;

public class FeedbackDAO {

    public boolean addFeedback(Feedback feedback) {

        String sql = "INSERT INTO feedback "
                   + "(booking_id, customer_id, captain_rating, "
                   + "boat_rating, food_rating, safety_rating, "
                   + "experience_rating, overall_rating, comments) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, feedback.getBookingId());
            ps.setInt(2, feedback.getCustomerId());
            ps.setInt(3, feedback.getCaptainRating());
            ps.setInt(4, feedback.getBoatRating());
            ps.setInt(5, feedback.getFoodRating());
            ps.setInt(6, feedback.getSafetyRating());
            ps.setInt(7, feedback.getExperienceRating());
            ps.setInt(8, feedback.getOverallRating());
            ps.setString(9, feedback.getComments());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}