package fastfood.delivery.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class DeliveryCompleted {
    private String eventType;
    private Long id;
    private String riderId;
    private String userId;
    private Long storeId;
    private String orderId;
    private String address;
    private String status;
    private Date deliveryDt;

    public DeliveryCompleted() {
        this.eventType = this.getClass().getSimpleName();
    } 
}