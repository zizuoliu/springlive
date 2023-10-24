package net.nvsoftware.ProductService.model;

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

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", paymentMode='" + paymentMode + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
