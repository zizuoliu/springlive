package net.nvsoftware.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumerService {
    @KafkaListener(topics = "wiki", groupId = "wikiGroup")
    public void consume(String msg) {
        log.info("Msg Received: " + msg);
    }
}
