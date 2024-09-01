package com.turgaydede.projection;

import com.turgaydede.data.CreditCard;
import com.turgaydede.data.CreditCardRepository;
import com.turgaydede.model.CardDetails;
import com.turgaydede.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProjection {
    private final CreditCardRepository creditCardRepository;

    public UserProjection(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @QueryHandler
    public CardDetails getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        List<CreditCard> creditCards = creditCardRepository.findAllByUserId(query.getUserId());

        if (creditCards.isEmpty()) {
            throw  new IllegalArgumentException("CreditCard not found with userId: %s " + query.getUserId());
        }

        CardDetails cardDetails = creditCards.stream()
                .filter(creditCard -> creditCard.getId().equals(query.getCardId()))
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
