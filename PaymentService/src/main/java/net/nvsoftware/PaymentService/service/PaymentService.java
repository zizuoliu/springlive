package net.nvsoftware.PaymentService.service;

import net.nvsoftware.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
