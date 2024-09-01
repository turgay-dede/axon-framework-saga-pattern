package com.turgaydede.service;

import com.turgaydede.data.CreditCard;
import com.turgaydede.data.CreditCardRepository;
import com.turgaydede.data.UserEntity;
import com.turgaydede.data.UserRepository;
import com.turgaydede.model.CreateUserRequest;
import com.turgaydede.model.CreditCardDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    public UserServiceImpl(UserRepository userRepository, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
    }

    public UserEntity createUser(CreateUserRequest request) {
        String userId = UUID.randomUUID().toString();
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userRepository.save(userEntity);

        for (CreditCardDto dto : request.getCardDetails()) {

            CreditCard creditCard = CreditCard.builder()
                    .id(UUID.randomUUID().toString())
                    .name(dto.getName())
                    .cardNumber(dto.getCardNumber())
                    .validUntilMonth(dto.getValidUntilMonth())
                    .validUntilYear(dto.getValidUntilYear())
                    .cvv(dto.getCvv())
                    .userId(userId)
                    .build();

            creditCardRepository.save(creditCard);

        }

        return userEntity;
    }
}
