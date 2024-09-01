package com.turgaydede.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    private String id;

    private String name;

    private String cardNumber;

    private int validUntilMonth;

    private int validUntilYear;

    private int cvv;

    private String userId;

}
