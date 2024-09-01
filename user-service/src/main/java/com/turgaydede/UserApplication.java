package com.turgaydede;

import com.turgaydede.data.CreditCard;
import com.turgaydede.data.CreditCardRepository;
import com.turgaydede.data.UserEntity;
import com.turgaydede.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class UserApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public void run(String... args) throws Exception {

//        UserEntity user = UserEntity.builder()
//                .id("a77d2ab3-fb27-4341-8a97-7cdb85f7f4c1")
//                .username("john_doe")
//                .firstName("John")
//                .lastName("Doe")
//                .email("john.doe@example.com")
//                .password("SecurePassword123")
//                .build();
//
//        CreditCard card1 = CreditCard.builder()
//                .id("a4e22f52-a7d6-4226-b237-527570ddb1ec")
//                .name("John Doe")
//                .cardNumber("1234567812345678")
//                .validUntilMonth(12)
//                .validUntilYear(2025)
//                .cvv(123)
//                .userId("a77d2ab3-fb27-4341-8a97-7cdb85f7f4c1")
//                .build();
//
//        CreditCard card2 = CreditCard.builder()
//                .id("a4e22f52-a7d6-4226-b237-527570ddb2ec")
//                .name("John Doe")
//                .cardNumber("8765432187654321")
//                .validUntilMonth(7)
//                .validUntilYear(2024)
//                .cvv(456)
//                .userId("a77d2ab3-fb27-4341-8a97-7cdb85f7f4c1")
//                .build();
//
//
//        userRepository.save(user);
//        creditCardRepository.saveAll(List.of(card1,card2));


    }
}
