package net.nvsoftware.PaymentService.repository;

import net.nvsoftware.PaymentService.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderId(long orderId);
}
