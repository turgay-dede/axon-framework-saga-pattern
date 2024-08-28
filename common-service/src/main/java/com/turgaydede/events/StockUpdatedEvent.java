package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdatedEvent {

    private String orderId;
    private String productId;
    private int quantity;
}
