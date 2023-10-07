package net.nvsoftware.OrderService.service;

import net.nvsoftware.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
