package com.turgaydede.model;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<CreditCardDto> cardDetails;
}
