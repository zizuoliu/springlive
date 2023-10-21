package net.nvsoftware.repository;

import net.nvsoftware.entity.WikiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiRepository extends JpaRepository<WikiEntity, Long> {
}
