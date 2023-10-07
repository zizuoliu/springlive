package net.nvsoftware.ProductService.service;

import net.nvsoftware.ProductService.entity.ProductEntity;
import net.nvsoftware.ProductService.model.ProductRequest;
import net.nvsoftware.ProductService.model.ProductResponse;
import net.nvsoftware.ProductService.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        ProductEntity productEntity = ProductEntity.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(productEntity);
        return productEntity.getId();
    }

    @Override
    public ProductResponse getById(long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductService getById Not Found with id: " + id));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productEntity, productResponse);
        return productResponse;
    }
}
