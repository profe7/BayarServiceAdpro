package id.ac.ui.cs.advprog.bayarservice.service.payment;

import id.ac.ui.cs.advprog.bayarservice.dto.payment.PaymentRequest;
import id.ac.ui.cs.advprog.bayarservice.model.payment.PaymentHistory;

import java.util.List;

public interface Payment {
    List<String> getPaymentMethods();
    PaymentHistory create(Integer invoiceId, PaymentRequest request);
}