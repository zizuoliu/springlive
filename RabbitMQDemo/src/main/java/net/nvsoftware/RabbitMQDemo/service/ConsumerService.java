package net.nvsoftware.RabbitMQDemo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConsumerService {
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String msg) {
        log.info("Received msg: " + msg);
    }
}
