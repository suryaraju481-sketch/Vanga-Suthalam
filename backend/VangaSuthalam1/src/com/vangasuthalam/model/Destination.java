package com.vangasuthalam.model;

public class Destination {

    private int destinationId;
    private String destinationName;
    private String location;
    private String destinationType;
    private String description;
    private double distanceKm;
    private boolean landingAllowed;
    private boolean tripAllowed;
    private String status;

    public Destination() {
    }

    public Destination(String destinationName, String location,
                       String destinationType, String description,
                       double distanceKm, boolean landingAllowed,
                       boolean tripAllowed) {

        this.destinationName = destinationName;
        this.location = location;
        this.destinationType = destinationType;
        this.description = description;
        this.distanceKm = distanceKm;
        this.landingAllowed = landingAllowed;
        this.tripAllowed = tripAllowed;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public boolean isLandingAllowed() {
        return landingAllowed;
    }

    public void setLandingAllowed(boolean landingAllowed) {
        this.landingAllowed = landingAllowed;
    }

    public boolean isTripAllowed() {
        return tripAllowed;
    }

    public void setTripAllowed(boolean tripAllowed) {
        this.tripAllowed = tripAllowed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}