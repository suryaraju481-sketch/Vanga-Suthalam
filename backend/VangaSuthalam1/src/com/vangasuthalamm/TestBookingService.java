package com.vangasuthalamm;

import java.sql.Date;
import java.sql.Time;

import com.vangasuthalam.model.Booking;
import com.vangasuthalam.service.BookingService;

public class TestBookingService {

    public static void main(String[] args) {

        Booking booking = new Booking(
                1,
                1,
                1,
                1,
                Date.valueOf("2026-10-05"),
                Time.valueOf("09:00:00"),
                3,
                true,
                true,
                13500.00
        );

        BookingService service = new BookingService();

        boolean result = service.createBooking(booking);

        if (result) {
            System.out.println("Booking Service Test Successful!");
        } else {
            System.out.println("Booking Service Test Failed!");
        }
    }
}