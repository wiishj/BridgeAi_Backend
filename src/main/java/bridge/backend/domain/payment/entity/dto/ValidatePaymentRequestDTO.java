package bridge.backend.domain.payment.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValidatePaymentRequestDTO {
    String impUid;
    String merchantUid;
}
