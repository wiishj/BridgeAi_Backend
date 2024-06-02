package bridge.backend.domain.payment.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
public class PrePaymentRequestDTO {
    String merchantUid;
    BigDecimal amount;
}
