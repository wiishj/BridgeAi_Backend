package bridge.backend.domain.payment.entity.dto;

import bridge.backend.domain.payment.entity.Order;
import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.MemberResponseDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    MemberResponseDTO member;
    ItemResponseDTO item;

    Long orderId;
    String merchantUid;
    String payMethod;
    BigDecimal amount;

    public static OrderResponseDTO from(MemberResponseDTO memberRes, ItemResponseDTO itemRes, Order entity){
        return OrderResponseDTO.builder()
                .member(memberRes)
                .item(itemRes)
                .orderId(entity.getId())
                .merchantUid(entity.getMerchantUid())
                .payMethod(entity.getPayMethod())
                .amount(entity.getAmount())
                .build();
    }
}
