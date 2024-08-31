package com.turgaydede.command.api.data;

import com.turgaydede.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private PaymentStatus paymentStatus;
    private Date timeStamp;
}

