package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StockUpdatedEvent {

    private String orderId;
    private String productId;
    private String userId;
    private String cardId;
    private int quantity;
}
