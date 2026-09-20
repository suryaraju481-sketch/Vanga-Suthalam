package com.vangasuthalamm;

import com.vangasuthalam.model.Boat;
import com.vangasuthalam.service.BoatService;

public class TestBoatService {

    public static void main(String[] args) {

        Boat boat = new Boat(
                3,
                "Ocean Star",
                "TN-RMD-009",
                "Motor Boat",
                10,
                "Diesel Engine",
                "Life Jackets, First Aid Kit, Fire Extinguisher",
                "TN Marine Registration 009"
        );

        BoatService service = new BoatService();

        boolean result = service.registerBoat(boat);

        if (result) {
            System.out.println("Boat Service Test Successful!");
        } else {
            System.out.println("Boat Service Test Failed!");
        }
    }
}
