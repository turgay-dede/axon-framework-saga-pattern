package com.turgaydede.command.api.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

import javax.annotation.Nonnull;

@Slf4j
public class InventoryServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@Nonnull Exception e, @Nonnull EventMessage<?> eventMessage, @Nonnull EventMessageHandler eventMessageHandler) throws Exception {

        log.error("Error handling event [{}] for aggregate [{}]: {}",
                eventMessage.getPayloadType().getSimpleName(),
                eventMessage.getIdentifier(),
                e.getMessage(),
                e);

        throw e;
    }
}
