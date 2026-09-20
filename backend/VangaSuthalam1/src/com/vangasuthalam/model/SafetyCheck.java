package com.vangasuthalam.model;

import java.sql.Timestamp;

public class SafetyCheck {

    private int safetyCheckId;
    private int bookingId;

    private boolean captainApproved;
    private boolean boatAvailable;
    private boolean passengerCapacityOk;
    private boolean lifeJacketsAvailable;
    private boolean emergencyEquipmentAvailable;
    private boolean communicationEquipmentAvailable;
    private boolean weatherClearance;

    private String safetyStatus;
    private String checkedBy;
    private Timestamp checkedDate;

    public SafetyCheck() {
    }

    public SafetyCheck(int bookingId,
                       boolean captainApproved,
                       boolean boatAvailable,
                       boolean passengerCapacityOk,
                       boolean lifeJacketsAvailable,
                       boolean emergencyEquipmentAvailable,
                       boolean communicationEquipmentAvailable,
                       boolean weatherClearance,
                       String checkedBy) {

        this.bookingId = bookingId;
        this.captainApproved = captainApproved;
        this.boatAvailable = boatAvailable;
        this.passengerCapacityOk = passengerCapacityOk;
        this.lifeJacketsAvailable = lifeJacketsAvailable;
        this.emergencyEquipmentAvailable = emergencyEquipmentAvailable;
        this.communicationEquipmentAvailable = communicationEquipmentAvailable;
        this.weatherClearance = weatherClearance;
        this.checkedBy = checkedBy;
    }

    public int getSafetyCheckId() {
        return safetyCheckId;
    }

    public void setSafetyCheckId(int safetyCheckId) {
        this.safetyCheckId = safetyCheckId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isCaptainApproved() {
        return captainApproved;
    }

    public void setCaptainApproved(boolean captainApproved) {
        this.captainApproved = captainApproved;
    }

    public boolean isBoatAvailable() {
        return boatAvailable;
    }

    public void setBoatAvailable(boolean boatAvailable) {
        this.boatAvailable = boatAvailable;
    }

    public boolean isPassengerCapacityOk() {
        return passengerCapacityOk;
    }

    public void setPassengerCapacityOk(boolean passengerCapacityOk) {
        this.passengerCapacityOk = passengerCapacityOk;
    }

    public boolean isLifeJacketsAvailable() {
        return lifeJacketsAvailable;
    }

    public void setLifeJacketsAvailable(boolean lifeJacketsAvailable) {
        this.lifeJacketsAvailable = lifeJacketsAvailable;
    }

    public boolean isEmergencyEquipmentAvailable() {
        return emergencyEquipmentAvailable;
    }

    public void setEmergencyEquipmentAvailable(boolean emergencyEquipmentAvailable) {
        this.emergencyEquipmentAvailable = emergencyEquipmentAvailable;
    }

    public boolean isCommunicationEquipmentAvailable() {
        return communicationEquipmentAvailable;
    }

    public void setCommunicationEquipmentAvailable(boolean communicationEquipmentAvailable) {
        this.communicationEquipmentAvailable = communicationEquipmentAvailable;
    }

    public boolean isWeatherClearance() {
        return weatherClearance;
    }

    public void setWeatherClearance(boolean weatherClearance) {
        this.weatherClearance = weatherClearance;
    }

    public String getSafetyStatus() {
        return safetyStatus;
    }

    public void setSafetyStatus(String safetyStatus) {
        this.safetyStatus = safetyStatus;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public Timestamp getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Timestamp checkedDate) {
        this.checkedDate = checkedDate;
    }
}