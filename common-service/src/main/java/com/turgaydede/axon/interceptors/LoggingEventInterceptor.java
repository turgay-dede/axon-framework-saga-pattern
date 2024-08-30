package com.turgaydede.axon.interceptors;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class LoggingEventInterceptor implements MessageDispatchInterceptor<EventMessage<?>> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingEventInterceptor.class);

    @Nonnull
    @Override
    public EventMessage<?> handle(@Nonnull EventMessage<?> message) {
        return MessageDispatchInterceptor.super.handle(message);
    }

    @Nonnull
    @Override
    public BiFunction<Integer, EventMessage<?>, EventMessage<?>> handle(@Nonnull List<? extends EventMessage<?>> list) {
        for (EventMessage<?> eventMessage : list) {
            logger.info("Handling event: {} with payload: {}", eventMessage.getPayloadType().getSimpleName(), eventMessage.getPayload());

        }

        return (index, event) -> event;
    }
}
