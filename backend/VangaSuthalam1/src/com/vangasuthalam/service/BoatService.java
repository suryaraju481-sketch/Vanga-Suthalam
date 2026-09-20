package com.vangasuthalam.service;

import com.vangasuthalam.dao.BoatDAO;
import com.vangasuthalam.model.Boat;

public class BoatService {

    private BoatDAO boatDAO;

    public BoatService() {

        boatDAO = new BoatDAO();
    }

    public boolean addBoat(Boat boat) {

        if (boat == null) {
            return false;
        }

        if (boat.getCaptainId() <= 0) {
            return false;
        }

        if (boat.getBoatName() == null ||
            boat.getBoatName().trim().isEmpty()) {

            return false;
        }

        if (boat.getBoatNumber() == null ||
            boat.getBoatNumber().trim().isEmpty()) {

            return false;
        }

        if (boat.getCapacity() <= 0) {

            return false;
        }

        return boatDAO.addBoat(boat);
    }

    public void viewAllBoats() {

        boatDAO.viewAllBoats();
    }

    // Check boat availability and capacity
    public boolean isBoatAvailable(
            int boatId,
            int numberOfPeople) {

        if (boatId <= 0) {

            System.out.println("Invalid Boat ID.");

            return false;
        }

        if (numberOfPeople <= 0) {

            System.out.println(
                    "Number of people must be greater than 0."
            );

            return false;
        }

        return boatDAO.isBoatAvailable(
                boatId,
                numberOfPeople
        );
    }
}