package com.vangasuthalam.controller;

import com.vangasuthalam.model.Payment;
import com.vangasuthalam.service.PaymentService;

public class PaymentController {

    private PaymentService paymentService;

    public PaymentController() {

        paymentService = new PaymentService();
    }

    public boolean addPayment(Payment payment) {

        return paymentService.addPayment(payment);
    }
}