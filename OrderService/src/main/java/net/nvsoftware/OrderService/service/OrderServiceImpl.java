package net.nvsoftware.OrderService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.OrderService.client.PaymentServiceFeignClient;
import net.nvsoftware.OrderService.client.ProductServiceFeignClient;
import net.nvsoftware.OrderService.entity.OrderEntity;
import net.nvsoftware.OrderService.model.OrderEvent;
import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.model.OrderResponse;
import net.nvsoftware.OrderService.model.PaymentRequest;
import net.nvsoftware.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;
    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @Autowired
    private OrderProducerService orderProducerService;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) { //TODO: transaction
        log.info("Start: OrderService placeOrder");
        // use OrderService to create OrderEntity with status CREATED, ORM JPA save to database
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .totalAmount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        orderRepository.save(orderEntity);
        log.info("Process: OrderService placeOrder save orderEntity with orderId: " + orderEntity.getId());

        // call ProductService to check product quantity, if ok, reduce it, else throw not enough
        productServiceFeignClient.reduceQuantity(orderEntity.getProductId(), orderEntity.getQuantity());
        log.info("Process: OrderService placeOrder FeignCall ProductService reduceQuantity");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderEntity.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .totalAmount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentServiceFeignClient.doPayment(paymentRequest);
            orderStatus = "PAID";
            log.info("Process: Service placeOrder FeignCall PaymentService doPayment PAID");
        } catch (Exception e) {
            orderStatus = "PAYMENT_FAILED";
            log.info("Process: Service placeOrder FeignCall PaymentService doPayment FAILED");
        }

        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);

        // call PaymentService to charge, if Success, mark order PAID, else CANCELLED
        log.info("End: OrderService placeOrder Done with orderId: " + orderEntity.getId());
        return orderEntity.getId();
    }

    @Override
    public long placeOrder2(OrderRequest orderRequest) {
        log.info("Start: OrderService placeOrder2");
        // use OrderService to create OrderEntity with status CREATED, ORM JPA save to database
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .totalAmount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        orderRepository.save(orderEntity);
        log.info("Process: OrderService placeOrder2 save orderEntity with orderId: " + orderEntity.getId());

        // call ProductService to check product quantity, if ok, reduce it, else throw not enough
        // call PaymentService to charge, if Success, mark order PAID, else CANCELLED
        // use kafka publish the orderEvent/orderMsg and let ProductService and PaymentService to consume
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(orderEntity.getId())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .paymentMode(orderRequest.getPaymentMode())
                .totalAmount(orderRequest.getTotalAmount())
                .build();
        orderProducerService.sendMsg(orderEvent);

        log.info("End: OrderService placeOrder2 Done with orderId: " + orderEntity.getId());
        return orderEntity.getId();
    }

    @Override
    public OrderResponse getOrderDetailById(long orderId) {
        log.info("Start: OrderService getOrderDetailById");
        OrderEntity orderEntity = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("OrderService getOrderDetailById: Order Not Found with id: " + orderId));

        OrderResponse.ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + orderEntity.getProductId(),
                OrderResponse.ProductResponse.class
        );

        OrderResponse.PaymentResponse paymentResponse  = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/" + orderEntity.getId(),
                OrderResponse.PaymentResponse.class
        );

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderEntity.getId())
                .orderStatus(orderEntity.getOrderStatus())
                .orderDate(orderEntity.getOrderDate())
                .totalAmount(orderEntity.getTotalAmount())
                .productResponse(productResponse)
                .paymentResponse(paymentResponse)
                .build();

        log.info("End: OrderService getOrderDetailById");
        return orderResponse;
    }
}
