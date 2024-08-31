package com.turgaydede.events;

import com.turgaydede.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private PaymentStatus paymentStatus;
}
