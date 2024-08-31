package com.turgaydede.projection;

import com.turgaydede.data.UserEntity;
import com.turgaydede.data.UserRepository;
import com.turgaydede.model.CardDetails;
import com.turgaydede.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProjection {
    private final UserRepository userRepository;

    public UserProjection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    public CardDetails getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        UserEntity userEntity = userRepository.findByUserIdAndCardId(query.getUserId(), query.getCardId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found with userId: %s and cardId: %s " + query.getUserId(), query.getCardId())));

        CardDetails cardDetails = userEntity.getCardDetails().stream()
                .map(creditCard -> CardDetails.builder()
                        .name(creditCard.getName())
                        .cardNumber(creditCard.getCardNumber())
                        .validUntilMonth(creditCard.getValidUntilMonth())
                        .validUntilYear(creditCard.getValidUntilYear())
                        .cvv(creditCard.getCvv())
                        .build())
                .findFirst().get();

        return cardDetails;

    }
}
