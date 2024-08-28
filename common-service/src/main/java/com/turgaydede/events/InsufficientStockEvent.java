package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsufficientStockEvent {

    private String orderId;
    private String productId;
}
