package com.vangasuthalamm;

import com.vangasuthalam.model.TripPackage;
import com.vangasuthalam.service.TripPackageService;

public class TestTripPackageService {

    public static void main(String[] args) {

        TripPackage tripPackage = new TripPackage(
                "Rameswaram Ocean Explorer",
                1,
                0,
                "One day sea exploration experience with food and fishing.",
                4500.00,
                true,
                true
        );

        TripPackageService service = new TripPackageService();

        boolean result = service.addTripPackage(tripPackage);

        if (result) {
            System.out.println("Trip Package Service Test Successful!");
        } else {
            System.out.println("Trip Package Service Test Failed!");
        }
    }
}