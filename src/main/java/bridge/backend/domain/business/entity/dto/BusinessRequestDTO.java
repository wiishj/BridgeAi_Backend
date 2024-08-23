package bridge.backend.domain.business.entity.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BusinessRequestDTO {
    private String title;
    private List<String> types;
    private LocalDate deadline;
    private String agent;
    @Size(max=1000)
    private String link;

    public Boolean isNull(){
        if(this.title==null || this.types.isEmpty() || this.deadline==null || this.agent==null || this.agent.isEmpty() || this.link==null || this.agent.isEmpty()){
            return true;
        }
        return false;
    }
}
