package com.turgaydede.command.api.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private int quantity;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}
