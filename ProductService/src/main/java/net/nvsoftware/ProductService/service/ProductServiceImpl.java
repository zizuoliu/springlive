package net.nvsoftware.ProductService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.ProductService.entity.ProductEntity;
import net.nvsoftware.ProductService.model.ProductRequest;
import net.nvsoftware.ProductService.model.ProductResponse;
import net.nvsoftware.ProductService.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Start: ProductService addProduct");
        ProductEntity productEntity = ProductEntity.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(productEntity);
        log.info("End: ProductService addProduct");
        return productEntity.getId();
    }

    @Override
    public ProductResponse getById(long id) {
        log.info("Start: ProductService getById");
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductService getById Not Found with id: " + id));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productEntity, productResponse);
        log.info("End: ProductService getById");
        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("Start: ProductService reduceQuantity with id: " + id + " quantity: " + quantity);

        ProductEntity productEntity = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("reduceQuantity: Product Not Found with id: " + id));

        if (productEntity.getQuantity() < quantity) {
            throw new RuntimeException("reduceQuantity: Product Not Enough with id: " + id);
        }

        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        log.info("End: ProductService reduceQuantity with id: " + id + " quantity: " + quantity);
    }
}
