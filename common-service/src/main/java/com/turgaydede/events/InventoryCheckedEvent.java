package com.turgaydede.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryCheckedEvent {

    private String productId;
    private int quantity;
    private String orderId;
}
