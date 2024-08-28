package com.turgaydede.command.api.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private String productId;

    private BigDecimal price;

    private int quantity;
}
