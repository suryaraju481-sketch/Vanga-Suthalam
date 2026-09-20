package com.vangasuthalam.model;

import java.sql.Date;
import java.sql.Time;

public class Booking {

    private int bookingId;
    private int customerId;
    private int destinationId;
    private int packageId;

    // Island approval ID
    private Integer approvalId;

    private int boatId;
    private Date bookingDate;
    private Time startTime;
    private int numberOfPeople;
    private boolean fishingRequired;
    private boolean foodRequired;
    private double totalAmount;
    private String bookingStatus;

    public Booking() {
    }

    public Booking(int customerId, int destinationId, int packageId,
                   int boatId, Date bookingDate, Time startTime,
                   int numberOfPeople, boolean fishingRequired,
                   boolean foodRequired, double totalAmount) {

        this.customerId = customerId;
        this.destinationId = destinationId;
        this.packageId = packageId;
        this.boatId = boatId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.numberOfPeople = numberOfPeople;
        this.fishingRequired = fishingRequired;
        this.foodRequired = foodRequired;
        this.totalAmount = totalAmount;
    }

    // ==========================================
    // BOOKING ID
    // ==========================================

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    // ==========================================
    // CUSTOMER ID
    // ==========================================

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // ==========================================
    // DESTINATION ID
    // ==========================================

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    // ==========================================
    // PACKAGE ID
    // ==========================================

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    // ==========================================
    // ISLAND APPROVAL ID
    // ==========================================

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    // ==========================================
    // BOAT ID
    // ==========================================

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    // ==========================================
    // BOOKING DATE
    // ==========================================

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    // ==========================================
    // START TIME
    // ==========================================

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    // ==========================================
    // NUMBER OF PEOPLE
    // ==========================================

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    // ==========================================
    // FISHING
    // ==========================================

    public boolean isFishingRequired() {
        return fishingRequired;
    }

    public void setFishingRequired(boolean fishingRequired) {
        this.fishingRequired = fishingRequired;
    }

    // ==========================================
    // FOOD
    // ==========================================

    public boolean isFoodRequired() {
        return foodRequired;
    }

    public void setFoodRequired(boolean foodRequired) {
        this.foodRequired = foodRequired;
    }

    // ==========================================
    // TOTAL AMOUNT
    // ==========================================

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ==========================================
    // BOOKING STATUS
    // ==========================================

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}