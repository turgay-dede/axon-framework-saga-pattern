package com.turgaydede.command.api.commands;

import com.turgaydede.command.api.model.OrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.List;


@Setter
@Getter
@Builder
public class OrderCreateCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String customerId;

    private List<OrderItemDto> orderItems;

    private BigDecimal totalAmount;

    private String status;
}
