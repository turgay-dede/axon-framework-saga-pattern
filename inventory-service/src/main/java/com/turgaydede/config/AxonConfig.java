package com.turgaydede.config;

import com.turgaydede.command.api.data.ProductLookupRepository;
import com.turgaydede.command.api.interceptors.LoggingCommandInterceptor;
import com.turgaydede.command.api.interceptors.LoggingEventInterceptor;
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
    @Autowired
    private ProductLookupRepository productLookupRepository;

    @PostConstruct
    public void registerCommandInterceptors() {
        commandBus.registerHandlerInterceptor(new LoggingCommandInterceptor(productLookupRepository));
    }

    @PostConstruct
    public void registerEventInterceptors() {
        eventBus.registerDispatchInterceptor(new LoggingEventInterceptor());
    }
}
