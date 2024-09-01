package com.turgaydede.command.api.commands;

import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.List;


@Setter
@Getter
@Builder
@ToString
public class CompleteOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private OrderStatus status;
}
