package com.vangasuthalamm;

import com.vangasuthalam.model.Payment;
import com.vangasuthalam.service.PaymentService;

public class TestPaymentService {

    public static void main(String[] args) {

        Payment payment = new Payment(
                2,
                1300.00,
                "UPI",
                "VANGA-TXN-2005"
        );

        PaymentService service = new PaymentService();

        boolean result = service.makePayment(payment);

        if (result) {
            System.out.println("Payment Service Test Successful!");
        } else {
            System.out.println("Payment Service Test Failed!");
        }
    }
}
