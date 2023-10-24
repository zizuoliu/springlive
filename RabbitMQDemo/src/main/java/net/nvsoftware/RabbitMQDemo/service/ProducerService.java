package net.nvsoftware.RabbitMQDemo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProducerService {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        log.info("Msg sent to exchange: " + exchange + " with routingKey: " + routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }
}
