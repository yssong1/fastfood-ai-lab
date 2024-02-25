package fastfood.order.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
public class OrderCancelled {
    private String eventType;
    private Long id;
    private String userId;
    private Long menuId;
    private String menuName;
    private Integer qty;
    private String address;
    private Date orderDt;

    public OrderCancelled() {
        this.eventType = this.getClass().getSimpleName();
    }
}