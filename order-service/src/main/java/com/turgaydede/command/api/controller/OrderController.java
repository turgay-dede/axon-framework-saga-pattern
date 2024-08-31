package com.turgaydede.command.api.controller;

import com.turgaydede.command.api.commands.CreateOrderCommand;
import com.turgaydede.command.api.model.OrderCreateRequest;
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

        CreateOrderCommand command = CreateOrderCommand.builder()
                .orderId(orderId)
                .userId(request.getUserId())
                .cardId(request.getCardId())
                .orderItems(request.getOrderItems())
                .build();

        commandGateway.sendAndWait(command);
    }
}
