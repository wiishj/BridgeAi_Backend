package bridge.backend.entity.dto;

import bridge.backend.entity.Business;
import bridge.backend.entity.Type;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponseDTO {
    Long id;
    String title;
    List<String> types;
    LocalDate deadline;
    Integer dDay;
    String agent;
    String link;
    Boolean star;

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
                .star(entity.getStar())
                .build();
    }
}
