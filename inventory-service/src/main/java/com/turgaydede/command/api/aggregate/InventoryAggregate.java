package com.turgaydede.command.api.aggregate;

import com.turgaydede.command.CancelProductReservationCommand;
import com.turgaydede.command.CheckInventoryCommand;
import com.turgaydede.command.UpdateStockCommand;
import com.turgaydede.command.CreateProductCommand;
import com.turgaydede.command.api.events.ProductCreatedEvent;
import com.turgaydede.events.ProductReservationCancelledEvent;
import com.turgaydede.events.StockNotAvailableEvent;
import com.turgaydede.events.InventoryDeductedEvent;
import com.turgaydede.events.StockUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class InventoryAggregate {

    @AggregateIdentifier
    private String productId;
    private int availableStock;
    private boolean isValidStock = true;


    @CommandHandler
    public InventoryAggregate(CreateProductCommand command) {
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(command.getProductId())
                .productName(command.getProductName())
                .price(command.getPrice())
                .quantity(command.getQuantity())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.availableStock = event.getQuantity();
    }

    @CommandHandler
    public void handle(CheckInventoryCommand command) {

        if (this.availableStock >= command.getQuantity()) {

            InventoryDeductedEvent event = InventoryDeductedEvent.builder()
                    .productId(command.getProductId())
                    .orderId(command.getOrderId())
                    .quantity(command.getQuantity())
                    .userId(command.getUserId())
                    .cardId(command.getCardId())
                    .build();

            AggregateLifecycle.apply(event);
        } else {
            StockNotAvailableEvent event = StockNotAvailableEvent.builder()
                    .orderId(command.getOrderId())
                    .productId(command.getProductId())
                    .userId(command.getUserId())
                    .cardId(command.getCardId())
                    .build();
            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void handle(StockNotAvailableEvent event) {
        this.productId = event.getProductId();
    }

    @EventSourcingHandler
    public void handle(StockUpdatedEvent event) {
        this.productId = event.getProductId();
        this.availableStock -= event.getQuantity();
    }

    @CommandHandler
    public void handle(UpdateStockCommand command) {
        StockUpdatedEvent event = StockUpdatedEvent.builder()
                .orderId(command.getOrderId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .userId(command.getUserId())
                .cardId(command.getCardId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelProductReservationCommand command) {
        ProductReservationCancelledEvent event = ProductReservationCancelledEvent
                .builder()
                .orderId(command.getOrderId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(ProductReservationCancelledEvent event) {
        this.productId = event.getProductId();
        this.availableStock += event.getQuantity();
    }
}
