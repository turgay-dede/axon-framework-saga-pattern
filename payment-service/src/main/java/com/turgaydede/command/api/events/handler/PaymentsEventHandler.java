package com.turgaydede.command.api.events.handler;

import com.turgaydede.command.api.data.PaymentEntity;
import com.turgaydede.command.api.data.PaymentRepository;
import com.turgaydede.enums.PaymentStatus;
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
                .paymentStatus(PaymentStatus.COMPLETED)
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        PaymentEntity payment
                = paymentRepository.findById(event.getPaymentId()).get();

        payment.setPaymentStatus(event.getPaymentStatus());

        paymentRepository.save(payment);
    }
}
