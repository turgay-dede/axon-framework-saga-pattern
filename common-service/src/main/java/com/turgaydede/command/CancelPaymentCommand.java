package com.turgaydede.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus = "CANCELLED";
}
