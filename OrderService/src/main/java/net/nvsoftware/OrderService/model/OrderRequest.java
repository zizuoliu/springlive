package net.nvsoftware.OrderService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    private long productId;
    private long quantity;
    private long totalAmount;
    private String paymentMode;
}
