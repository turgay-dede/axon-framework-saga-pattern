package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentProcessedEvent {
    private String paymentId;
    private String orderId;
    private String productId;
    private int quantity;
}
