package com.turgaydede;

import com.turgaydede.logger.axon.LoggingCommandInterceptor;
import com.turgaydede.logger.axon.LoggingEventInterceptor;
import jakarta.annotation.PostConstruct;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }


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
