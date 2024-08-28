package com.turgaydede.command.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class OrderCreateRequest {

    private String customerId;

    private List<OrderItemDto> orderItems;

    private BigDecimal totalAmount;
}
