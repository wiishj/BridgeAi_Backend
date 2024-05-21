package bridge.backend.domain.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetHostRequestDTO {
    private Long planId;
    private Long memberId;
}
