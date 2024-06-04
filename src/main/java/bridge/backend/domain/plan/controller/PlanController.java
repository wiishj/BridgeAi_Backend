package bridge.backend.domain.plan.controller;

import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.dto.*;
import bridge.backend.domain.plan.repository.ItemRepository;
import bridge.backend.domain.plan.service.UserService;
import bridge.backend.domain.plan.service.ItemService;
import bridge.backend.global.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static bridge.backend.global.exception.ExceptionCode.NOT_FOUND_PLAN_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final UserService userService;
    @PostMapping()
    public ResponseEntity<?> createPlan(@Valid @RequestBody PlanRequestDTO planRequestDTO){
        UserResponseDTO userRes = userService.saveMember(planRequestDTO.getUser());
        ItemResponseDTO itemRes = itemService.savePlan(planRequestDTO.getItem(), userService.findMemberById(userRes.getUserId()));

        PlanResponseDTO res = new PlanResponseDTO(userRes, itemRes);
        return ResponseEntity.ok(res);
    }

    /*for admin*/
    @GetMapping("/{itemId}")
    public ResponseEntity<?> getPlanById(@PathVariable("itemId")Long id){
        PlanResponseDTO res  = itemService.findPlanById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping()
    public ResponseEntity<?> getAllPlans(@RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10);
        List<PlanResponseDTO> res = itemService.findAll(pageable);
        return ResponseEntity.ok(Map.of("size", res.size(), "data", res));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> updatePlan(@PathVariable("itemId") Long id, @Valid @RequestBody PlanRequestDTO planRequestDTO){
        Item item = itemRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        Long memberId = item.getHost().getId();
        userService.updateMember(memberId, planRequestDTO.getUser());
        itemService.updatePlan(id, planRequestDTO.getItem(), userService.findMemberById(memberId));
        return new ResponseEntity<>("해당 사계서가 정상적으로 수정되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/isSent")
    public ResponseEntity<?> updateIsSent(@RequestBody IdDTO idDTO){
        itemService.updateIsSent(idDTO.getId());
        return new ResponseEntity<>("해당 사계서가 독스훈트에 전달됨이 정상적으로 표시되었습니다.", HttpStatus.OK);
    }

}
