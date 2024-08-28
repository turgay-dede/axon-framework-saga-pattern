package com.turgaydede.command.api.controller;

import com.turgaydede.command.api.commands.OrderCreateCommand;
import com.turgaydede.command.api.model.OrderCreateRequest;
import com.turgaydede.enums.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private CommandGateway commandGateway;

    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderCreateRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderCreateCommand command = OrderCreateCommand.builder()
                .orderId(orderId)
                .customerId(request.getCustomerId())
                .orderItems(request.getOrderItems())
                .build();

        commandGateway.sendAndWait(command);
    }
}
