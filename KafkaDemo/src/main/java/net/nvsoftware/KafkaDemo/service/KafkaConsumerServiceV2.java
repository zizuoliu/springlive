package net.nvsoftware.KafkaDemo.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.KafkaDemo.model.MsgRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumerServiceV2 {
    @KafkaListener(topics = "google", groupId = "offerGroup")
    public void consume(MsgRequest msgRequest) {
        log.info("msg: " + msgRequest.toString() + " receved from topic: google");
    }
}

