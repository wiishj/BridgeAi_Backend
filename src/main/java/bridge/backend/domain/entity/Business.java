package bridge.backend.domain.entity;

import bridge.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "business")
public class Business extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="businessId")
    private Long id;
    private String title;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Type> types;
    private LocalDate deadline;
    private Integer dDay;
    private String agent;
    private String link;
}
