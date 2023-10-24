package net.nvsoftware.RabbitMQDemo.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.RabbitMQDemo.model.MsgRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConsumerServiceV2 {
    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consume(MsgRequest msgRequest){
        log.info("Received msgJson: " + msgRequest.toString());
    }
}
