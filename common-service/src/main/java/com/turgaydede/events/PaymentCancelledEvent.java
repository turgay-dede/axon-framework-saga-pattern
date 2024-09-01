package com.turgaydede.events;

import com.turgaydede.enums.OrderStatus;
import lombok.Data;

@Data
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private OrderStatus paymentStatus;
}
