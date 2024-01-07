package com.ap.orderservice.services;

import com.ap.orderservice.event.OrderPlacedEvent;
import com.ap.orderservice.model.Order;
import com.ap.orderservice.model.OrderItem;
import com.ap.orderservice.repository.OrderRepository;
import com.ap.orderservice.to.OrderItemTO;
import com.ap.orderservice.to.OrderRequestTO;
import com.ap.orderservice.to.ProductStockTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequestTO orderRequestTO) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItems = orderRequestTO.getOrderItemToList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderItems(orderItems);

        order.setUserEmail(orderRequestTO.getUserEmail());

        List<String> productCodes = order.getOrderItems().stream().map(OrderItem::getProductCode).toList();

        ProductStockTO[] productStock = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("productCodes", productCodes).build())
                .retrieve()
                .bodyToMono(ProductStockTO[].class)
                .block();

        boolean areAllItemsOfOrderPresentInStock = Arrays.stream(productStock).allMatch(ProductStockTO::isInStock);

        if (areAllItemsOfOrderPresentInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("order-placed-topic", new OrderPlacedEvent(order.getOrderNumber(), order.getUserEmail()));
            return "Order placed successfully";
        }
        else throw new RuntimeException("All items are not in stock.");
    }

    private OrderItem mapToDto(OrderItemTO orderItemTO) {
        OrderItem orderLineItems = new OrderItem();
        orderLineItems.setTotalPrice(orderItemTO.getPrice());
        orderLineItems.setQuantity(orderItemTO.getQuantity());
        orderLineItems.setProductCode(orderItemTO.getProductCode());
        return orderLineItems;
    }

}
