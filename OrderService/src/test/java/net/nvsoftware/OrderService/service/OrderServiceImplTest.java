package net.nvsoftware.OrderService.service;

import net.nvsoftware.OrderService.client.PaymentServiceFeignClient;
import net.nvsoftware.OrderService.client.ProductServiceFeignClient;
import net.nvsoftware.OrderService.entity.OrderEntity;
import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.model.OrderResponse;
import net.nvsoftware.OrderService.model.PaymentRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DisplayName("GetOrderDetailById - Failed")
    @Test
    void testWhenGetOrderDetailByIdFailed() {
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                orderService.getOrderDetailById(1)
        );
        Assertions.assertEquals("OrderService getOrderDetailById: Order Not Found with id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("PlaceOrder - Success")
    void testWhenPlaceOrderSuccess() {
        OrderEntity orderEntity = getMockOrderEntity();
        OrderRequest orderRequest = getMockOrderRequest();

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(orderEntity);
        Mockito.when(productServiceFeignClient.reduceQuantity(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Mockito.when(paymentServiceFeignClient.doPayment(Mockito.any(PaymentRequest.class)))
                .thenReturn(new ResponseEntity<Long>(1L, HttpStatus.OK));

        long orderId = orderService.placeOrder(orderRequest);

        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(productServiceFeignClient, Mockito.times(1)).reduceQuantity(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(paymentServiceFeignClient, Mockito.times(1)).doPayment(Mockito.any(PaymentRequest.class));

        Assertions.assertEquals(orderEntity.getId(), orderId);
    }

    @Test
    @DisplayName("PlaceOrder Failed")
    void testWhenPlaceOrderPaymentFailed() {
        OrderEntity orderEntity = getMockOrderEntity();
        OrderRequest orderRequest = getMockOrderRequest();

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class)))
                .thenReturn(orderEntity);
        Mockito.when(productServiceFeignClient.reduceQuantity(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        Mockito.when(paymentServiceFeignClient.doPayment(Mockito.any(PaymentRequest.class)))
                .thenThrow(new RuntimeException("Payment Failed"));

        long orderId = orderService.placeOrder(orderRequest);

        Mockito.verify(orderRepository, Mockito.times(2)).save(Mockito.any());
        Mockito.verify(productServiceFeignClient, Mockito.times(1)).reduceQuantity(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(paymentServiceFeignClient, Mockito.times(1)).doPayment(Mockito.any(PaymentRequest.class));

        Assertions.assertEquals(orderEntity.getId(), orderId);
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

    private OrderRequest getMockOrderRequest() {
        return OrderRequest.builder()
                .productId(1)
                .quantity(1)
                .totalAmount(1299)
                .paymentMode("CASH")
                .build();
    }

}