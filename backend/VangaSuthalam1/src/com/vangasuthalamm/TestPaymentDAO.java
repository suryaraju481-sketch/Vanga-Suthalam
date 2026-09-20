package com.vangasuthalamm;

import com.vangasuthalam.dao.PaymentDAO;
import com.vangasuthalam.model.Payment;

public class TestPaymentDAO {

    public static void main(String[] args) {

        Payment payment = new Payment(
                1,
                13000.00,
                "UPI",
                "VANGA-TXN-1001"
        );

        PaymentDAO dao = new PaymentDAO();

        boolean result = dao.addPayment(payment);

        if (result) {
            System.out.println("Payment Added Successfully!");
        } else {
            System.out.println("Payment Add Failed!");
        }
    }
}
