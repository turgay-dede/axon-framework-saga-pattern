package com.turgaydede.command.api.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Setter
@Getter
@Builder
public class OrderCancelCommand {

    @TargetAggregateIdentifier
    private String orderId;
}
