package bridge.backend.domain.controller;

import bridge.backend.domain.entity.Plan;
import bridge.backend.domain.entity.dto.*;
import bridge.backend.domain.service.MemberService;
import bridge.backend.domain.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;
    private final MemberService memberService;
    @PostMapping()
    public ResponseEntity<?> createPlan(@Valid @RequestBody PlanRequestDTO planRequestDTO){
        Long memberId = memberService.saveMember(planRequestDTO.getMember());
        Long planId = planService.savePlan(planRequestDTO.getItem(), memberService.findMemberById(memberId));

        Map<String, Long> res = new HashMap<>();
        res.put("planId", planId);
        return ResponseEntity.ok(res);
    }

    /*for admin*/
    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlanById(@PathVariable("planId")Long id){
        PlanResponseDTO res = planService.findPlanById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping()
    public ResponseEntity<?> getAllPlans(@RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10);
        List<PlanResponseDTO> res = planService.findAll(pageable);
        return ResponseEntity.ok(Map.of("size", res.size(), "data", res));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable("planId") Long id, @Valid @RequestBody PlanRequestDTO planRequestDTO){
        Long memberId = planService.findPlanById(id).getHostId();
        memberService.updateMember(memberId, planRequestDTO.getMember());
        planService.updatePlan(id, planRequestDTO.getItem());
        return new ResponseEntity<>("해당 사계서가 정상적으로 수정되었습니다.", HttpStatus.OK);
    }

}
