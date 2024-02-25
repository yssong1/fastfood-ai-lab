package fastfood.delivery.service;

import fastfood.delivery.domain.Delivery;
import fastfood.delivery.events.CookStarted;
import fastfood.delivery.repository.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class DeliveryServiceTest {

    @Autowired
    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private StreamBridge streamBridge;

    @Test
    void testWheneverCookStarted_PropagateDeliveryCall() {
        // Given
        CookStarted cookStarted = new CookStarted();
        cookStarted.setOrderId(1L);
        cookStarted.setId(2L);
        cookStarted.setAddress("123 Main St");
        cookStarted.setUserId("user1");

        // When
        deliveryService.wheneverCookStarted_PropagateDeliveryCall().accept(MessageBuilder.withPayload(cookStarted).build());

        // Then
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }
}