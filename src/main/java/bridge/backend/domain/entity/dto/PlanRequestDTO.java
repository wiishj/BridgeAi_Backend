package bridge.backend.domain.entity.dto;

import bridge.backend.domain.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequestDTO {
    private MemberRequestDTO member;
    private ItemRequestDTO item;
}
