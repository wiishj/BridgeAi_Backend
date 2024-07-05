package bridge.backend.domain.business.entity.dto;

import bridge.backend.domain.business.entity.Business;
import bridge.backend.domain.plan.entity.Type;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponseDTO implements Serializable {
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
        Long dDay = ChronoUnit.DAYS.between(LocalDate.now(),entity.getDeadline());
        return BusinessResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .types(typeTexts)
                .deadline(entity.getDeadline())
                .dDay(dDay.intValue())
                .agent(entity.getAgent())
                .link(entity.getLink())
                .build();
    }
}
