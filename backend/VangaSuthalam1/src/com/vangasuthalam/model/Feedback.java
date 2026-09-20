package com.vangasuthalam.model;

import java.sql.Timestamp;

public class Feedback {

    private int feedbackId;
    private int bookingId;
    private int customerId;

    private int captainRating;
    private int boatRating;
    private int foodRating;
    private int safetyRating;
    private int experienceRating;
    private int overallRating;

    private String comments;
    private Timestamp feedbackDate;

    public Feedback() {
    }

    public Feedback(int bookingId, int customerId,
                    int captainRating, int boatRating,
                    int foodRating, int safetyRating,
                    int experienceRating, int overallRating,
                    String comments) {

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.captainRating = captainRating;
        this.boatRating = boatRating;
        this.foodRating = foodRating;
        this.safetyRating = safetyRating;
        this.experienceRating = experienceRating;
        this.overallRating = overallRating;
        this.comments = comments;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCaptainRating() {
        return captainRating;
    }

    public void setCaptainRating(int captainRating) {
        this.captainRating = captainRating;
    }

    public int getBoatRating() {
        return boatRating;
    }

    public void setBoatRating(int boatRating) {
        this.boatRating = boatRating;
    }

    public int getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(int foodRating) {
        this.foodRating = foodRating;
    }

    public int getSafetyRating() {
        return safetyRating;
    }

    public void setSafetyRating(int safetyRating) {
        this.safetyRating = safetyRating;
    }

    public int getExperienceRating() {
        return experienceRating;
    }

    public void setExperienceRating(int experienceRating) {
        this.experienceRating = experienceRating;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
