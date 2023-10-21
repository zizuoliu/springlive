package net.nvsoftware.KafkaDemo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMsg(String msg) {
        log.info("msg: " + msg + " sent to topic: amazon");
        for (int i = 0; i < 10000; i++) {
            kafkaTemplate.send("amazon", msg + i);
        }
    }
}
