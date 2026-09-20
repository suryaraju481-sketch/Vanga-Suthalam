package com.vangasuthalamm;

import java.sql.Timestamp;

import com.vangasuthalam.model.Trip;
import com.vangasuthalam.service.TripService;

public class TestTripService {

    public static void main(String[] args) {

        Trip trip = new Trip(
                2,
                3,
                1,
                Timestamp.valueOf("2026-10-05 09:00:00"),
                Timestamp.valueOf("2026-10-06 17:00:00")
        );

        TripService service = new TripService();

        boolean result = service.createTrip(trip);

        if (result) {
            System.out.println("Trip Service Test Successful!");
        } else {
            System.out.println("Trip Service Test Failed!");
        }
    }
}