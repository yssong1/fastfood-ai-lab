package fastfood.delivery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String riderId;
    private String userId;
    private Long storeId;
    private Long orderId;
    private String address;
    private String status;
    private Date deliveryDt;
}