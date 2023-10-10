package net.nvsoftware.PaymentService.controller;

import net.nvsoftware.PaymentService.model.PaymentRequest;
import net.nvsoftware.PaymentService.model.PaymentResponse;
import net.nvsoftware.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        long paymentId = paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailByOrderId(@PathVariable long orderId) {
        PaymentResponse paymentResponse = paymentService.getPaymentDetailByOrderId(orderId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
