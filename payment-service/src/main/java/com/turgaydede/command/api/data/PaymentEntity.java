package com.turgaydede.command.api.data;

import com.turgaydede.enums.PaymentStatus;
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
    private PaymentStatus paymentStatus;
    private Date timeStamp;
}

