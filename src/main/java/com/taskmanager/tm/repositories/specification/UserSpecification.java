package com.taskmanager.tm.repositories.specification;

import com.taskmanager.tm.services.dto.user.UserFilterDTO;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Supplier;

@Builder
public class UserSpecification implements Specification {

    private final transient UserFilterDTO filter;
    private static final String LIKE = "%";

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        addPredicateForStringField(predicate, root, criteriaBuilder, filter::getName, "name");
        addPredicateForStringField(predicate, root, criteriaBuilder, filter::getSurname, "surname");
        addPredicateForBooleanField(predicate, root, criteriaBuilder, filter::getActive, "active");

        return predicate;
    }

    private void addPredicateForStringField(Predicate predicate, Root root, CriteriaBuilder criteriaBuilder, Supplier<String> methodFromFilter, String fieldNameFromEntity) {
        if (methodFromFilter.get() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldNameFromEntity)),
                            LIKE + methodFromFilter.get().toLowerCase() + LIKE));
        }
    }

    private void addPredicateForBooleanField(Predicate predicate, Root root, CriteriaBuilder criteriaBuilder, Supplier<Boolean> methodFromFilter, String fieldNameFromEntity) {
        if (methodFromFilter.get() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.equal(root.get(fieldNameFromEntity), methodFromFilter.get()));
        }
    }
}
