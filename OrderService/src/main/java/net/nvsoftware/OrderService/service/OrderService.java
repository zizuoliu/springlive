package net.nvsoftware.OrderService.service;

import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    long placeOrder2(OrderRequest orderRequest);

    OrderResponse getOrderDetailById(long orderId);
}
