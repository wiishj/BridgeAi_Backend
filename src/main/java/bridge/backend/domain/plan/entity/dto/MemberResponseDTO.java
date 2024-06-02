package bridge.backend.domain.plan.entity.dto;

import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long memberId;
    private String name;
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    public static MemberResponseDTO from(Member entity){
        return MemberResponseDTO.builder()
                .memberId(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .birth(entity.getBirth())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}
