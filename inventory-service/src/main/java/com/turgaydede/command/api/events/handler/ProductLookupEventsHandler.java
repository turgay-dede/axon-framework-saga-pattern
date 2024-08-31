package com.turgaydede.command.api.events.handler;

import com.turgaydede.command.api.data.ProductLookupEntity;
import com.turgaydede.command.api.data.ProductLookupRepository;
import com.turgaydede.command.api.events.ProductCreatedEvent;
import com.turgaydede.events.StockUpdatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }


    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity entity = new ProductLookupEntity();

        BeanUtils.copyProperties(event, entity);

        productLookupRepository.save(entity);
    }

    @EventHandler
    public void on(StockUpdatedEvent event) {
        ProductLookupEntity entity = productLookupRepository.findById(event.getProductId()).get();

        int newQuantity = entity.getQuantity() - event.getQuantity();

        entity.setQuantity(newQuantity);
        productLookupRepository.save(entity);
    }
}
