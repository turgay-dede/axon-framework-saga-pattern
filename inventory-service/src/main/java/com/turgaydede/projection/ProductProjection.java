package com.turgaydede.projection;

import com.turgaydede.command.api.data.ProductEntity;
import com.turgaydede.command.api.data.ProductRepository;
import com.turgaydede.model.ProductRestModel;
import com.turgaydede.queries.GetProductRestModelQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class ProductProjection {

    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public ProductRestModel handle(GetProductRestModelQuery getProductsQuery) {
        ProductEntity product =
                productRepository.findById(getProductsQuery.getProductId()).get();

        ProductRestModel productRestModel = ProductRestModel
                .builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        return productRestModel;
    }
}
