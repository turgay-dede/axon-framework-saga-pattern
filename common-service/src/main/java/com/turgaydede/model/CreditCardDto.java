package com.turgaydede.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDto {
    private String name;

    private String cardNumber;

    private int validUntilMonth;

    private int validUntilYear;

    private int cvv;
}
