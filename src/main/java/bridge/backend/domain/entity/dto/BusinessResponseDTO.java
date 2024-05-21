package bridge.backend.domain.entity.dto;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Type;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponseDTO {
    private Long id;
    private String title;
    private List<String> types;
    private LocalDate deadline;
    private Integer dDay;
    private String agent;
    private String link;

    public static BusinessResponseDTO from(Business entity){
        List<String> typeTexts = new ArrayList<>();
        for(Type type : entity.getTypes()){
            typeTexts.add(type.getText());
        }
        return BusinessResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .types(typeTexts)
                .deadline(entity.getDeadline())
                .dDay(entity.getDDay())
                .agent(entity.getAgent())
                .link(entity.getLink())
                .build();
    }
}
