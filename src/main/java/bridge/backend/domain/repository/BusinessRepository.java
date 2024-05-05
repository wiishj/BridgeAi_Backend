package bridge.backend.domain.repository;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Page<Business> findAll(Pageable pageable);
    Optional<Business> findById(Long id);
    List<Business> findByDeadlineBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT DISTINCT b FROM Business b JOIN b.types t WHERE t IN :types GROUP BY b HAVING COUNT(DISTINCT t) = :typeCount")
    Page<Business> findByTypesContainingAll(@Param("types") List<Type> types, @Param("typeCount") long typeCount, Pageable pageable);
    @Query("SELECT DISTINCT b FROM Business b JOIN b.types t WHERE b.deadline BETWEEN :startDate AND :endDate AND t IN :types GROUP BY b HAVING COUNT(DISTINCT t) = :typeCount")
    List<Business> findByDeadlineBetweenAndTypesContainingAll(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("types") List<Type> types,
            @Param("typeCount") long typeCount);
    @Query("SELECT b FROM Business b WHERE b.dDay >= 0")
    Page<Business> findByDDayGreaterThanZero(Pageable pageable);
}
