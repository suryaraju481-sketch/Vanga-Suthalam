package com.vangasuthalamm;

import com.vangasuthalam.controller.TripPackageController;
import com.vangasuthalam.model.TripPackage;

public class TestTripPackageController {

    public static void main(String[] args) {

        TripPackage tripPackage = new TripPackage(
                "Rameswaram Sea Adventure",
                1,
                0,
                "One day sea exploration with fishing experience.",
                2500.00,
                true,
                false
        );

        TripPackageController controller = new TripPackageController();

        boolean result = controller.addTripPackage(tripPackage);

        if (result) {
            System.out.println("Trip Package Controller Test Successful!");
        } else {
            System.out.println("Trip Package Controller Test Failed!");
        }
    }
}