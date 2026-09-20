package com.vangasuthalam.model;

public class TripPackage {

    private int packageId;
    private String packageName;
    private int durationDays;
    private int durationNights;
    private String description;
    private double basePricePerPerson;
    private boolean fishingIncluded;
    private boolean foodIncluded;
    private String status;

    public TripPackage() {
    }

    public TripPackage(String packageName, int durationDays,
                       int durationNights, String description,
                       double basePricePerPerson,
                       boolean fishingIncluded,
                       boolean foodIncluded) {

        this.packageName = packageName;
        this.durationDays = durationDays;
        this.durationNights = durationNights;
        this.description = description;
        this.basePricePerPerson = basePricePerPerson;
        this.fishingIncluded = fishingIncluded;
        this.foodIncluded = foodIncluded;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getDurationNights() {
        return durationNights;
    }

    public void setDurationNights(int durationNights) {
        this.durationNights = durationNights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePricePerPerson() {
        return basePricePerPerson;
    }

    public void setBasePricePerPerson(double basePricePerPerson) {
        this.basePricePerPerson = basePricePerPerson;
    }

    public boolean isFishingIncluded() {
        return fishingIncluded;
    }

    public void setFishingIncluded(boolean fishingIncluded) {
        this.fishingIncluded = fishingIncluded;
    }

    public boolean isFoodIncluded() {
        return foodIncluded;
    }

    public void setFoodIncluded(boolean foodIncluded) {
        this.foodIncluded = foodIncluded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}