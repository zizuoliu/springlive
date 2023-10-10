package net.nvsoftware.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long id;
    private long orderId;
    private String paymentStatus;
    private String paymentMode;
    private Instant paymentDate;
    private long totalAmount;
}

