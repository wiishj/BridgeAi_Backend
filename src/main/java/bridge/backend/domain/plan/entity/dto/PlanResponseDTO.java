package bridge.backend.domain.plan.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResponseDTO {
    UserResponseDTO user;
    ItemResponseDTO item;

    public PlanResponseDTO(UserResponseDTO user, ItemResponseDTO item){
        this.user = user;
        this.item = item;
    }
}
