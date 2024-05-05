package bridge.backend.domain.entity.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BusinessRequestDTO {
    String title;
    List<String> types;
    LocalDate deadline;
    String agent;
    String link;

}
