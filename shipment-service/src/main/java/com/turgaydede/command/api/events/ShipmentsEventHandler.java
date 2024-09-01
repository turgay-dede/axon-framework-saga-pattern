package com.turgaydede.command.api.events;

import com.turgaydede.command.api.data.ShipmentEntity;
import com.turgaydede.command.api.data.ShipmentRepository;
import com.turgaydede.events.OrderShippedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShipmentsEventHandler {

    private ShipmentRepository shipmentRepository;

    public ShipmentsEventHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        ShipmentEntity shipment
                = new ShipmentEntity();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }
}
