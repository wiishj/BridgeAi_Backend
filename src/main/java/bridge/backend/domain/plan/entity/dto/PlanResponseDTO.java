package bridge.backend.domain.plan.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResponseDTO {
    MemberResponseDTO member;
    ItemResponseDTO item;

    public PlanResponseDTO(MemberResponseDTO member, ItemResponseDTO item){
        this.member = member;
        this.item = item;
    }
}
