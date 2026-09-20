package com.vangasuthalamm;

import com.vangasuthalam.controller.CaptainController;
import com.vangasuthalam.model.Captain;

public class TestCaptainController {

    public static void main(String[] args) {

        Captain captain = new Captain(
                "Ramesh Kumar",
                "9876543216",
                "rameshkumar16@gmail.com",
                "Ramesh@123",
                8,
                "Certified Boat Captain"
        );

        CaptainController controller = new CaptainController();

        boolean result = controller.registerCaptain(captain);

        if (result) {
            System.out.println("Captain Controller Test Successful!");
        } else {
            System.out.println("Captain Controller Test Failed!");
        }
    }
}
