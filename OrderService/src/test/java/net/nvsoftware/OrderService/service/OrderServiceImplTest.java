package net.nvsoftware.OrderService.service;

import net.nvsoftware.OrderService.client.PaymentServiceFeignClient;
import net.nvsoftware.OrderService.client.ProductServiceFeignClient;
import net.nvsoftware.OrderService.entity.OrderEntity;
import net.nvsoftware.OrderService.model.OrderResponse;
import net.nvsoftware.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductServiceFeignClient productServiceFeignClient;
    @Mock
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("GetOrderDetailById - Success")
    @Test
    void testWhenGetOrderDetailByIdSuccess() {
        // Mock
        OrderEntity orderEntity = getMockOrderEntity();
        Mockito.when(orderRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(orderEntity));
        Mockito.when(restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + orderEntity.getProductId(),
                OrderResponse.ProductResponse.class
        )).thenReturn(getMockProductResponse());
        Mockito.when(restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/" + orderEntity.getId(),
                OrderResponse.PaymentResponse.class
        )).thenReturn(getMockPaymentResponse());

        // Actual Call
        OrderResponse orderResponse = orderService.getOrderDetailById(1); // only actual call without mock will error

        // Verify Call
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
                "http://PRODUCT-SERVICE/product/" + orderEntity.getProductId(),
                OrderResponse.ProductResponse.class
        );
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(
                "http://PAYMENT-SERVICE/payment/" + orderEntity.getId(),
                OrderResponse.PaymentResponse.class
        );

        // Assert Result
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(orderEntity.getId(), orderResponse.getOrderId());
    }

    

    // Mock Part: Add Mock Data
    private OrderResponse.PaymentResponse getMockPaymentResponse() {
        return OrderResponse.PaymentResponse.builder()
                .orderId(7)
                .id(1)
                .paymentDate(Instant.now())
                .paymentMode("VISA")
                .paymentStatus("SUCCESS")
                .totalAmount(2999)
                .build();
    }

    private OrderResponse.ProductResponse getMockProductResponse() {
        return OrderResponse.ProductResponse.builder()
                .id(2)
                .name("MacMini")
                .quantity(2)
                .price(1499)
                .build();
    }

    private OrderEntity getMockOrderEntity() {
        return OrderEntity.builder()
                .id(7)
                .productId(2)
                .quantity(2)
                .totalAmount(2999)
                .orderDate(Instant.now())
                .orderStatus("PLACED")
                .build();
    }

}