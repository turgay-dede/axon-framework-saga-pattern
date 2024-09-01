package com.turgaydede.saga;

import com.turgaydede.command.*;
import com.turgaydede.command.api.commands.CancelOrderCommand;
import com.turgaydede.command.api.commands.CompleteOrderCommand;
import com.turgaydede.command.api.events.OrderCancelledEvent;
import com.turgaydede.command.api.events.OrderCompletedEvent;
import com.turgaydede.command.api.events.OrderCreatedEvent;
import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.enums.OrderStatus;
import com.turgaydede.events.*;
import com.turgaydede.model.CardDetails;
import com.turgaydede.queries.GetUserPaymentDetailsQuery;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Saga
@Slf4j
@NoArgsConstructor
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    private String orderId;
    private Map<String, Integer> products = new HashMap<>();
    private int processedProducts = 0;
    private boolean isPaymentCompleted = false;
    private boolean hasInventoryFailure = false;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id : {}", event.getOrderId());
        this.orderId = event.getOrderId();

        for (OrderItemDto orderItem : event.getOrderItems()) {
            products.put(orderItem.getProductId(), orderItem.getQuantity());

            CheckInventoryCommand command = CheckInventoryCommand.builder()
                    .productId(orderItem.getProductId())
                    .quantity(orderItem.getQuantity())
                    .orderId(event.getOrderId())
                    .userId(event.getUserId())
                    .cardId(event.getCardId())
                    .build();


            commandGateway.send(command, new CommandCallback<CheckInventoryCommand, Object>() {
                @Override
                public void onResult(@Nonnull CommandMessage<? extends CheckInventoryCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                    if (commandResultMessage.isExceptional()) {
                        Throwable exception = commandResultMessage.exceptionResult();
                        log.error("SagaEventHandler: Error occurred while handling InventoryCheckCommand: {}", exception.getMessage());
                        orderCancelCommand();
                    }
                }
            });
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(StockUpdatedEvent event) {
        log.info("StockUpdatedEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;

        CardDetails cardDetails = null;
        try {
            GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = GetUserPaymentDetailsQuery.builder()
                    .userId(event.getUserId())
                    .cardId(event.getCardId())
                    .build();

            cardDetails = queryGateway.query(getUserPaymentDetailsQuery,
                            ResponseTypes.instanceOf(CardDetails.class)).join();
        } catch (Exception exception) {
            log.info("GetUserPaymentDetailsQuery: cardDetails not found exception. " + exception.getMessage());
            orderCancelCommand();
        }


        if (cardDetails == null) {
            orderCancelCommand();
        }

        String paymentId = UUID.randomUUID().toString();

        if (!isPaymentCompleted) {
            isPaymentCompleted = true;
            ValidatePaymentCommand command = ValidatePaymentCommand.builder()
                    .orderId(event.getOrderId())
                    .paymentId(paymentId)
                    .cardDetails(cardDetails)
                    .build();

            commandGateway.send(command, new CommandCallback<ValidatePaymentCommand, Object>() {
                @Override
                public void onResult(@Nonnull CommandMessage<? extends ValidatePaymentCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                    if (commandResultMessage.isExceptional()) {
                        Throwable exception = commandResultMessage.exceptionResult();
                        log.error("SagaEventHandler: Error occurred while handling ValidatePaymentCommand: {}", exception.getMessage());

                        cancelProductReservationCommand(event);
                    }
                }
            });
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InventoryDeductedEvent event) {
        log.info("InventoryDeductedEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;

        products.put(event.getProductId(), event.getQuantity());

        checkIfSagaShouldComplete(event.getUserId(),event.getCardId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(StockNotAvailableEvent event) {
        log.info("InsufficientStockEvent in Saga for Order Id : {}", event.getOrderId());
        processedProducts++;
        hasInventoryFailure = true;

        products.put(event.getProductId(), 0);

        checkIfSagaShouldComplete(event.getUserId(),event.getCardId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent event) {
        log.info("InsufficientStockEvent in Saga for Order Id : {}", event.getOrderId());

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent event) {
        log.info("ProductReservationCancelledEvent in Saga for Order Id : {}", event.getOrderId());
        orderCancelCommand();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent in Saga for Order Id : {}", event.getOrderId());
        createShipOrderCommand(event.getOrderId(), event.getPaymentId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {
        log.info("OrderShippedEvent in Saga for Order Id : {}", event.getOrderId());

        CompleteOrderCommand command = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .status(OrderStatus.COMPLETED)
                .build();

        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in Saga for Order Id : {}", event.getOrderId());
        SagaLifecycle.end();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent in Saga for Order Id : {}", event.getOrderId());
        SagaLifecycle.end();
    }

    private void checkIfSagaShouldComplete(String userId, String cardId) {
        if (processedProducts == products.size()) {
            if (hasInventoryFailure) {
                orderCancelCommand();
            } else {
                products.forEach((productId, quantity) -> {
                    UpdateStockCommand command = UpdateStockCommand.builder()
                            .orderId(orderId)
                            .productId(productId)
                            .quantity(quantity)
                            .userId(userId)
                            .cardId(cardId)
                            .build();
                    commandGateway.send(command);
                });
            }
        }
    }

    private void orderCancelCommand() {
        CancelOrderCommand command = CancelOrderCommand.builder()
                .orderId(orderId)
                .build();

        commandGateway.send(command);
    }

    private void cancelProductReservationCommand(StockUpdatedEvent event) {
        CancelProductReservationCommand command = CancelProductReservationCommand.builder()
                .userId(event.getUserId())
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .build();

        commandGateway.sendAndWait(command);
    }



    private void createShipOrderCommand(String orderId, String paymentId) {
        String shipmentId = UUID.randomUUID().toString();

        ShipOrderCommand command = ShipOrderCommand.builder()
                .shipmentId(shipmentId)
                .orderId(orderId)
                .build();

        commandGateway.send(command, new CommandCallback<ShipOrderCommand, Object>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends ShipOrderCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    Throwable exception = commandResultMessage.exceptionResult();
                    log.error("SagaEventHandler: Error occurred while handling ShipOrderCommand: {}", exception.getMessage());

                    cancelPaymentCommand(paymentId);
                }
            }
        });
    }

    private void cancelPaymentCommand(String paymentId) {
        CancelPaymentCommand command = CancelPaymentCommand.builder()
                .orderId(orderId)
                .paymentId(paymentId)
                .status(OrderStatus.FAILED_PAYMENT)
                .build();

        commandGateway.send(command);
    }

    private void completeOrderCommand() {
        CompleteOrderCommand command = CompleteOrderCommand.builder()
                .orderId(orderId)
                .status(OrderStatus.COMPLETED)
                .build();

        commandGateway.send(command);
    }
}
