package bridge.backend.domain.business.repository;

import bridge.backend.domain.business.entity.Business;
import bridge.backend.domain.plan.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BusinessCustomRepository {
    Page<Business> findAll(Pageable pageable);

    Optional<Business> findById(Long id);

    List<Business> findByDeadlineBetween(LocalDate startDate, LocalDate endDate);

    List<Business> findByDeadlineBetweenAndTypesContainingAll(LocalDate startDate, LocalDate endDate, List<Type> types, long typeCount);

    Page<Business> findByTypesContainingAll(LocalDate today, List<Type> types, long typeCount, Pageable pageable);

    Page<Business> findByDDayGreaterThanZero(LocalDate today, Pageable pageable);

}
