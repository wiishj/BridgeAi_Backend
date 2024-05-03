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
public class BusinessRequestDTO {
    String title;
    List<String> types;
    LocalDate deadline;
    String agent;
    String link;

}
