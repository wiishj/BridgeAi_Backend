package bridge.backend.domain.plan.entity.dto;

import bridge.backend.domain.plan.entity.Item;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {
    private Long itemId;
    private String title;
    private String input1;
    private String input2;
    private String input3;
    private String input4;
    private String input5;
    private Boolean term3;
    private Boolean isPaid;
    private Boolean isSent;

    public static ItemResponseDTO from(Item entity){
        return ItemResponseDTO.builder()
                .itemId(entity.getId())
                .title(entity.getTitle())
                .input1(entity.getInput1())
                .input2(entity.getInput2())
                .input3(entity.getInput3())
                .input4(entity.getInput4())
                .input5(entity.getInput5())
                .term3(entity.getTerm3())
                .isPaid(entity.getIsPaid())
                .isSent(entity.getIsSent())
                .build();
    }
}
