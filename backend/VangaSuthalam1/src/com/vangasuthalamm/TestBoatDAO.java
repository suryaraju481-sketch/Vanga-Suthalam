package com.vangasuthalamm;

import com.vangasuthalam.dao.BoatDAO;
import com.vangasuthalam.model.Boat;

public class TestBoatDAO {

    public static void main(String[] args) {

        Boat boat = new Boat(
                3,
                "Sea Pearl",
                "TN-RMD-002",
                "Motor Boat",
                8,
                "Diesel Engine",
                "Life Jackets, First Aid Kit, Fire Extinguisher",
                "TN Marine Registration 002"
        );

        BoatDAO dao = new BoatDAO();

        boolean result = dao.addBoat(boat);

        if (result) {
            System.out.println("Boat Added Successfully!");
        } else {
            System.out.println("Boat Add Failed!");
        }
    }
}