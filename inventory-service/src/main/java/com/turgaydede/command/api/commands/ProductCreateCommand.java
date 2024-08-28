package com.turgaydede.command.api.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateCommand {

    @TargetAggregateIdentifier
    private String productId;

    private String productName;

    private Double price;

    private int quantity;
}
