package com.vangasuthalamm;

import com.vangasuthalam.model.Destination;
import com.vangasuthalam.service.DestinationService;

public class TestDestinationService {

    public static void main(String[] args) {

        Destination destination = new Destination(
                "Pamban Marine Explorer",
                "Rameswaram",
                "Marine Exploration",
                "Marine exploration route near the Pamban area.",
                15.0,
                false,
                true
        );

        DestinationService service = new DestinationService();

        boolean result = service.addDestination(destination);

        if (result) {
            System.out.println("Destination Service Test Successful!");
        } else {
            System.out.println("Destination Service Test Failed!");
        }
    }
}