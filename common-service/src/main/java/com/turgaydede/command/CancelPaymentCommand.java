package com.turgaydede.command;

import com.turgaydede.enums.OrderStatus;
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
    private OrderStatus status;
}
