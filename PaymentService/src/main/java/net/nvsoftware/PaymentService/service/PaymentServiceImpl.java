package net.nvsoftware.PaymentService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.PaymentService.entity.PaymentEntity;
import net.nvsoftware.PaymentService.model.PaymentRequest;
import net.nvsoftware.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Start: PaymentService doPayment");

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMode(paymentRequest.getPaymentMode())
                .totalAmount(paymentRequest.getTotalAmount())
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .build();
        paymentRepository.save(paymentEntity);

        log.info("End: PaymentService doPayment with id: " + paymentEntity.getId());
        return paymentEntity.getId();
    }
}
