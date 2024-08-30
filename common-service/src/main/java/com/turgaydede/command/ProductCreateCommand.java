package com.turgaydede.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductCreateCommand {

    @TargetAggregateIdentifier
    private String productId;

    private String productName;

    private Double price;

    private int quantity;
}
