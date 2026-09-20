package com.vangasuthalam.controller;

import com.vangasuthalam.model.Feedback;
import com.vangasuthalam.service.FeedbackService;

public class FeedbackController {

    private FeedbackService feedbackService;

    public FeedbackController() {
        feedbackService = new FeedbackService();
    }

    public boolean addFeedback(Feedback feedback) {

        return feedbackService.addFeedback(feedback);
    }
}