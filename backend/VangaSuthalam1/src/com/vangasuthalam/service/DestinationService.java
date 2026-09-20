package com.vangasuthalam.service;

import com.vangasuthalam.dao.DestinationDAO;
import com.vangasuthalam.model.Destination;

public class DestinationService {

    private DestinationDAO destinationDAO;

    public DestinationService() {
        destinationDAO = new DestinationDAO();
    }

    // Add Destination
    public boolean addDestination(Destination destination) {

        if (destination == null) {
            return false;
        }

        if (destination.getDestinationName() == null ||
            destination.getDestinationName().trim().isEmpty()) {
            return false;
        }

        if (destination.getLocation() == null ||
            destination.getLocation().trim().isEmpty()) {
            return false;
        }

        if (destination.getDestinationType() == null ||
            destination.getDestinationType().trim().isEmpty()) {
            return false;
        }

        if (destination.getDescription() == null ||
            destination.getDescription().trim().isEmpty()) {
            return false;
        }

        if (destination.getDistanceKm() <= 0) {
            return false;
        }

        return destinationDAO.addDestination(destination);
    }

    // View normal destinations
    public void viewAllDestinations() {

        destinationDAO.viewAllDestinations();
    }

    // View Island Explorer destinations
    public void viewIslandDestinations() {

        destinationDAO.viewIslandDestinations();
    }
}