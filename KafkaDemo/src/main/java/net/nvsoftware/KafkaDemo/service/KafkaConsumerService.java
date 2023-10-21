package net.nvsoftware.KafkaDemo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumerService {
    @KafkaListener(topics = "amazon", groupId = "offerGroup")
    public void consume(String msg) {
        log.info("msg: " + msg + " receved from topic: amazon");
    }
}
