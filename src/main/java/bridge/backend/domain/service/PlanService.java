package bridge.backend.domain.service;

import bridge.backend.domain.entity.Member;
import bridge.backend.domain.entity.Plan;
import bridge.backend.domain.entity.dto.*;
import bridge.backend.domain.repository.MemberRepository;
import bridge.backend.domain.repository.PlanRepository;
import bridge.backend.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bridge.backend.global.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final MemberService memberService;

    public PlanResponseDTO findPlanById(Long id){
        Plan plan = planRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        return PlanResponseDTO.from(plan);
    }

    public List<PlanResponseDTO> findAll(Pageable pageable){
        Page<Plan> plans = planRepository.findAll(pageable);
        if(plans==null || plans.isEmpty()){
            throw new BadRequestException(NOT_FOUND_PLAN);
        }
        return plans.stream()
                .map(PlanResponseDTO::from)
                .collect(Collectors.toList());
    }
    @Transactional
    public Long savePlan(ItemRequestDTO itemRequestDTO, Member member){
        if(itemRequestDTO.isNull()){
            throw new BadRequestException(INVALID_PLAN_REQUEST);
        }
        if(!itemRequestDTO.getTerm1() || !itemRequestDTO.getTerm2()){
            throw new BadRequestException(INVALID_NECESSARY_TERM);
        }
        if(itemRequestDTO.getInput1().length()<60){
            throw new BadRequestException(INVALID_ITEM1_SIZE);
        }
        if(itemRequestDTO.getInput2().length()<140){
            throw new BadRequestException(INVALID_ITEM2_SIZE);
        }

        Plan plan = new Plan();
        plan.setTitle(itemRequestDTO.getTitle());
        plan.setInput1(itemRequestDTO.getInput1());
        plan.setInput2(itemRequestDTO.getInput2());
        plan.setInput3(itemRequestDTO.getInput3());
        plan.setInput4(itemRequestDTO.getInput4());
        plan.setInput5(itemRequestDTO.getInput5());

        plan.setTerm1(itemRequestDTO.getTerm1());
        plan.setTerm2(itemRequestDTO.getTerm2());
        plan.setTerm3(itemRequestDTO.getTerm3());

        plan.setHost(member);

        planRepository.save(plan);
        return plan.getId();
    }

    @Transactional
    public void updatePlan(Long id, ItemRequestDTO itemRequestDTO){
        if(itemRequestDTO.isNull()){
            throw new BadRequestException(INVALID_PLAN_REQUEST);
        }
        if(!itemRequestDTO.getTerm1() || !itemRequestDTO.getTerm2()){
            throw new BadRequestException(INVALID_NECESSARY_TERM);
        }
        if(itemRequestDTO.getInput1().length()<60){
            throw new BadRequestException(INVALID_ITEM1_SIZE);
        }
        if(itemRequestDTO.getInput2().length()<140){
            throw new BadRequestException(INVALID_ITEM2_SIZE);
        }

        Plan plan = planRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        plan.setTitle(itemRequestDTO.getTitle());
        plan.setInput1(itemRequestDTO.getInput1());
        plan.setInput2(itemRequestDTO.getInput2());
        plan.setInput3(itemRequestDTO.getInput3());
        plan.setInput4(itemRequestDTO.getInput4());
        plan.setInput5(itemRequestDTO.getInput5());

        plan.setTerm1(itemRequestDTO.getTerm1());
        plan.setTerm2(itemRequestDTO.getTerm2());
        plan.setTerm3(itemRequestDTO.getTerm3());
    }

}
