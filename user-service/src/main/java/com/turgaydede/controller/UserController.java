package com.turgaydede.controller;

import com.turgaydede.data.CreditCard;
import com.turgaydede.data.UserEntity;
import com.turgaydede.data.UserRepository;
import com.turgaydede.model.CreateUserRequest;
import com.turgaydede.model.CreditCardDto;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping
    public UserEntity createUser(@RequestBody CreateUserRequest request) {

        Set<CreditCard> creditCards = new HashSet<>();

        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cardDetails(creditCards)
                .build();

        for (CreditCardDto dto : request.getCardDetails()) {
            CreditCard creditCard = CreditCard.builder()
                    .id(UUID.randomUUID().toString())
                    .name(dto.getName())
                    .cardNumber(dto.getCardNumber())
                    .validUntilMonth(dto.getValidUntilMonth())
                    .validUntilYear(dto.getValidUntilYear())
                    .cvv(dto.getCvv())
                    .user(entity)
                    .build();

            creditCards.add(creditCard);
        }


        return userRepository.save(entity);
    }
}
