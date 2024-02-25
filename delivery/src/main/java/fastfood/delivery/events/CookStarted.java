package fastfood.delivery.events;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookStarted {
    private Long id;
    private Long orderId;
    private String userId;
    private Long menuId;
    private String menuName;
    private Integer qty;
    private String address;
    private String status;
    private Date cookingDt;
}