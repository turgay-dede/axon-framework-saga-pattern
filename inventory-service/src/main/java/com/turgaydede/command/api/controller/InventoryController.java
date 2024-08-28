package com.turgaydede.command.api.controller;

import com.turgaydede.command.api.commands.ProductCreateCommand;
import com.turgaydede.command.api.model.ProductCreateRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final CommandGateway commandGateway;

    public InventoryController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestBody ProductCreateRequest request) {

        String productId = UUID.randomUUID().toString();

        ProductCreateCommand command = ProductCreateCommand.builder()
                .productId(productId)
                .productName(request.getProductName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        commandGateway.sendAndWait(command);

        return productId;
    }
}
