package com.turgaydede.command.api.data;

import com.turgaydede.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    private String orderId;

    private String customerId;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public OrderEntity(String orderId) {
        this.orderId = orderId;
    }
}
