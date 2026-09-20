package com.vangasuthalam.controller;

import com.vangasuthalam.model.Trip;
import com.vangasuthalam.service.TripService;

public class TripController {

    private TripService tripService;

    public TripController() {
        tripService = new TripService();
    }

    public boolean createTrip(Trip trip) {
        return tripService.createTrip(trip);
    }
}