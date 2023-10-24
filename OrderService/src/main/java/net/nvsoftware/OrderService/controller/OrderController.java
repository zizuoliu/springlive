package net.nvsoftware.OrderService.controller;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.model.OrderResponse;
import net.nvsoftware.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/placeOrder2")
    public ResponseEntity<Long> placeOrder2(@RequestBody OrderRequest orderRequest) {
        log.info("Start: OrderService Controller placeOrder2");
        long orderId = orderService.placeOrder2(orderRequest);
        log.info("End: OrderService Controller placeOrder2");
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetailById(@PathVariable long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetailById(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
