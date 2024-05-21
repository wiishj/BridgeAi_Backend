package bridge.backend.domain.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRequestDTO {
    private String email;
    private LocalDate birth;
    private String phoneNumber;

    public Boolean isNull(){
        if(this.email==null || this.birth==null || this.phoneNumber==null){
            return true;
        }
        return false;
    }
}
