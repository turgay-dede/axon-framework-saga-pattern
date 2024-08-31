package com.turgaydede.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {
    Optional<ProductLookupEntity> findByProductIdOrProductName(String productId, String productName);

}
