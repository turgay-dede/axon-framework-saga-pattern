package com.turgaydede.events;

import com.turgaydede.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {
    private String shipmentId;
    private String orderId;
    private OrderStatus status;
}
