package com.vangasuthalamm;

import com.vangasuthalam.model.Feedback;
import com.vangasuthalam.service.FeedbackService;

public class TestFeedbackService {

    public static void main(String[] args) {

        Feedback feedback = new Feedback(
                2,
                1,
                5,
                5,
                4,
                5,
                5,
                5,
                "Excellent trip. Captain was helpful and the safety arrangements were very good."
        );

        FeedbackService service = new FeedbackService();

        boolean result = service.addFeedback(feedback);

        if (result) {
            System.out.println("Feedback Service Test Successful!");
        } else {
            System.out.println("Feedback Service Test Failed!");
        }
    }
}
