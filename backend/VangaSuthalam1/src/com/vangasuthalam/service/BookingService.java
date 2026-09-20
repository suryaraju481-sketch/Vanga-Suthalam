package com.vangasuthalam.service;

import com.vangasuthalam.dao.BookingDAO;
import com.vangasuthalam.model.Booking;

public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService() {

        bookingDAO = new BookingDAO();

    }

    // ==========================================
    // CREATE BOOKING
    // ==========================================

    public int addBooking(Booking booking) {

        if (booking == null) {
            return 0;
        }

        if (booking.getCustomerId() <= 0) {
            return 0;
        }

        if (booking.getDestinationId() <= 0) {
            return 0;
        }

        if (booking.getPackageId() <= 0) {
            return 0;
        }

        if (booking.getBoatId() <= 0) {
            return 0;
        }

        if (booking.getBookingDate() == null) {
            return 0;
        }

        if (booking.getStartTime() == null) {
            return 0;
        }

        if (booking.getNumberOfPeople() <= 0) {
            return 0;
        }

        if (booking.getTotalAmount() <= 0) {
            return 0;
        }

        // Create booking and get generated booking ID
        return bookingDAO.addBooking(booking);
    }


    // ==========================================
    // VIEW MY BOOKINGS
    // ==========================================

    public void viewMyBookings(int customerId) {

        if (customerId <= 0) {

            System.out.println();
            System.out.println("Invalid Customer ID.");

            return;
        }

        bookingDAO.viewMyBookings(customerId);
    }
}