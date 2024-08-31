package com.turgaydede;

import com.turgaydede.command.CreateProductCommand;
import com.turgaydede.command.api.errorhandling.InventoryServiceEventsErrorHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class InventoryApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("product-group",
                configuration -> new InventoryServiceEventsErrorHandler());

// Alternatif olarak, hatanın olduğu gibi yayılmasını sağlayan varsayılan hata işleyici kullanılabilir:
//        configurer.registerListenerInvocationErrorHandler("product-group",
//                configuration -> PropagatingErrorHandler.instance());
    }

    @Override
    public void run(String... args) throws Exception {
        List<CreateProductCommand> products = List.of(
                new CreateProductCommand("8088c6c8-182e-4417-87cc-ea00ef35b1ec", "İphone 11", 25000.0, 14),
                new CreateProductCommand("30f12d34-e4d7-4cc2-aa0d-ca3fca7c07ab", "Samsung Galaxy S21", 22000.0, 20),
                new CreateProductCommand("60b3efe8-48f7-48f3-a14e-bf2befbac79d", "Huawei P40", 18000.0, 10),
                new CreateProductCommand("cf675c20-1820-4e4f-b90a-bfb4b51f8c81", "OnePlus 9", 21000.0, 15),
                new CreateProductCommand("b49d7e56-1d86-43d0-84e7-58fc49b55ca4", "Xiaomi Mi 11", 20000.0, 12)
        );


        for (CreateProductCommand product : products) {
            commandGateway.send(product);
        }
    }
}
