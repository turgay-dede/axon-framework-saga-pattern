package com.turgaydede.events;

import com.turgaydede.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCancelledEvent {

    private String orderId;
    private OrderStatus status;
}
