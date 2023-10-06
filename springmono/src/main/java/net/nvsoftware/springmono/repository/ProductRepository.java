package net.nvsoftware.springmono.repository;

import net.nvsoftware.springmono.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
}
