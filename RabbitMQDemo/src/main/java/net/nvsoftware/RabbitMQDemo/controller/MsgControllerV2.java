package net.nvsoftware.RabbitMQDemo.controller;

import net.nvsoftware.RabbitMQDemo.model.MsgRequest;
import net.nvsoftware.RabbitMQDemo.service.ProducerService;
import net.nvsoftware.RabbitMQDemo.service.ProducerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/rabbit")
public class MsgControllerV2 {

    @Autowired
    private ProducerServiceV2 producerServiceV2;

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody MsgRequest msgRequest){
        producerServiceV2.sendMsg(msgRequest);
        return ResponseEntity.ok("MsgJson sent to RabbitMQ");
    }
}

