package bridge.backend.repository;

import bridge.backend.entity.Business;
import bridge.backend.entity.Type;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findById(Long id);
    List<Business> findByDeadlineBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT DISTINCT b FROM Business b JOIN b.types t WHERE t IN :types GROUP BY b HAVING COUNT(DISTINCT t) = :typeCount")
    List<Business> findByTypesContainingAll(@Param("types") List<Type> types, @Param("typeCount") long typeCount);
}
