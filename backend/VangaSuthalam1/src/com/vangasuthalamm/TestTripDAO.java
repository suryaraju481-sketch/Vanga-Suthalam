package com.vangasuthalamm;

import java.sql.Timestamp;

import com.vangasuthalam.dao.TripDAO;
import com.vangasuthalam.model.Trip;

public class TestTripDAO {

    public static void main(String[] args) {

        Trip trip = new Trip(
                2,  // booking_id
                3,  // captain_id
                1,  // boat_id
                Timestamp.valueOf("2026-09-20 08:00:00"),
                Timestamp.valueOf("2026-09-21 17:00:00")
        );

        TripDAO dao = new TripDAO();

        boolean result = dao.addTrip(trip);

        if (result) {
            System.out.println("Trip Added Successfully!");
        } else {
            System.out.println("Trip Add Failed!");
        }
    }
}
