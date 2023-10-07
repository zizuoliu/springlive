package net.nvsoftware.OrderService.repository;

import net.nvsoftware.OrderService.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
