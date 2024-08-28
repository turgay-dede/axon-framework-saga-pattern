package com.turgaydede.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRestModel {

    private String productId;
    private String productName;
    private Double price;
    private int quantity;
}
