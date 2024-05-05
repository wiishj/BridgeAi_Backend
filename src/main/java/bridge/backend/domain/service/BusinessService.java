package bridge.backend.domain.service;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Type;
import bridge.backend.domain.entity.dto.BusinessRequestDTO;
import bridge.backend.domain.entity.dto.BusinessResponseDTO;
import bridge.backend.domain.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
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
        business.setCreatedAt(LocalDateTime.now());

        businessRepository.save(business);
        return business.getId();
    }

    @Transactional
    public void deleteBusiness(Long id){
        Business business = businessRepository.findById(id).get();
        businessRepository.delete(business);
    }

    /*for user*/
    public List<BusinessResponseDTO> findBusinessByType(List<Integer> idxList, Pageable pageable){
        List<Type> types = idxList.stream()
                .map(Type::fromIdx)
                .collect(Collectors.toList());

        Page<Business> businesses = businessRepository.findByTypesContainingAll(types, types.size(), pageable);

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

    public List<BusinessResponseDTO> findBusinessByMonthAndFilter(YearMonth date, List<Integer> idxList){
        LocalDate startDate = date.atDay(1);
        LocalDate endDate = date.atEndOfMonth();
        if(idxList==null){
            List<Business> businesses = businessRepository.findByDeadlineBetween(startDate, endDate);
            return businesses.stream()
                    .map(BusinessResponseDTO::from)
                    .collect(Collectors.toList());
        }
        else{
            List<Type> types = idxList.stream()
                    .map(Type::fromIdx)
                    .collect(Collectors.toList());
            List<Business> businesses = businessRepository.findByDeadlineBetweenAndTypesContainingAll(startDate, endDate, types, types.size());
            return businesses.stream()
                    .map(BusinessResponseDTO::from)
                    .collect(Collectors.toList());
        }
    }

    public List<BusinessResponseDTO> findAll(Pageable pageable){
        Page<Business> businesses = businessRepository.findAll(pageable);
        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<BusinessResponseDTO> findBusinessByDDayGreaterThanZero(Pageable pageable){
        Page<Business> business = businessRepository.findByDDayGreaterThanZero(pageable);
        return business.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }

}
