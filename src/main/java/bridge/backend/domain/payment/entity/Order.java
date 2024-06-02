package bridge.backend.domain.payment.entity;

import bridge.backend.domain.plan.entity.Item;
import bridge.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="`order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @OneToOne(mappedBy = "order")
    private Item item;

    private String merchantUid;
    private BigDecimal amount;
    private String payMethod;
}
