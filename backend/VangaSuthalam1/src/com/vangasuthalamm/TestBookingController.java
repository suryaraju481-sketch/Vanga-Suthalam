package com.vangasuthalamm;

import java.sql.Date;
import java.sql.Time;

import com.vangasuthalam.controller.BookingController;
import com.vangasuthalam.model.Booking;

public class TestBookingController {

    public static void main(String[] args) {

        Booking booking = new Booking(
                1,
                1,
                1,
                1,
                Date.valueOf("2026-10-15"),
                Time.valueOf("09:00:00"),
                2,
                true,
                false,
                5000.00
        );

        BookingController controller = new BookingController();

        boolean result = controller.createBooking(booking);

        if (result) {
            System.out.println("Booking Controller Test Successful!");
        } else {
            System.out.println("Booking Controller Test Failed!");
        }
    }
}