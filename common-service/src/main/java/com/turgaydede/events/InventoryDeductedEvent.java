package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryDeductedEvent {

    private String orderId;
    private String productId;
    private int quantity;
    private String userId;
    private String cardId;
}
