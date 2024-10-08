package com.turgaydede.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CancelProductReservationCommand {

    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
}