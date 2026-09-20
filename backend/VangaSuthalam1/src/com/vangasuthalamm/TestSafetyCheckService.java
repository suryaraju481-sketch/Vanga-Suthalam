package com.vangasuthalamm;

import com.vangasuthalam.model.SafetyCheck;
import com.vangasuthalam.service.SafetyCheckService;

public class TestSafetyCheckService {

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

        SafetyCheckService service = new SafetyCheckService();

        boolean result = service.performSafetyCheck(safetyCheck);

        if (result) {
            System.out.println("Safety Check Service Test Successful!");
        } else {
            System.out.println("Safety Check Service Test Failed!");
        }
    }
}