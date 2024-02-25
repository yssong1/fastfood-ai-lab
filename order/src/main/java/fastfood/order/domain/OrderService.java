package fastfood.order.domain;

import fastfood.order.events.OrderPlaced;
import fastfood.order.events.OrderCancelled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public OrderService(OrderRepository orderRepository, StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
    }

    @Bean
    public Consumer<OrderPlaced> orderPlacedConsumer() {
        return orderPlaced -> {
            // Logic to handle OrderPlaced event
        };
    }

    @Bean
    public Consumer<OrderCancelled> orderCancelledConsumer() {
        return orderCancelled -> {
            // Logic to handle OrderCancelled event
        };
    }

    @Bean
    public Consumer<Object> discardFunction() {
        return event -> {
            // Logic to discard the event
        };
    }
}