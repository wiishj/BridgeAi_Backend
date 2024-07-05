package bridge.backend.domain.payment.service;

import bridge.backend.domain.payment.entity.Order;
import bridge.backend.domain.payment.entity.dto.OrderRequestDTO;
import bridge.backend.domain.payment.entity.dto.OrderResponseDTO;
import bridge.backend.domain.payment.repository.OrderRepository;
import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.User;
import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.UserResponseDTO;
import bridge.backend.domain.plan.repository.ItemRepository;
import bridge.backend.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static bridge.backend.global.exception.ExceptionCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderResponseDTO findOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_ORDER_ID));
        Item item = order.getItem();
        User host = item.getHost();
        return OrderResponseDTO.from(UserResponseDTO.from(host), ItemResponseDTO.from(item), order);
    }
    @Transactional
    public OrderResponseDTO saveOrderInfo(OrderRequestDTO requestDTO){
        Item item = itemRepository.findById(requestDTO.getItemId()).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        Order order = Order.builder()
                .amount(requestDTO.getAmount())
                .merchantUid(requestDTO.getMerchantUid())
                .payMethod(requestDTO.getPayMethod())
                .build();
        orderRepository.save(order);

        item.addOrder(order);
        item.setIsPaid(true);

        ItemResponseDTO itemRes = ItemResponseDTO.from(item);
        UserResponseDTO memberRes = UserResponseDTO.from(item.getHost());

        return OrderResponseDTO.from(memberRes, itemRes, order);
    }
}
