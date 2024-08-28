package com.turgaydede.saga;

import com.turgaydede.command.InventoryCheckCommand;
import com.turgaydede.command.StockUpdateCommand;
import com.turgaydede.command.ValidatePaymentCommand;
import com.turgaydede.command.api.commands.OrderCancelCommand;
import com.turgaydede.command.api.events.OrderCreatedEvent;
import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.events.InsufficientStockEvent;
import com.turgaydede.events.InventoryDeductedEvent;
import com.turgaydede.events.OrderCancelledEvent;
import com.turgaydede.events.StockUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Saga
@Slf4j
@NoArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    private String orderId;
    private Map<String, Integer> products = new HashMap<>();
    private int processedProducts = 0;
    private boolean hasInventoryFailure = false;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());
        this.orderId = event.getOrderId();

        for (OrderItemDto orderItem : event.getOrderItems()) {
            products.put(orderItem.getProductId(), orderItem.getQuantity());

            InventoryCheckCommand command = InventoryCheckCommand.builder()
                    .productId(orderItem.getProductId())
                    .quantity(orderItem.getQuantity())
                    .orderId(event.getOrderId())
                    .build();


            commandGateway.sendAndWait(command);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(StockUpdatedEvent event) {
        log.info("StockUpdatedEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;

        String paymentId = UUID.randomUUID().toString();
        ValidatePaymentCommand command = ValidatePaymentCommand.builder()
                .orderId(event.getOrderId())
                .paymentId(paymentId)
                .build();

        //commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InventoryDeductedEvent event) {
        log.info("InventoryDeductedEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;

        products.put(event.getProductId(), event.getQuantity());

        checkIfSagaShouldComplete();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InsufficientStockEvent event) {
        log.info("InsufficientStockEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;
        hasInventoryFailure = true;

        products.put(event.getProductId(), 0);

        checkIfSagaShouldComplete();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent in Saga for Order Id : {}", event.getOrderId());
        SagaLifecycle.end();
    }

    private void checkIfSagaShouldComplete() {
        if (processedProducts == products.size()) {
            if (hasInventoryFailure) {
                OrderCancelCommand command = OrderCancelCommand.builder()
                        .orderId(orderId)
                        .build();

                commandGateway.send(command);
            } else {
                products.forEach((productId, quantity) -> {
                    StockUpdateCommand command = StockUpdateCommand.builder()
                            .orderId(orderId)
                            .productId(productId)
                            .quantity(quantity)
                            .build();
                    commandGateway.send(command);
                });
            }
            SagaLifecycle.end();
        }
    }
}
