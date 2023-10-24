package net.nvsoftware.OrderService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
    private long orderId;
    private long productId;
    private long quantity;
    private String paymentMode;
    private long totalAmount;
}
