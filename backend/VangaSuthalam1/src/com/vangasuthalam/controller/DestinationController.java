package com.vangasuthalam.controller;

import com.vangasuthalam.model.Destination;
import com.vangasuthalam.service.DestinationService;

public class DestinationController {

    private DestinationService destinationService;

    public DestinationController() {
        destinationService = new DestinationService();
    }

    // Add Destination
    public boolean addDestination(Destination destination) {

        return destinationService.addDestination(destination);
    }

    // View normal destinations
    public void viewAllDestinations() {

        destinationService.viewAllDestinations();
    }

    // View Island Explorer destinations
    public void viewIslandDestinations() {

        destinationService.viewIslandDestinations();
    }
}