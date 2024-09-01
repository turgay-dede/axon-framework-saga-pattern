package com.turgaydede.command.api.data;

import com.turgaydede.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    private String paymentId;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Date timeStamp;
}

