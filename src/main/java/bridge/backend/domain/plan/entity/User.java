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
@Table(name = "user")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;
    private String name;
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    @OneToMany(mappedBy="host")
    private final List<Item> items = new ArrayList<>();

    //==연관관계 메서드==//
    public void addPlans(Item item){
        items.add(item);
        item.setHost(this);
    }
}
