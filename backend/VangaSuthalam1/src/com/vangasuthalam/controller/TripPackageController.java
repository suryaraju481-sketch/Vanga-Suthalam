package com.vangasuthalam.controller;

import com.vangasuthalam.model.TripPackage;
import com.vangasuthalam.service.TripPackageService;

public class TripPackageController {

    private TripPackageService tripPackageService;

    public TripPackageController() {

        tripPackageService = new TripPackageService();
    }

    public boolean addTripPackage(TripPackage tripPackage) {

        return tripPackageService.addTripPackage(tripPackage);
    }

    public void viewAllPackages() {

        tripPackageService.viewAllPackages();
    }

    // Get package price from database
    public double getPackagePrice(int packageId) {

        return tripPackageService.getPackagePrice(packageId);
    }
}