package bridge.backend.domain.payment.service;

import bridge.backend.domain.payment.entity.Order;
import bridge.backend.domain.payment.entity.dto.OrderRequestDTO;
import bridge.backend.domain.payment.entity.dto.OrderResponseDTO;
import bridge.backend.domain.payment.repository.OrderRepository;
import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.Member;
import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.MemberResponseDTO;
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
        Member host = item.getHost();
        return OrderResponseDTO.from(MemberResponseDTO.from(host), ItemResponseDTO.from(item), order);
    }
    @Transactional
    public OrderResponseDTO saveOrderInfo(OrderRequestDTO requestDTO){
        Item item = itemRepository.findById(requestDTO.getPlanId()).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        Order order = new Order();
        order.setAmount(requestDTO.getAmount());
        order.setMerchantUid(requestDTO.getMerchantUid());
        order.setPayMethod(requestDTO.getPayMethod());
        item.addOrder(order);
        item.setIsPaid(true);

        ItemResponseDTO itemRes = ItemResponseDTO.from(item);
        MemberResponseDTO memberRes = MemberResponseDTO.from(item.getHost());

        return OrderResponseDTO.from(memberRes, itemRes, order);
    }
}
