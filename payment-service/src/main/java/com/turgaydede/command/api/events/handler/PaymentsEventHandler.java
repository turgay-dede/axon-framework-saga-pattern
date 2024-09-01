package com.turgaydede.command.api.events.handler;

import com.turgaydede.command.api.data.PaymentEntity;
import com.turgaydede.command.api.data.PaymentRepository;
import com.turgaydede.enums.OrderStatus;
import com.turgaydede.events.PaymentCancelledEvent;
import com.turgaydede.events.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentsEventHandler {

    private PaymentRepository paymentRepository;

    public PaymentsEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        PaymentEntity payment
                = PaymentEntity.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .status(OrderStatus.PAYMENT_CONFIRMED)
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        PaymentEntity payment
                = paymentRepository.findById(event.getPaymentId()).get();

        payment.setStatus(event.getPaymentStatus());

        paymentRepository.save(payment);
    }
}
