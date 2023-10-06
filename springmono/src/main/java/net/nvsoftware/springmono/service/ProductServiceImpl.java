package net.nvsoftware.springmono.service;

import net.nvsoftware.springmono.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    List<Product> productList = new ArrayList<>();
    @Override
    public Product save(Product product) {
        if(product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productList.add(product);
        return product;
    }
}
