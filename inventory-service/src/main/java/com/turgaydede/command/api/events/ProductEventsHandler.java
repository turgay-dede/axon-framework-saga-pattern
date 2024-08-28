package com.turgaydede.command.api.events;

import com.turgaydede.command.api.data.ProductEntity;
import com.turgaydede.command.api.data.ProductRepository;
import com.turgaydede.events.StockUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity entity = new ProductEntity();

        BeanUtils.copyProperties(event, entity);

        productRepository.save(entity);
    }

    @EventHandler
    public void on(StockUpdatedEvent event) {
        ProductEntity entity = productRepository.findById(event.getProductId()).get();

        int newQuantity = entity.getQuantity() - event.getQuantity();

        entity.setQuantity(newQuantity);
        productRepository.save(entity);
    }
}
