package net.nvsoftware.springmono.service;

import net.nvsoftware.springmono.error.ProductNotFoundException;
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

    @Override
    public List<Product> getAll() {
        return productList;
    }

    @Override
    public Product getById(String id) {
        return productList.stream().filter(product -> product.getProductId().equals(id))
                .findFirst()
                //.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public String deleteById(String id) {
        Product product = productList.stream().filter(e -> e.getProductId().equals(id)).findFirst().get();
        productList.remove(product);
        return "Deleted Product with id: " + id;
    }
}
