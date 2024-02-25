package fastfood.store.service;

import fastfood.store.events.*;
import fastfood.store.domain.Store;
import fastfood.store.repository.StoreRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public StoreService(StoreRepository storeRepository, StreamBridge streamBridge) {
        this.storeRepository = storeRepository;
        this.streamBridge = streamBridge;
    }

    @Bean
    public Consumer<Message<OrderPlaced>> wheneverOrderPlaced_AcceptOrder() {
        return event -> {
            // Logic to handle OrderPlaced
            OrderPlaced orderPlaced = event.getPayload();
            Store store = new Store();
            store.setUserId(orderPlaced.getUserId());
            store.setOrderId(orderPlaced.getId());
            store.setMenuId(orderPlaced.getMenuId());
            store.setQty(orderPlaced.getQty());
            store.setAddress(orderPlaced.getAddress());
            store.setStatus(OrderAccepted.class.getSimpleName());

            storeRepository.save(store);

            // Publish Domain Event
            OrderAccepted orderAccepted = new OrderAccepted();
            BeanUtils.copyProperties(store, orderAccepted);
            
            streamBridge.send("producer-out-0", MessageBuilder
                .withPayload(orderAccepted)
                .setHeader("type", OrderAccepted.class.getSimpleName())
                .build()
            );
        };
    }

    @Bean
    public Consumer<Message<OrderCancelled>> wheneverOrderCancelled_CancelCook() {
        return event -> {
            // Logic to handle OrderCancelled
            OrderCancelled orderCancelled = event.getPayload();
            Store store = storeRepository.findByOrderId(orderCancelled.getId());            
            store.setStatus(CookCancelled.class.getSimpleName());

            storeRepository.save(store);

            // Publish Domain Event
            CookCancelled cookCancelled = new CookCancelled();
            BeanUtils.copyProperties(store, cookCancelled);

            streamBridge.send("producer-out-0", MessageBuilder
                .withPayload(cookCancelled)
                .setHeader("type", CookCancelled.class.getSimpleName())
                .build()
            );
        };
    }

    @Bean
    public Consumer<Object> discardFunction() {
        return event -> {
            // Logic to discard the event
        };
    }

}