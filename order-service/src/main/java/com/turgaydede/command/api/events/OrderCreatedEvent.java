package com.turgaydede.command.api.events;

import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class OrderCreatedEvent {

    private String orderId;

    private String customerId;

    private List<OrderItemDto> orderItems;

    private BigDecimal totalAmount;

    private OrderStatus status;

}
