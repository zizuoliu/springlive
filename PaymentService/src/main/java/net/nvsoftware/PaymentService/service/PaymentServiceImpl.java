package net.nvsoftware.PaymentService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.OrderService.model.OrderEvent;
import net.nvsoftware.PaymentService.entity.PaymentEntity;
import net.nvsoftware.PaymentService.model.PaymentRequest;
import net.nvsoftware.PaymentService.model.PaymentResponse;
import net.nvsoftware.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "order", groupId = "payment")
    public void doPayment2(OrderEvent orderEvent) { // consume
        log.info("Consume: PaymentService doPayment2");

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .orderId(orderEvent.getOrderId())
                .paymentMode(orderEvent.getPaymentMode())
                .totalAmount(orderEvent.getTotalAmount())
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .build();
        paymentRepository.save(paymentEntity);

        log.info("Consume: PaymentService doPayment with id: " + paymentEntity.getId());
    }

    @Override
    public PaymentResponse getPaymentDetailByOrderId(long orderId) {
        log.info("Start: PaymentService getPaymentDetailByOrderId");
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(paymentEntity.getId())
                .orderId(paymentEntity.getOrderId())
                .paymentStatus(paymentEntity.getPaymentStatus())
                .paymentMode(paymentEntity.getPaymentMode())
                .totalAmount(paymentEntity.getTotalAmount())
                .paymentDate(paymentEntity.getPaymentDate())
                .build();
        log.info("End: PaymentService getPaymentDetailByOrderId");
        return paymentResponse;
    }
}
