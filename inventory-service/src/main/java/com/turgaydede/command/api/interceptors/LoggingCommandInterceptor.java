package com.turgaydede.command.api.interceptors;

import com.turgaydede.command.ProductCreateCommand;
import com.turgaydede.command.api.data.ProductLookupRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Slf4j
@Component
public class LoggingCommandInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {

    @Autowired
    private final ProductLookupRepository productLookupRepository;

    public LoggingCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }


    @Override
    public Object handle(@Nonnull UnitOfWork<? extends CommandMessage<?>> unitOfWork, @Nonnull InterceptorChain interceptorChain) throws Exception {
        CommandMessage<?> commandMessage = unitOfWork.getMessage();

        checkProductExists(commandMessage);

        log.info("Handling command: {}", commandMessage.getPayloadType().getSimpleName());
        return interceptorChain.proceed();
    }

    private void checkProductExists(CommandMessage<?> commandMessage) {
        if (commandMessage.getPayload() instanceof ProductCreateCommand productCreateCommand) {

            boolean productExists = productLookupRepository
                    .findByProductIdOrProductName(productCreateCommand.getProductId(), productCreateCommand.getProductName())
                    .isPresent();

            if (productExists) {
                throw new IllegalArgumentException(String.format("Product with productId: %s or productName: %s already exists!", productCreateCommand.getProductId(), productCreateCommand.getProductName()));
            }
        }
    }
}
