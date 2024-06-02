package bridge.backend.domain.payment.repository;

import bridge.backend.domain.payment.entity.PrePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrePaymentRepository extends JpaRepository<PrePayment, Long> {
    PrePayment findByMerchantUid(String merchantUid);
}
