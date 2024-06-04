package bridge.backend.domain.plan.entity.dto;

import bridge.backend.domain.plan.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    public static UserResponseDTO from(User entity){
        return UserResponseDTO.builder()
                .userId(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .birth(entity.getBirth())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}
