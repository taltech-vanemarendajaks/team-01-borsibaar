package com.borsibaar.repository;

import com.borsibaar.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct_OrganizationIdAndProduct_Id(Long organizationId, Long productId);

    List<Inventory> findByProduct_OrganizationId(Long organizationId);

    List<Inventory> findByProduct_OrganizationIdAndProduct_CategoryId(
            Long organizationId,
            Long categoryId
    );
}
