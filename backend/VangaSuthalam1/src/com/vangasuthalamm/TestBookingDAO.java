package com.vangasuthalamm;

import java.sql.Date;
import java.sql.Time;

import com.vangasuthalam.dao.BookingDAO;
import com.vangasuthalam.model.Booking;

public class TestBookingDAO {

    public static void main(String[] args) {

        Booking booking = new Booking(
                1,  // customer_id
                1,  // destination_id
                1,  // package_id
                1,  // boat_id
                Date.valueOf("2026-09-20"),
                Time.valueOf("08:00:00"),
                2,
                true,
                true,
                13000.00
        );

        BookingDAO dao = new BookingDAO();

        boolean result = dao.addBooking(booking);

        if (result) {
            System.out.println("Booking Added Successfully!");
        } else {
            System.out.println("Booking Add Failed!");
        }
    }
}