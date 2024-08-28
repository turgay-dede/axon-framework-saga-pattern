package com.turgaydede.command.api.aggregate;

import com.turgaydede.command.InventoryCheckCommand;
import com.turgaydede.command.StockUpdateCommand;
import com.turgaydede.command.api.commands.ProductCreateCommand;
import com.turgaydede.command.api.events.ProductCreatedEvent;
import com.turgaydede.events.InsufficientStockEvent;
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
    public InventoryAggregate(ProductCreateCommand command) {
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
    public void handle(InventoryCheckCommand command) {

        if (this.availableStock >= command.getQuantity()) {

            InventoryDeductedEvent event = InventoryDeductedEvent.builder()
                    .productId(command.getProductId())
                    .orderId(command.getOrderId())
                    .quantity(command.getQuantity())
                    .build();

            AggregateLifecycle.apply(event);
        } else {
            InsufficientStockEvent event = InsufficientStockEvent.builder()
                    .orderId(command.getOrderId())
                    .productId(command.getProductId())
                    .build();
            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void handle(InsufficientStockEvent event) {
        this.productId = event.getProductId();
    }

    @EventSourcingHandler
    public void handle(StockUpdatedEvent event) {
        this.productId = event.getProductId();
        this.availableStock -= event.getQuantity();
    }

    @CommandHandler
    public void handle(StockUpdateCommand command) {
        StockUpdatedEvent event = StockUpdatedEvent.builder()
                .orderId(command.getOrderId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .build();

        AggregateLifecycle.apply(event);
    }
}
