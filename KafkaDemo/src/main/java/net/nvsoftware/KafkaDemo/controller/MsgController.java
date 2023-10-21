package net.nvsoftware.KafkaDemo.controller;

import net.nvsoftware.KafkaDemo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class MsgController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String msg) {
        kafkaProducerService.sendMsg(msg);
        return new ResponseEntity<>("Msg sent to topic: amazon", HttpStatus.OK);
    }
}
