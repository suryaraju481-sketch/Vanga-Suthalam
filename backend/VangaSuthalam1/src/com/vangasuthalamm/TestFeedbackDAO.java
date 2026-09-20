package com.vangasuthalamm;

import com.vangasuthalam.dao.FeedbackDAO;
import com.vangasuthalam.model.Feedback;

public class TestFeedbackDAO {

    public static void main(String[] args) {

        Feedback feedback = new Feedback(
                2,  // booking_id
                1,  // customer_id
                5,  // captain rating
                4,  // boat rating
                5,  // food rating
                5,  // safety rating
                5,  // experience rating
                5,  // overall rating
                "Excellent sea exploration experience. Captain was helpful and safety was good."
        );

        FeedbackDAO dao = new FeedbackDAO();

        boolean result = dao.addFeedback(feedback);

        if (result) {
            System.out.println("Feedback Added Successfully!");
        } else {
            System.out.println("Feedback Add Failed!");
        }
    }
}