package fastfood.delivery.service;

import fastfood.delivery.events.*;
import fastfood.delivery.domain.*;
import fastfood.delivery.repository.DeliveryRepository;
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
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, StreamBridge streamBridge) {
        this.deliveryRepository = deliveryRepository;
        this.streamBridge = streamBridge;
    }    

    @Bean
    public Consumer<Message<CookStarted>> wheneverCookStarted_PropagateDeliveryCall() {
        return event -> {
            // Logic to handle OrderPlaced
            CookStarted cookStarted = event.getPayload();
            Delivery delivery = new Delivery();
            delivery.setUserId(cookStarted.getUserId());
            delivery.setOrderId(cookStarted.getOrderId());
            delivery.setStoreId(cookStarted.getId());
            delivery.setAddress(cookStarted.getAddress());
            delivery.setStatus(RiderAssigned.class.getSimpleName());

            deliveryRepository.save(delivery);
            
            // Publish Domain Event
            RiderAssigned riderAssigned = new RiderAssigned();
            BeanUtils.copyProperties(delivery, riderAssigned);            

            streamBridge.send("producer-out-0", MessageBuilder
                .withPayload(riderAssigned)
                .setHeader("type", RiderAssigned.class.getSimpleName())
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