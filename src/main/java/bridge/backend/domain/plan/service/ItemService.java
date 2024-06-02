package bridge.backend.domain.plan.service;

import bridge.backend.domain.plan.entity.Item;
import bridge.backend.domain.plan.entity.Member;
import bridge.backend.domain.plan.entity.dto.ItemRequestDTO;
import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.MemberResponseDTO;
import bridge.backend.domain.plan.entity.dto.PlanResponseDTO;
import bridge.backend.domain.plan.repository.ItemRepository;
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
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberService memberService;

    public PlanResponseDTO findPlanById(Long id){
        Item item = itemRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        Member member = item.getHost();
        return new PlanResponseDTO(MemberResponseDTO.from(member), ItemResponseDTO.from(item));
    }

    public List<PlanResponseDTO> findAll(Pageable pageable){
        Page<Item> items = itemRepository.findAll(pageable);
        if(items==null || items.isEmpty()){
            throw new BadRequestException(NOT_FOUND_PLAN);
        }
        return items.stream()
                .map(item -> {
                    Member member = item.getHost();
                    return new PlanResponseDTO(MemberResponseDTO.from(member), ItemResponseDTO.from(item));
                })
                .collect(Collectors.toList());
    }
    @Transactional
    public ItemResponseDTO savePlan(ItemRequestDTO itemRequestDTO, Member member){
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

        Item item = new Item();
        item.setTitle(itemRequestDTO.getTitle());
        item.setInput1(itemRequestDTO.getInput1());
        item.setInput2(itemRequestDTO.getInput2());
        item.setInput3(itemRequestDTO.getInput3());
        item.setInput4(itemRequestDTO.getInput4());
        item.setInput5(itemRequestDTO.getInput5());

        item.setTerm1(itemRequestDTO.getTerm1());
        item.setTerm2(itemRequestDTO.getTerm2());
        item.setTerm3(itemRequestDTO.getTerm3());

        item.setIsPaid(false);
        item.setOrder(null);
        member.addPlans(item);
        itemRepository.save(item);
        return ItemResponseDTO.from(item);
    }

    @Transactional
    public void updatePlan(Long id, ItemRequestDTO itemRequestDTO, Member member){
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

        Item item = itemRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_PLAN_ID));
        item.setTitle(itemRequestDTO.getTitle());
        item.setInput1(itemRequestDTO.getInput1());
        item.setInput2(itemRequestDTO.getInput2());
        item.setInput3(itemRequestDTO.getInput3());
        item.setInput4(itemRequestDTO.getInput4());
        item.setInput5(itemRequestDTO.getInput5());

        item.setTerm1(itemRequestDTO.getTerm1());
        item.setTerm2(itemRequestDTO.getTerm2());
        item.setTerm3(itemRequestDTO.getTerm3());
        member.addPlans(item);
    }

}
