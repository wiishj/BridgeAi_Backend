package bridge.backend.domain.plan.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequestDTO {
    private UserRequestDTO user;
    private ItemRequestDTO item;
}
