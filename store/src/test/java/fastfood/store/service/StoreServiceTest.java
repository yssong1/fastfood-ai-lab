package fastfood.store.service;

import fastfood.store.domain.Store;
import fastfood.store.events.OrderCancelled;
import fastfood.store.events.OrderPlaced;
import fastfood.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @MockBean
    private StoreRepository storeRepository;

    @MockBean
    private StreamBridge streamBridge;

    @Test
    void testWheneverOrderPlaced_AcceptOrder() {
        // Given
        OrderPlaced orderPlaced = new OrderPlaced();
        orderPlaced.setUserId("user1");
        orderPlaced.setId(1L);
        orderPlaced.setMenuId(10L);
        orderPlaced.setQty(2);
        orderPlaced.setAddress("123 Main St");

        Message<OrderPlaced> message = MessageBuilder.withPayload(orderPlaced).build();

        // When
        storeService.wheneverOrderPlaced_AcceptOrder().accept(message);

        // Then
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    void testWheneverOrderCancelled_CancelCook() {
        // Given
        OrderCancelled orderCancelled = new OrderCancelled();
        orderCancelled.setId(1L);

        Store store = new Store();
        store.setOrderId(1L);
        store.setStatus("OrderAccepted");

        when(storeRepository.findByOrderId(1L)).thenReturn(store);

        Message<OrderCancelled> message = MessageBuilder.withPayload(orderCancelled).build();

        // When
        storeService.wheneverOrderCancelled_CancelCook().accept(message);

        // Then
        verify(storeRepository).save(any(Store.class));
    }
}