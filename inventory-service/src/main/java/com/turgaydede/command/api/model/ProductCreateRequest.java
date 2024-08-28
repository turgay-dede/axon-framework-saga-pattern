package com.turgaydede.command.api.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {
    private String productName;

    private Double price;

    private int quantity;
}
