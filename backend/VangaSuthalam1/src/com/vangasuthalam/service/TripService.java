package com.vangasuthalam.service;

import com.vangasuthalam.dao.TripDAO;
import com.vangasuthalam.model.Trip;

public class TripService {

    private TripDAO tripDAO;

    public TripService() {
        tripDAO = new TripDAO();
    }

    public boolean createTrip(Trip trip) {

        if (trip == null) {
            return false;
        }

        if (trip.getBookingId() <= 0) {
            return false;
        }

        if (trip.getCaptainId() <= 0) {
            return false;
        }

        if (trip.getBoatId() <= 0) {
            return false;
        }

        if (trip.getTripStartDatetime() == null) {
            return false;
        }

        if (trip.getTripEndDatetime() == null) {
            return false;
        }

        if (trip.getTripEndDatetime()
                .before(trip.getTripStartDatetime())) {
            return false;
        }

        return tripDAO.addTrip(trip);
    }
}
