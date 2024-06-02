package bridge.backend.domain.payment.controller;

import bridge.backend.domain.payment.entity.dto.OrderRequestDTO;
import bridge.backend.domain.payment.entity.dto.OrderResponseDTO;
import bridge.backend.domain.payment.service.OrderService;
import bridge.backend.domain.plan.service.ItemService;
import bridge.backend.domain.plan.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    @PostMapping()
    public ResponseEntity<?> createPlan(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO res = orderService.saveOrderInfo(orderRequestDTO);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPlanById(@PathVariable("orderId")Long id){
        OrderResponseDTO res = orderService.findOrderById(id);
        return ResponseEntity.ok(res);
    }
}
