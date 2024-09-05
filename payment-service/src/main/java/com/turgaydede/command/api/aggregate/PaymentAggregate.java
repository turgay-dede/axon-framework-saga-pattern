package com.turgaydede.command.api.aggregate;

import com.turgaydede.command.CancelPaymentCommand;
import com.turgaydede.command.ValidatePaymentCommand;
import com.turgaydede.enums.OrderStatus;
import com.turgaydede.events.PaymentCancelledEvent;
import com.turgaydede.events.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private OrderStatus status;

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand command) {
        log.info("Executing ValidatePaymentCommand for " +
                        "Order Id: {} and PaymentEntity Id: {}",
                command.getOrderId(),
                command.getPaymentId());

        PaymentProcessedEvent event = PaymentProcessedEvent.builder()
                .paymentId(command.getPaymentId())
                .orderId(command.getOrderId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .build();

        AggregateLifecycle.apply(event);

        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }

    @CommandHandler
    public void handle(CancelPaymentCommand cancelPaymentCommand) {
        PaymentCancelledEvent paymentCancelledEvent
                = new PaymentCancelledEvent();
        BeanUtils.copyProperties(cancelPaymentCommand,
                paymentCancelledEvent);

        AggregateLifecycle.apply(paymentCancelledEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event) {
        this.status = event.getStatus();
    }

}
