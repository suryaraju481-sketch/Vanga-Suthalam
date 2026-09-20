package com.vangasuthalam.service;

import com.vangasuthalam.dao.PaymentDAO;
import com.vangasuthalam.model.Payment;

public class PaymentService {

    private PaymentDAO paymentDAO;

    public PaymentService() {

        paymentDAO = new PaymentDAO();

    }

    // ==========================================
    // ADD PAYMENT
    // ==========================================

    public boolean addPayment(Payment payment) {

        if (payment == null) {
            return false;
        }

        // ==========================================
        // BOOKING ID VALIDATION
        // ==========================================

        if (payment.getBookingId() <= 0) {
            return false;
        }

        // ==========================================
        // AMOUNT VALIDATION
        // ==========================================

        if (payment.getAmount() <= 0) {
            return false;
        }

        // ==========================================
        // PAYMENT METHOD VALIDATION
        // ==========================================

        if (payment.getPaymentMethod() == null ||
            payment.getPaymentMethod().trim().isEmpty()) {

            return false;
        }

        // ==========================================
        // TRANSACTION REFERENCE VALIDATION
        // ==========================================

        if (payment.getTransactionReference() == null ||
            payment.getTransactionReference().trim().isEmpty()) {

            return false;
        }

        // ==========================================
        // PAYMENT STATUS
        // ==========================================

        if (payment.getPaymentStatus() == null ||
            payment.getPaymentStatus().trim().isEmpty()) {

            return false;
        }

        // ==========================================
        // SEND TO DAO
        // ==========================================

        return paymentDAO.addPayment(payment);
    }
}