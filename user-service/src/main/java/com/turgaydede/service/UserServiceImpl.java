package com.turgaydede.service;

import com.turgaydede.data.CreditCard;
import com.turgaydede.data.UserEntity;
import com.turgaydede.data.UserRepository;
import com.turgaydede.model.CreateUserRequest;
import com.turgaydede.model.CreditCardDto;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserRequest dto) {
        UserEntity userEntity = convertToEntity(dto);
        return userRepository.save(userEntity);

    }

    private UserEntity convertToEntity(CreateUserRequest userDTO) {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();

        // Set user reference in CreditCard entities
        userEntity.setCardDetails(userDTO.getCardDetails().stream()
                .map(cardDTO -> convertToEntity(cardDTO, userEntity))
                .collect(Collectors.toList()));

        return userEntity;
    }

    private CreditCard convertToEntity(CreditCardDto dto, UserEntity userEntity) {
        return CreditCard.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.getName())
                .cardNumber(dto.getCardNumber())
                .validUntilMonth(dto.getValidUntilMonth())
                .validUntilYear(dto.getValidUntilYear())
                .cvv(dto.getCvv())
                .user(userEntity)
                .build();
    }
}
