package com.turgaydede.command.api.interceptors;

import com.turgaydede.command.ValidatePaymentCommand;
import com.turgaydede.model.CardDetails;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;

@Slf4j
@Component
public class CommandInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {

    @Override
    public Object handle(@Nonnull UnitOfWork<? extends CommandMessage<?>> unitOfWork, @Nonnull InterceptorChain interceptorChain) throws Exception {
        CommandMessage<?> commandMessage = unitOfWork.getMessage();

        log.info("Handling command: {}", commandMessage.getPayloadType().getSimpleName());

        try {
            checkCreditCard(commandMessage);
        } catch (Exception ex) {
            log.error("Error in command processing: {}", ex.getMessage());
            throw ex;
        }

        return interceptorChain.proceed();
    }

    private void checkCreditCard(CommandMessage<?> commandMessage) {
        if (commandMessage.getPayload() instanceof ValidatePaymentCommand validatePaymentCommand) {

            CardDetails cardDetails = validatePaymentCommand.getCardDetails();
            LocalDate currentDate = LocalDate.now();

            boolean isCardExpired = currentDate.getYear() > cardDetails.getValidUntilYear() ||
                    (currentDate.getYear() == cardDetails.getValidUntilYear() && currentDate.getMonthValue() > cardDetails.getValidUntilMonth());

            if (isCardExpired) {
                throw new IllegalArgumentException(
                        String.format("Credit card has expired. Expiration date: %d/%d",
                                cardDetails.getValidUntilMonth(), cardDetails.getValidUntilYear()));
            }
        }
    }


}
