package com.borsibaar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "adjusted_price", precision = 19, scale = 4)
    private BigDecimal adjustedPrice;

    @OneToMany(mappedBy = "inventory")
    private Set<InventoryTransaction> transactions = new HashSet<>();

    @Transient
    public Long getOrganizationId() {
        return product != null ? product.getOrganizationId() : null;
    }

    @Transient
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    // Custom constructor for easy creation
    public Inventory(
            Product product,
            BigDecimal quantity,
            BigDecimal adjustedPrice
    ) {
        this.product = product;
        this.quantity = quantity;
        this.adjustedPrice = adjustedPrice;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
