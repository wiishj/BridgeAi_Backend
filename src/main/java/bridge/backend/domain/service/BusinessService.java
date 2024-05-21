package bridge.backend.domain.service;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Type;
import bridge.backend.domain.entity.dto.BusinessRequestDTO;
import bridge.backend.domain.entity.dto.BusinessResponseDTO;
import bridge.backend.domain.repository.BusinessRepository;
import bridge.backend.global.exception.BadRequestException;
import bridge.backend.global.exception.ExceptionCode;
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

import static bridge.backend.global.exception.ExceptionCode.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;

    /*for admin*/
    public Business findBusinessById(Long id){
       return businessRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_BUSINESS_ID));
    }
    @Transactional
    public Long saveBusiness(BusinessRequestDTO businessDTO){
        if(businessDTO.isNull()){
            throw new BadRequestException(INVALID_BUSINESS_REQUEST);
        }
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
        business.setCreatedAt(LocalDateTime.now());

        businessRepository.save(business);
        return business.getId();
    }

    @Transactional
    public void deleteBusiness(Long id){
        Business business = findBusinessById(id);
        businessRepository.delete(business);
    }

    @Transactional
    public void updateBusiness(Long id, BusinessRequestDTO businessDTO){
        if(businessDTO.isNull()){
            throw new BadRequestException(INVALID_BUSINESS_REQUEST);
        }
        List<Type> types = businessDTO.getTypes().stream()
                .map(Type::fromText)
                .collect(Collectors.toList());
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(),businessDTO.getDeadline());

        Business business = findBusinessById(id);

        business.setTitle(businessDTO.getTitle());
        business.setTypes(types);
        business.setDeadline(businessDTO.getDeadline());
        business.setAgent(businessDTO.getAgent());
        business.setDDay((int)dDay);
        business.setLink(businessDTO.getLink());
    }

    /*for user*/
    /*filter기능*/
    public List<BusinessResponseDTO> findBusinessByType(List<Integer> idxList, Pageable pageable){
        if(idxList==null || idxList.isEmpty()){
            throw new BadRequestException(NOT_SET_TYPE);
        }

        List<Type> types = idxList.stream()
                .map(Type::fromIdx)
                .collect(Collectors.toList());
        Page<Business> businesses = businessRepository.findByTypesContainingAll(types, types.size(), pageable);

        if(businesses==null || businesses.isEmpty()){
            throw new BadRequestException(NOT_FOUND_BUSINESS_TYPE);
        }

        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }
    /*calendar+filter기능*/
    public List<BusinessResponseDTO> findBusinessByMonthAndFilter(YearMonth date, List<Integer> idxList){
        LocalDate startDate = date.atDay(1);
        LocalDate endDate = date.atEndOfMonth();
        List<Business> businesses;
        if(idxList==null || idxList.isEmpty()){ //filter기능 설정 안함
            businesses = businessRepository.findByDeadlineBetween(startDate, endDate);
            if(businesses==null || businesses.isEmpty()){
                throw new BadRequestException(NOT_FOUND_BUSINESS_MONTH);
            }
        }
        else{
            List<Type> types = idxList.stream()
                    .map(Type::fromIdx)
                    .collect(Collectors.toList());

            businesses = businessRepository.findByDeadlineBetweenAndTypesContainingAll(startDate, endDate, types, types.size());
            if(businesses==null || businesses.isEmpty()){
                throw new BadRequestException(NOT_FOUND_BUSINESS_MONTH_AND_TYPE);
            }
        }
        return businesses.stream()
                .map(BusinessResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<BusinessResponseDTO> findAll(List<Integer> idxList, Pageable pageable){
        if(idxList==null){
            Page<Business> businesses = businessRepository.findAll(pageable);
            if(businesses==null || businesses.isEmpty()){
                throw new BadRequestException(NOT_FOUND_BUSINESS);
            }
            return businesses.stream()
                    .map(BusinessResponseDTO::from)
                    .collect(Collectors.toList());
        }else{
            return findBusinessByType(idxList, pageable);
        }

    }
    /*sortingByDeadline+filter기능*/
    public List<BusinessResponseDTO> findBusinessByDDayGreaterThanZero(List<Integer> idxList, Pageable pageable){
        if(idxList==null){
            Page<Business> businesses = businessRepository.findByDDayGreaterThanZero(pageable);
            if(businesses==null || businesses.isEmpty()){
                throw new BadRequestException(NOT_FOUND_BUSINESS);
            }
            return businesses.stream()
                    .map(BusinessResponseDTO::from)
                    .collect(Collectors.toList());
        }else{
            return findBusinessByType(idxList, pageable);

        }
    }

}
