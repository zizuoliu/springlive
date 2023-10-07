package net.nvsoftware.ProductService.service;

import net.nvsoftware.ProductService.model.ProductRequest;

public interface ProductService {
    long addProduct(ProductRequest productRequest);
}
