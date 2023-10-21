package net.nvsoftware.KafkaDemo.controller;

import net.nvsoftware.KafkaDemo.model.MsgRequest;
import net.nvsoftware.KafkaDemo.service.KafkaProducerService;
import net.nvsoftware.KafkaDemo.service.KafkaProducerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/kafka")
public class MsgControllerV2 {
    @Autowired
    private KafkaProducerServiceV2 kafkaProducerServiceV2;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody MsgRequest msgRequest) {
        kafkaProducerServiceV2.sendMsg(msgRequest);
        return new ResponseEntity<>("Msg sent to topic: google", HttpStatus.OK);
    }
}
