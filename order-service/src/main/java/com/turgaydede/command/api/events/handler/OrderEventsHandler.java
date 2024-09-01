package com.turgaydede.command.api.events.handler;

import com.turgaydede.command.api.data.OrderEntity;
import com.turgaydede.command.api.data.OrderItem;
import com.turgaydede.command.api.data.OrderRepository;
import com.turgaydede.command.api.events.OrderCancelledEvent;
import com.turgaydede.command.api.events.OrderCompletedEvent;
import com.turgaydede.command.api.events.OrderCreatedEvent;
import com.turgaydede.command.api.model.OrderItemDto;
import com.turgaydede.enums.OrderStatus;
import com.turgaydede.events.StockUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderEventsHandler {
    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity entity = new OrderEntity();
        List<OrderItem> orderItems = new ArrayList<>();
        BeanUtils.copyProperties(event,entity);

        int totalAmount = 0;

        for (OrderItemDto dto: event.getOrderItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .orderEntity(entity)
                    .productId(dto.getProductId())
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .build();
            totalAmount += Math.multiplyExact( dto.getPrice().intValue(),dto.getQuantity());

            orderItems.add(orderItem);
        }
        entity.setTotalAmount(new BigDecimal(totalAmount));
        entity.setOrderItems(orderItems);
        orderRepository.save(entity);
    }

    @EventHandler
    public void on(StockUpdatedEvent event) {
        OrderEntity entity = orderRepository.findById(event.getOrderId()).get();
        entity.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(entity);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        OrderEntity entity = orderRepository.findById(event.getOrderId()).get();
        entity.setStatus(event.getStatus());
        orderRepository.save(entity);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        OrderEntity entity = orderRepository.findById(event.getOrderId()).get();
        entity.setStatus(event.getStatus());
        orderRepository.save(entity);
    }
}
