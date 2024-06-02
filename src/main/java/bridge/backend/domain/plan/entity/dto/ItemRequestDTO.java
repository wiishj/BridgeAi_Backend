package bridge.backend.domain.plan.entity.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDTO {
    private String title;
    @Size(max=50000)
    private String input1;
    @Size(max=50000)
    private String input2;
    @Size(max=50000)
    private String input3;
    @Size(max=50000)
    private String input4;
    @Size(max=50000)
    private String input5;

    private Boolean term1;
    private Boolean term2;
    private Boolean term3;

    public Boolean isNull(){
        if(this.title==null || this.title.isEmpty() || this.input1==null || this.input2==null|| this.input3==null || this.input4==null|| this.input5==null || this.term1==null|| this.term2==null|| this.term3==null){
            return true;
        }
        return false;
    }
}
