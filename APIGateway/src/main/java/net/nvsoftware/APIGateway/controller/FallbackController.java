package net.nvsoftware.APIGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/orderServiceFallback")
    public String orderServiceFallback() {
        return "Order Service Down";
    }

    @GetMapping("/productServiceFallback")
    public String productServiceFallback() {
        return "Product Service Down";
    }

    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback() {
        return "Payment Service Down";
    }
}
