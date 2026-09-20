package com.vangasuthalamm;

import com.vangasuthalam.model.Captain;
import com.vangasuthalam.service.CaptainService;

public class TestCaptainService {

    public static void main(String[] args) {

        Captain captain = new Captain(
                "Suresh",
                "9876543298",
                "suresh98@gmail.com",
                "suresh123",
                8,
                "Marine Safety and Boat Handling"
        );

        CaptainService service = new CaptainService();

        boolean result = service.registerCaptain(captain);

        if (result) {
            System.out.println("Captain Service Test Successful!");
        } else {
            System.out.println("Captain Service Test Failed!");
        }
    }
}