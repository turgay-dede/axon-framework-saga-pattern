package com.turgaydede.axon.config;

import com.turgaydede.axon.interceptors.LoggingCommandInterceptor;
import com.turgaydede.axon.interceptors.LoggingEventInterceptor;
import jakarta.annotation.PostConstruct;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AxonConfig {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void registerCommandInterceptors() {
        commandBus.registerHandlerInterceptor(new LoggingCommandInterceptor());
    }

    @PostConstruct
    public void registerEventInterceptors() {
        eventBus.registerDispatchInterceptor(new LoggingEventInterceptor());
    }
}
