package com.vangasuthalamm;

import java.sql.Timestamp;

import com.vangasuthalam.controller.TripController;
import com.vangasuthalam.model.Trip;

public class TestTripController {

    public static void main(String[] args) {

        Trip trip = new Trip(
                2,
                3,
                1,
                Timestamp.valueOf("2026-10-15 09:00:00"),
                Timestamp.valueOf("2026-10-15 17:00:00")
        );

        TripController controller = new TripController();

        boolean result = controller.createTrip(trip);

        if (result) {
            System.out.println("Trip Controller Test Successful!");
        } else {
            System.out.println("Trip Controller Test Failed!");
        }
    }
}