package net.nvsoftware.OrderService.controller;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Start: OrderService Controller placeOrder");
        long orderId = orderService.placeOrder(orderRequest);
        log.info("End: OrderService Controller placeOrder");
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
}
