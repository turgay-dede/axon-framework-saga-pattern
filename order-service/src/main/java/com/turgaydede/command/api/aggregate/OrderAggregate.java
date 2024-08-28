package com.turgaydede.command.api.aggregate;


import com.turgaydede.command.api.commands.OrderCancelCommand;
import com.turgaydede.command.api.commands.OrderCreateCommand;
import com.turgaydede.command.api.events.OrderCreatedEvent;
import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.enums.OrderStatus;
import com.turgaydede.events.OrderCancelledEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;

    @CommandHandler
    public OrderAggregate(OrderCreateCommand command) {

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(command.getOrderId())
                .customerId(command.getCustomerId())
                .orderItems(command.getOrderItems())
                .status(OrderStatus.CREATED)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.orderItems = event.getOrderItems();
        this.orderStatus = event.getStatus();
    }

    @CommandHandler
    public void on(OrderCancelCommand command) {
        OrderCancelledEvent event = OrderCancelledEvent.builder()
                .orderId(command.getOrderId())
                .status(OrderStatus.CANCELLED)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(OrderCancelledEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getStatus();
    }
}
