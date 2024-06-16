package bridge.backend.domain.user.entity.dto;

import bridge.backend.domain.user.infra.SocialType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private SocialType socialType;
    private String role;
    private String username;
    private String nickname;
}
