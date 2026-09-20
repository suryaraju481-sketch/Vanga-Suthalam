package com.vangasuthalamm;

import com.vangasuthalam.dao.TripPackageDAO;
import com.vangasuthalam.model.TripPackage;

public class TestTripPackageDAO {

    public static void main(String[] args) {

        TripPackage tripPackage = new TripPackage(
                "Marine Adventure - 2 Days",
                2,
                1,
                "Two day marine exploration with fishing and food.",
                6500.00,
                true,
                true
        );

        TripPackageDAO dao = new TripPackageDAO();

        boolean result = dao.addTripPackage(tripPackage);

        if (result) {
            System.out.println("Trip Package Added Successfully!");
        } else {
            System.out.println("Trip Package Add Failed!");
        }
    }
}