package net.nvsoftware.OrderService.service;

import com.netflix.discovery.converters.Auto;
import net.nvsoftware.OrderService.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMsg(OrderEvent orderEvent) {
        Message<OrderEvent> msg = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, "order")
                .build();
        kafkaTemplate.send(msg);
    }
}
