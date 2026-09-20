package com.vangasuthalamm;

import com.vangasuthalam.dao.SafetyCheckDAO;
import com.vangasuthalam.model.SafetyCheck;

public class TestSafetyCheckDAO {

    public static void main(String[] args) {

        SafetyCheck safetyCheck = new SafetyCheck(
                2,      // booking_id
                true,   // captain approved
                true,   // boat available
                true,   // passenger capacity OK
                true,   // life jackets available
                true,   // emergency equipment available
                true,   // communication equipment available
                true,   // weather clearance
                "Admin"
        );

        SafetyCheckDAO dao = new SafetyCheckDAO();

        boolean result = dao.addSafetyCheck(safetyCheck);

        if (result) {
            System.out.println("Safety Check Added Successfully!");
        } else {
            System.out.println("Safety Check Add Failed!");
        }
    }
}