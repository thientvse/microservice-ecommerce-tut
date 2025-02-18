package com.thientvse.microservice.order.service;

import com.thientvse.microservice.order.client.InventoryClient;
import com.thientvse.microservice.order.dto.OrderRequest;
import com.thientvse.microservice.order.event.OrderPlaceEvent;
import com.thientvse.microservice.order.model.Order;
import com.thientvse.microservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private KafkaTemplate<String, OrderPlaceEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            // map OrderRequest to Order object
            Order order = new Order();

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            // save order
            orderRepository.save(order);

            // send to kafka
            OrderPlaceEvent orderPlaceEvent = new OrderPlaceEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            log.info("Start - Sending OrderPlaces {} to Kafka topic order-placed", orderPlaceEvent);
            kafkaTemplate.send("order-placed", orderPlaceEvent);
            log.info("End - Sending OrderPlaces {} to Kafka topic order-placed", orderPlaceEvent);

        } else {
            throw new RuntimeException("Product with SKUCODE: "+orderRequest.skuCode()+" is not in stock");
        }

    }
}
