package bridge.backend.domain.business.repository;

import bridge.backend.domain.business.entity.Business;
import bridge.backend.domain.plan.entity.Type;
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
public interface BusinessRepository extends JpaRepository<Business, Long>, BusinessCustomRepository {
    Page<Business> findAll(Pageable pageable);

    Optional<Business> findById(Long id);

}
