package com.vangasuthalamm;

import com.vangasuthalam.controller.DestinationController;
import com.vangasuthalam.model.Destination;

public class TestDestinationController {

    public static void main(String[] args) {

        Destination destination = new Destination(
                "Rameswaram Sea Explorer",
                "Rameswaram",
                "Sea Exploration",
                "Approved marine exploration route near Rameswaram.",
                20.0,
                false,
                true
        );

        DestinationController controller = new DestinationController();

        boolean result = controller.addDestination(destination);

        if (result) {
            System.out.println("Destination Controller Test Successful!");
        } else {
            System.out.println("Destination Controller Test Failed!");
        }
    }
}