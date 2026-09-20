package com.vangasuthalamm;

import com.vangasuthalam.controller.BoatController;
import com.vangasuthalam.model.Boat;

public class TestBoatController {

    public static void main(String[] args) {

        Boat boat = new Boat(
                3,
                "Sea Pearl",
                "TN-RMD-010",
                "Passenger Boat",
                8,
                "Marine Diesel Engine",
                "Life Jackets, First Aid Kit, Fire Extinguisher",
                "TN-RMD-REG-010"
        );

        BoatController controller = new BoatController();

        boolean result = controller.registerBoat(boat);

        if (result) {
            System.out.println("Boat Controller Test Successful!");
        } else {
            System.out.println("Boat Controller Test Failed!");
        }
    }
}