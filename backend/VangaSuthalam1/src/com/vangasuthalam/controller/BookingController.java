package com.vangasuthalam.controller;

import com.vangasuthalam.model.Booking;
import com.vangasuthalam.service.BookingService;

public class BookingController {

    private BookingService bookingService;

    public BookingController() {

        bookingService = new BookingService();

    }

    // ==========================================
    // CREATE BOOKING
    // ==========================================

    public int addBooking(Booking booking) {

        return bookingService.addBooking(booking);

    }


    // ==========================================
    // VIEW MY BOOKINGS
    // ==========================================

    public void viewMyBookings(int customerId) {

        bookingService.viewMyBookings(customerId);

    }

}