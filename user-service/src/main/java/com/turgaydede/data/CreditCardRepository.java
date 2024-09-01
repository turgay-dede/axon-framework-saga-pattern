package com.turgaydede.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    List<CreditCard> findAllByUserId(String userId);
}
