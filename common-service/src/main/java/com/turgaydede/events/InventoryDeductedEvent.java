package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDeductedEvent {

    private String orderId;
    private String productId;
    private int quantity;
}
