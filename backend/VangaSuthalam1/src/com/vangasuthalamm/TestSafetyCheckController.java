package com.vangasuthalamm;

import com.vangasuthalam.controller.SafetyCheckController;
import com.vangasuthalam.model.SafetyCheck;

public class TestSafetyCheckController {

    public static void main(String[] args) {

        SafetyCheck safetyCheck = new SafetyCheck(
                2,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                "Admin"
        );

        SafetyCheckController controller =
                new SafetyCheckController();

        boolean result =
                controller.performSafetyCheck(safetyCheck);

        if (result) {
            System.out.println(
                    "Safety Check Controller Test Successful!"
            );
        } else {
            System.out.println(
                    "Safety Check Controller Test Failed!"
            );
        }
    }
}