package bridge.backend.domain.user.entity;

import bridge.backend.domain.user.infra.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private SocialType socialType;
}
