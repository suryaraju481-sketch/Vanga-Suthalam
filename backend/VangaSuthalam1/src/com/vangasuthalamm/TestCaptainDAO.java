package com.vangasuthalamm;

import com.vangasuthalam.dao.CaptainDAO;
import com.vangasuthalam.model.Captain;

public class TestCaptainDAO {

    public static void main(String[] args) {

        Captain captain = new Captain(
                "Karthik",
                "9876543212",
                "karthik@gmail.com",
                "12345",
                6,
                "Marine Safety and Boat Handling"
        );

        CaptainDAO dao = new CaptainDAO();

        boolean result = dao.addCaptain(captain);

        if (result) {
            System.out.println("Captain Added Successfully!");
        } else {
            System.out.println("Captain Add Failed!");
        }
    }
}