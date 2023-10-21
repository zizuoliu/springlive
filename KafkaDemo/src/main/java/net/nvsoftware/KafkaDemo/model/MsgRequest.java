package net.nvsoftware.KafkaDemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgRequest {
    private long orderId;
    private long totalAmount;
    private String paymentMode;

    @Override
    public String toString() {
        return "msgRequest{" +
                "orderId=" + orderId +
                ", totalAmount=" + totalAmount +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}
