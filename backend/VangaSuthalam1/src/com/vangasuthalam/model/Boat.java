package com.vangasuthalam.model;

public class Boat {

    private int boatId;
    private int captainId;
    private String boatName;
    private String boatNumber;
    private String boatType;
    private int capacity;
    private String engineDetails;
    private String safetyEquipment;
    private String registrationDetails;
    private String status;

    public Boat() {
    }

    public Boat(int captainId, String boatName, String boatNumber,
                String boatType, int capacity, String engineDetails,
                String safetyEquipment, String registrationDetails) {

        this.captainId = captainId;
        this.boatName = boatName;
        this.boatNumber = boatNumber;
        this.boatType = boatType;
        this.capacity = capacity;
        this.engineDetails = engineDetails;
        this.safetyEquipment = safetyEquipment;
        this.registrationDetails = registrationDetails;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getBoatNumber() {
        return boatNumber;
    }

    public void setBoatNumber(String boatNumber) {
        this.boatNumber = boatNumber;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getEngineDetails() {
        return engineDetails;
    }

    public void setEngineDetails(String engineDetails) {
        this.engineDetails = engineDetails;
    }

    public String getSafetyEquipment() {
        return safetyEquipment;
    }

    public void setSafetyEquipment(String safetyEquipment) {
        this.safetyEquipment = safetyEquipment;
    }

    public String getRegistrationDetails() {
        return registrationDetails;
    }

    public void setRegistrationDetails(String registrationDetails) {
        this.registrationDetails = registrationDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}