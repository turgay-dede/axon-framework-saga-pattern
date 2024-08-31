package com.turgaydede.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u JOIN CreditCard c WHERE u.id = :userId AND c.id = :cardId")
    Optional<UserEntity> findByUserIdAndCardId(@Param("userId") String userId, @Param("cardId") String cardId);

}
