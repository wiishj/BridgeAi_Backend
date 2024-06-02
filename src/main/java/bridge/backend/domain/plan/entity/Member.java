package bridge.backend.domain.plan.entity;

import bridge.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;
    private String name;
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    @OneToMany(mappedBy="host")
    private List<Item> items = new ArrayList<>();

    //==연관관계 메서드==//
    public void addPlans(Item item){
        items.add(item);
        item.setHost(this);
    }
}
