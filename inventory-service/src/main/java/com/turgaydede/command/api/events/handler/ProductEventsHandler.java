package com.turgaydede.command.api.events.handler;

import com.turgaydede.command.api.data.ProductEntity;
import com.turgaydede.command.api.data.ProductRepository;
import com.turgaydede.command.api.events.ProductCreatedEvent;
import com.turgaydede.events.ProductReservationCancelledEvent;
import com.turgaydede.events.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ProcessingGroup("product-group")
@Slf4j
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity entity = new ProductEntity();

        BeanUtils.copyProperties(event, entity);


        try {
            productRepository.save(entity);
        } catch (Exception exception) {
            log.error("ProductCreatedEvent: " + exception.getMessage());
            throw exception;
        }


    }
    @Transactional
    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity entity = productRepository.findById(event.getProductId()).get();

        int newQuantity = entity.getQuantity() - event.getQuantity();

        entity.setQuantity(newQuantity);

        try {
            productRepository.save(entity);

        } catch (Exception exception) {
            log.error("StockUpdatedEvent: " + exception.getMessage());
            throw exception;
        }

    }

    @Transactional
    @EventHandler
    public void on(ProductReservationCancelledEvent event) {
        ProductEntity entity = productRepository.findById(event.getProductId()).get();

        int newQuantity = entity.getQuantity() + event.getQuantity();

        entity.setQuantity(newQuantity);

        try {
            productRepository.save(entity);

        } catch (Exception exception) {
            log.error("ProductReservationCancelledEvent: " + exception.getMessage());
            throw exception;
        }

    }

    @ExceptionHandler(resultType = RuntimeException.class)
    public void handleError(Exception exception) throws Exception {
        log.error("ProductEventsHandler: " + exception.getMessage());
        throw exception;
    }
}
