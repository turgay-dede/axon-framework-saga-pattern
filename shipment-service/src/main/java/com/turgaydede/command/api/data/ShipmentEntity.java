package com.turgaydede.command.api.data;

import com.turgaydede.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentEntity {

    @Id
    private String shipmentId;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
