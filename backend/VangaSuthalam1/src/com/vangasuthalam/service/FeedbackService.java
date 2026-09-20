package com.vangasuthalam.service;

import com.vangasuthalam.dao.FeedbackDAO;
import com.vangasuthalam.model.Feedback;

public class FeedbackService {

    private FeedbackDAO feedbackDAO;

    public FeedbackService() {
        feedbackDAO = new FeedbackDAO();
    }

    public boolean addFeedback(Feedback feedback) {

        if (feedback == null) {
            return false;
        }

        if (feedback.getBookingId() <= 0) {
            return false;
        }

        if (feedback.getCustomerId() <= 0) {
            return false;
        }

        if (feedback.getCaptainRating() < 1 ||
            feedback.getCaptainRating() > 5) {
            return false;
        }

        if (feedback.getBoatRating() < 1 ||
            feedback.getBoatRating() > 5) {
            return false;
        }

        if (feedback.getFoodRating() < 1 ||
            feedback.getFoodRating() > 5) {
            return false;
        }

        if (feedback.getSafetyRating() < 1 ||
            feedback.getSafetyRating() > 5) {
            return false;
        }

        if (feedback.getExperienceRating() < 1 ||
            feedback.getExperienceRating() > 5) {
            return false;
        }

        if (feedback.getOverallRating() < 1 ||
            feedback.getOverallRating() > 5) {
            return false;
        }

        if (feedback.getComments() == null ||
            feedback.getComments().trim().isEmpty()) {
            return false;
        }

        return feedbackDAO.addFeedback(feedback);
    }
}