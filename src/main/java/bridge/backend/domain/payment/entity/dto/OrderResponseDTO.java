package bridge.backend.domain.payment.entity.dto;

import bridge.backend.domain.payment.entity.Order;
import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.UserResponseDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    UserResponseDTO user;
    ItemResponseDTO item;

    Long orderId;
    String merchantUid;
    String payMethod;
    BigDecimal amount;

    public static OrderResponseDTO from(UserResponseDTO userRes, ItemResponseDTO itemRes, Order entity){
        return OrderResponseDTO.builder()
                .user(userRes)
                .item(itemRes)
                .orderId(entity.getId())
                .merchantUid(entity.getMerchantUid())
                .payMethod(entity.getPayMethod())
                .amount(entity.getAmount())
                .build();
    }
}
