package net.nvsoftware.springmono.controller;

import net.nvsoftware.springmono.model.Product;
import net.nvsoftware.springmono.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("")
    public Product save(@RequestBody Product product) {
        productService.save(product);
        return product;
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable String id) {
        return productService.deleteById(id);
    }
}
