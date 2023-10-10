package net.nvsoftware.PaymentService.service;

import net.nvsoftware.PaymentService.model.PaymentRequest;
import net.nvsoftware.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailByOrderId(long orderId);
}
