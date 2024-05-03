package bridge.backend.service;

import bridge.backend.entity.Business;
import bridge.backend.entity.Type;
import bridge.backend.entity.dto.BusinessRequestDTO;
import bridge.backend.entity.dto.BusinessResponseDTO;
import bridge.backend.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;

    /*for admin*/
    @Transactional
    public Long saveBusiness(BusinessRequestDTO businessDTO){
        List<Type> types = businessDTO.getTypes().stream()
                .map(Type::fromText)
                .collect(Collectors.toList());
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(),businessDTO.getDeadline());

        Business business = new Business();
        business.setTitle(businessDTO.getTitle());
        business.setTypes(types);
        business.setDeadline(businessDTO.getDeadline());
        business.setAgent(businessDTO.getAgent());
        business.setDDay((int)dDay);
        business.setLink(businessDTO.getLink());
        business.setStar(false);

        businessRepository.save(business);
        return business.getId();
    }

    @Transactional
    public void deleteBusiness(Long id){
        Business business = businessRepository.findById(id).get();
        businessRepository.delete(business);
    }

    /*for user*/
    public List<BusinessResponseDTO> findBusinessByType(List<Integer> idxList){

        List<Type> types = idxList.stream()
                .map(Type::fromIdx)
                .collect(Collectors.toList());

        List<Business> businesses = businessRepository.findByTypesContainingAll(types, types.size());

        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }
    @Transactional
    public void toggleStar(Long id){
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        optionalBusiness.ifPresent(business->{
            boolean currentStarStatus = business.getStar();
            business.setStar(!currentStarStatus);
            businessRepository.save(business);
        });
    }

    public List<BusinessResponseDTO> findBusinessByMonth(YearMonth date){
        LocalDate startDate = date.atDay(1);
        LocalDate endDate = date.atEndOfMonth();
        List<Business> businesses = businessRepository.findByDeadlineBetween(startDate, endDate);
        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<BusinessResponseDTO> findAll(){
        List<Business> businesses = businessRepository.findAll();
        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }

}
