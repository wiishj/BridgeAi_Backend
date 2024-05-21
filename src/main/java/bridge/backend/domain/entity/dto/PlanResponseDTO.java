package bridge.backend.domain.entity.dto;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Member;
import bridge.backend.domain.entity.Plan;
import bridge.backend.domain.entity.Type;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponseDTO {
    private Long id;
    private Long hostId;
    private String input1;
    private String input2;
    private String input3;
    private String input4;
    private String input5;
    private Boolean term3;

    public static PlanResponseDTO from(Plan entity){

        return PlanResponseDTO.builder()
                .id(entity.getId())
                .hostId(entity.getHost().getId())
                .input1(entity.getInput1())
                .input2(entity.getInput2())
                .input3(entity.getInput3())
                .input4(entity.getInput4())
                .input5(entity.getInput5())
                .term3(entity.getTerm3())
                .build();
    }
}
