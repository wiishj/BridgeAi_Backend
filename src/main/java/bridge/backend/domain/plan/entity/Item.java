package bridge.backend.domain.plan.entity;

import bridge.backend.domain.payment.entity.Order;
import bridge.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item")
public class Item extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="itemId")
    private Long id;

    @ManyToOne
    @JoinColumn(name="hostId")
    private User host;

    @OneToOne
    @JoinColumn(name="orderId")
    private Order order;
    private Boolean isPaid;

    private String title;
    @Column(length=50000)
    private String input1;
    @Column(length=50000)
    private String input2;
    @Column(length=50000)
    private String input3;
    @Column(length=50000)
    private String input4;
    @Column(length=50000)
    private String input5;

    private Boolean term1;
    private Boolean term2;
    private Boolean term3;

    private Boolean isSent;
    //==연관관계 메서드==//
    public void addOrder(Order savedOrder){
        order=savedOrder;
        savedOrder.setItem(this);
    }
}
