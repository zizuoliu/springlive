package net.nvsoftware.RabbitMQDemo.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.RabbitMQDemo.model.MsgRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProducerServiceV2 {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(MsgRequest msgRequest) {
        log.info("MsgJson sent to exchange: " + exchange + " with routingKey: " + routingKey + "with msg: " + msgRequest.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, msgRequest);
    }
}

