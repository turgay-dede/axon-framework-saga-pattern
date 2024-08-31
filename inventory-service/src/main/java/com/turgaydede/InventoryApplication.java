package com.turgaydede;

import com.turgaydede.command.api.errorhandling.InventoryServiceEventsErrorHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("product-group",
                configuration -> new InventoryServiceEventsErrorHandler());

// Alternatif olarak, hatanın olduğu gibi yayılmasını sağlayan varsayılan hata işleyici kullanılabilir:
//        configurer.registerListenerInvocationErrorHandler("product-group",
//                configuration -> PropagatingErrorHandler.instance());
    }

}
