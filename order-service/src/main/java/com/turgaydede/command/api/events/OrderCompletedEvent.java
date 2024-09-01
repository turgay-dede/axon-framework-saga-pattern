package com.turgaydede.command.api.events;

import com.turgaydede.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderCompletedEvent {

    private String orderId;
    private OrderStatus status;
}
