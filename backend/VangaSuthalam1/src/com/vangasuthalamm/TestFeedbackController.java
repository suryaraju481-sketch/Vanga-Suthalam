package com.vangasuthalamm;

import com.vangasuthalam.controller.FeedbackController;
import com.vangasuthalam.model.Feedback;

public class TestFeedbackController {

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
                "Excellent sea trip. Captain was helpful and the experience was very enjoyable."
        );

        FeedbackController controller = new FeedbackController();

        boolean result = controller.addFeedback(feedback);

        if (result) {
            System.out.println("Feedback Controller Test Successful!");
        } else {
            System.out.println("Feedback Controller Test Failed!");
        }
    }
}