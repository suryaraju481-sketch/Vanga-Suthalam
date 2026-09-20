package com.vangasuthalamm;

import com.vangasuthalam.dao.DestinationDAO;
import com.vangasuthalam.model.Destination;

public class TestDestinationDAO {

    public static void main(String[] args) {

        Destination destination = new Destination(
                "Dhanushkodi Sea Route",
                "Rameswaram",
                "Sea Exploration",
                "Sea exploration route around the Rameswaram and Dhanushkodi area.",
                18.5,
                false,
                true
        );

        DestinationDAO dao = new DestinationDAO();

        boolean result = dao.addDestination(destination);

        if (result) {
            System.out.println("Destination Added Successfully!");
        } else {
            System.out.println("Destination Add Failed!");
        }
    }
}