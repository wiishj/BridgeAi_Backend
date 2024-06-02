package bridge.backend.domain.payment.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequestDTO {
    Long itemId;
    String merchantUid;
    String payMethod;
    BigDecimal amount;
}
