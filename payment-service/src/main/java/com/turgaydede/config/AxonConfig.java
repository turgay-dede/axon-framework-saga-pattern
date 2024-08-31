package com.turgaydede.config;

import com.turgaydede.command.api.interceptors.CommandInterceptor;
import com.turgaydede.command.api.interceptors.EventInterceptor;
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
        commandBus.registerHandlerInterceptor(new CommandInterceptor());
    }

    @PostConstruct
    public void registerEventInterceptors() {
        eventBus.registerDispatchInterceptor(new EventInterceptor());
    }
}
