package com.turgaydede.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdateCommand {

    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
}


