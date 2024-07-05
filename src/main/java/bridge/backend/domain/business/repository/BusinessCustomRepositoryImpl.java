package bridge.backend.domain.business.repository;

import bridge.backend.domain.business.entity.Business;
import bridge.backend.domain.business.entity.QBusiness;
import bridge.backend.domain.plan.entity.Type;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusinessCustomRepositoryImpl implements BusinessCustomRepository {
    private JPAQueryFactory queryFactory;

    @Override
    public Page<Business> findAll(Pageable pageable) {
        QBusiness business = QBusiness.business;

        List<Business> businesses = queryFactory.selectFrom(business)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(business.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(businesses, pageable, () -> queryFactory.selectFrom(business).fetch().size());
    }

    @Override
    public Optional<Business> findById(Long id) {
        QBusiness business = QBusiness.business;

        Business result = queryFactory.selectFrom(business)
                .where(business.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Business> findByDeadlineBetween(LocalDate startDate, LocalDate endDate) {
        QBusiness business = QBusiness.business;

        return queryFactory.selectFrom(business)
                .where(business.deadline.between(startDate, endDate))
                .fetch();
    }

    @Override
    public List<Business> findByDeadlineBetweenAndTypesContainingAll(LocalDate startDate, LocalDate endDate, List<Type> types, long typeCount) {
        QBusiness business = QBusiness.business;

        BooleanExpression typesCondition = business.types.any().in(types);

        return queryFactory.selectFrom(business)
                .distinct()
                .where(business.deadline.between(startDate, endDate)
                        .and(typesCondition))
                .groupBy(business)
                .having(business.types.size().eq((int)typeCount))
                .fetch();
    }

    @Override
    public Page<Business> findByTypesContainingAll(LocalDate today, List<Type> types, long typeCount, Pageable pageable) {
        QBusiness business = QBusiness.business;

        BooleanExpression typesCondition = business.types.any().in(types);

        List<Business> businesses = queryFactory.selectFrom(business)
                .distinct()
                .where(business.deadline.goe(today)
                        .and(typesCondition))
                .groupBy(business)
                .having(business.types.size().eq((int)typeCount))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(business)
                .distinct()
                .where(business.deadline.goe(today)
                        .and(typesCondition))
                .groupBy(business)
                .having(business.types.size().eq((int)typeCount))
                .fetchCount();

        return new PageImpl<>(businesses, pageable, total);
    }

    @Override
    public Page<Business> findByDDayGreaterThanZero(LocalDate today, Pageable pageable) {
        QBusiness business = QBusiness.business;

        List<Business> businesses = queryFactory.selectFrom(business)
                .where(business.deadline.goe(today))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(business)
                .where(business.deadline.goe(today))
                .fetchCount();

        return new PageImpl<>(businesses, pageable, total);
    }
}
