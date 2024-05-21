package bridge.backend.domain.repository;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Page<Plan> findAll(Pageable pageable);
    Optional<Plan> findById(Long id);
}
