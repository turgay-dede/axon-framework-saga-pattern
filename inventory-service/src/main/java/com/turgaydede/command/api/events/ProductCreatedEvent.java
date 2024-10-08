package com.turgaydede.command.api.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductCreatedEvent {
    private String productId;

    private String productName;

    private Double price;

    private int quantity;
}
