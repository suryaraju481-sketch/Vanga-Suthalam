package com.vangasuthalam.model;

import java.sql.Timestamp;

public class Trip {

    private int tripId;
    private int bookingId;
    private int captainId;
    private int boatId;

    private Timestamp tripStartDatetime;
    private Timestamp tripEndDatetime;

    private String tripStatus;

    public Trip() {
    }

    public Trip(int bookingId, int captainId, int boatId,
                Timestamp tripStartDatetime,
                Timestamp tripEndDatetime) {

        this.bookingId = bookingId;
        this.captainId = captainId;
        this.boatId = boatId;
        this.tripStartDatetime = tripStartDatetime;
        this.tripEndDatetime = tripEndDatetime;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public Timestamp getTripStartDatetime() {
        return tripStartDatetime;
    }

    public void setTripStartDatetime(Timestamp tripStartDatetime) {
        this.tripStartDatetime = tripStartDatetime;
    }

    public Timestamp getTripEndDatetime() {
        return tripEndDatetime;
    }

    public void setTripEndDatetime(Timestamp tripEndDatetime) {
        this.tripEndDatetime = tripEndDatetime;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}