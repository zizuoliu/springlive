package net.nvsoftware.RabbitMQDemo.controller;

import net.nvsoftware.RabbitMQDemo.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbit")
public class MsgController {
    @Autowired
    private ProducerService producerService;

    // http://localhost:8080/api/v1/rabbit/publish?msg=IGetOracleOffer
    @GetMapping("/publish")
    public ResponseEntity<String> sendMsg(@RequestParam String msg){
        producerService.sendMsg(msg);
        return ResponseEntity.ok("Msg sent to RabbitMQ");
    }
}
