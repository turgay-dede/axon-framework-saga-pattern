package com.turgaydede.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;

    private String orderId;

}


