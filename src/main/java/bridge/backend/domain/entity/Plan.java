package bridge.backend.domain.entity;

import bridge.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "businessPlan")
public class Plan extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="planId")
    private Long id;

    @ManyToOne
    @JoinColumn(name="hostId")
    private Member host;

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

}
