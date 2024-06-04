package bridge.backend.domain.plan.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDTO {
    private String name;
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    public Boolean isNull(){
        if(this.name==null || this.name.isEmpty() || this.email==null || this.email.isEmpty() || this.birth==null || this.phoneNumber==null){
            return true;
        }
        return false;
    }
}
