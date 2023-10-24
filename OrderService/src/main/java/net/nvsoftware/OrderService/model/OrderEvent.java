package net.nvsoftware.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
    private long orderId;
    private long productId;
    private long quantity;
    private String paymentMode;
    private long totalAmount;
}
