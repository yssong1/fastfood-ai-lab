package fastfood.order.domain;

import fastfood.order.events.OrderPlaced;
import fastfood.order.events.OrderCancelled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StreamBridge streamBridge;

    @Test
    void testOrderPlacedConsumer() {
        // Given
        OrderPlaced orderPlaced = new OrderPlaced();
        // Populate orderPlaced with necessary data

        // When
        orderService.orderPlacedConsumer().accept(orderPlaced);

        // Then
        // Verify interactions or state changes here
    }

    @Test
    void testOrderCancelledConsumer() {
        // Given
        OrderCancelled orderCancelled = new OrderCancelled();
        // Populate orderCancelled with necessary data

        // When
        orderService.orderCancelledConsumer().accept(orderCancelled);

        // Then
        // Verify interactions or state changes here
    }
}