package com.turgaydede.command.api.model;

import com.turgaydede.model.CardDetails;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class PaymentCreateRequest {

    private String orderId;
    private CardDetails cardDetails;
}
