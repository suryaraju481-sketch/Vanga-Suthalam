package com.vangasuthalam.service;

import com.vangasuthalam.dao.TripPackageDAO;
import com.vangasuthalam.model.TripPackage;

public class TripPackageService {

    private TripPackageDAO tripPackageDAO;

    public TripPackageService() {

        tripPackageDAO = new TripPackageDAO();
    }

    public boolean addTripPackage(TripPackage tripPackage) {

        if (tripPackage == null) {
            return false;
        }

        if (tripPackage.getPackageName() == null ||
            tripPackage.getPackageName().trim().isEmpty()) {

            return false;
        }

        if (tripPackage.getDurationDays() <= 0 ||
            tripPackage.getDurationDays() > 2) {

            System.out.println();
            System.out.println(
                    "Package duration must be between 1 and 2 days."
            );

            return false;
        }

        if (tripPackage.getDurationNights() < 0 ||
            tripPackage.getDurationNights() > 1) {

            System.out.println();
            System.out.println(
                    "Package duration can have maximum 1 night."
            );

            return false;
        }

        if (tripPackage.getDescription() == null ||
            tripPackage.getDescription().trim().isEmpty()) {

            return false;
        }

        if (tripPackage.getBasePricePerPerson() <= 0) {

            return false;
        }

        return tripPackageDAO.addTripPackage(tripPackage);
    }

    public void viewAllPackages() {

        tripPackageDAO.viewAllPackages();
    }

    // Get package price from database
    public double getPackagePrice(int packageId) {

        if (packageId <= 0) {

            return 0;
        }

        return tripPackageDAO.getPackagePrice(packageId);
    }
}