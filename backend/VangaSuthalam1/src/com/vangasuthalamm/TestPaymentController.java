package com.vangasuthalamm;

import com.vangasuthalam.controller.PaymentController;
import com.vangasuthalam.model.Payment;

public class TestPaymentController {

    public static void main(String[] args) {

        Payment payment = new Payment(
                2,
                5000.00,
                "UPI",
                "VANGA-TXN-1002"
        );

        PaymentController controller = new PaymentController();

        boolean result = controller.makePayment(payment);

        if (result) {
            System.out.println("Payment Controller Test Successful!");
        } else {
            System.out.println("Payment Controller Test Failed!");
        }
    }
}
