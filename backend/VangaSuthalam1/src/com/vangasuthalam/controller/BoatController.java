package com.vangasuthalam.controller;

import com.vangasuthalam.model.Boat;
import com.vangasuthalam.service.BoatService;

public class BoatController {

    private BoatService boatService;

    public BoatController() {

        boatService = new BoatService();
    }

    public boolean addBoat(Boat boat) {

        return boatService.addBoat(boat);
    }

    public void viewAllBoats() {

        boatService.viewAllBoats();
    }

    // Check boat availability and capacity
    public boolean isBoatAvailable(
            int boatId,
            int numberOfPeople) {

        return boatService.isBoatAvailable(
                boatId,
                numberOfPeople
        );
    }
}