package com.turgaydede.command.api.controller;

import com.turgaydede.command.ValidatePaymentCommand;
import com.turgaydede.command.api.model.PaymentCreateRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private CommandGateway commandGateway;

    public PaymentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createOrder(@RequestBody PaymentCreateRequest request) {
        String paymentId = UUID.randomUUID().toString();

        ValidatePaymentCommand command = ValidatePaymentCommand.builder()
                .paymentId(paymentId)
                .orderId(request.getOrderId())
                .cardDetails(request.getCardDetails())
                .build();

        commandGateway.sendAndWait(command);
    }
}
