package net.nvsoftware.KafkaDemo.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.KafkaDemo.model.MsgRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerServiceV2 {
    @Autowired
    private KafkaTemplate<String, MsgRequest> kafkaTemplate;

    public void sendMsg(MsgRequest msg) {
        log.info("Service msg: " + msg + " sent to topic: google");
        kafkaTemplate.send("google", msg);
    }
}
