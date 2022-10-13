package com.taskmanager.tm.repositories.specification;

import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import com.taskmanager.tm.services.dto.task.TaskFilterDTO;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Builder
public class TaskSpecification implements Specification {

    private final transient TaskFilterDTO filter;
    private static final String LIKE = "%";
    private static final String TASK_TYPE = "taskType";
    private static final String PRIORITY = "priority";
    private static final String TASK_STATUS = "taskStatus";

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getName() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), LIKE + filter.getName().toLowerCase() + LIKE));
        }

        if (filter.getTaskType() != null) {
            switch (filter.getTaskType()) {
                case ASSIGNMENT -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_TYPE), TaskType.ASSIGNMENT));
                case BLOCKER -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_TYPE), TaskType.BLOCKER));
                case BUG -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_TYPE), TaskType.BUG));
            }
        }

        if (filter.getComponent() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("component")), LIKE + filter.getComponent().toLowerCase() + LIKE));
        }

        if (filter.getDescription() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), LIKE + filter.getDescription().toLowerCase() + LIKE));
        }

        if (filter.getPriority() != null) {
            switch(filter.getPriority()) {
                case HIGH -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(PRIORITY), Priority.HIGH));
                case HIGHEST -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(PRIORITY), Priority.HIGHEST));
                case MEDIUM -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(PRIORITY), Priority.MEDIUM));
                case LOW -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(PRIORITY), Priority.LOW));
                case LOWEST -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(PRIORITY), Priority.LOWEST));
            }
        }

        if (filter.getDeadlineDate() != null) {
            Predicate fromDate =
                    criteriaBuilder.lessThanOrEqualTo(root.get("deadlineDate"), filter.getDeadlineDate());
            Predicate toDate =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("deadlineDate"), filter.getDeadlineDate());
            predicate.getExpressions()
                    .add(criteriaBuilder.and(fromDate, toDate));
        }

        if (filter.getReportedDate() != null) {
            Predicate fromDate =
                    criteriaBuilder.lessThanOrEqualTo(root.get("reportedDate"), filter.getReportedDate());
            Predicate toDate =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("reportedDate"), filter.getReportedDate());
            predicate.getExpressions()
                    .add(criteriaBuilder.and(fromDate, toDate));
        }

        if (filter.getLastChangeDate() != null) {
            Predicate fromDate =
                    criteriaBuilder.lessThanOrEqualTo(root.get("lastChangeDate"), filter.getLastChangeDate());
            Predicate toDate =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("lastChangeDate"), filter.getLastChangeDate());
            predicate.getExpressions()
                    .add(criteriaBuilder.and(fromDate, toDate));
        }

        if (filter.getEstimatedTime() != null) {
            Predicate from =
                    criteriaBuilder.lessThanOrEqualTo(root.get("estimatedTime"), filter.getEstimatedTime());
            Predicate to =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("estimatedTime"), filter.getEstimatedTime());
            predicate.getExpressions()
                    .add(criteriaBuilder.and(from, to));
        }

        if (filter.getLoggedTime() != null) {
            Predicate from =
                    criteriaBuilder.lessThanOrEqualTo(root.get("loggedTime"), filter.getLoggedTime());
            Predicate to =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("loggedTime"), filter.getLoggedTime());
            predicate.getExpressions()
                    .add(criteriaBuilder.and(from, to));
        }

        if (filter.getTaskStatus() != null) {
            switch(filter.getTaskStatus()) {
                case NEW -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.NEW));
                case ARCHIVED -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.ARCHIVED));
                case FAILED -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.FAILED));
                case IN_PROGRESS -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.IN_PROGRESS));
                case IN_TEST -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.IN_TEST));
                case RESOLVED -> predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get(TASK_STATUS), TaskStatus.RESOLVED));
            }
        }

        if (filter.getReportedUserId() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.equal(root.get("reportedUser"), filter.getReportedUserId()));
        }

        if (filter.getAssignedUserId() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.equal(root.get("assignedUser"), filter.getAssignedUserId()));
        }

        return predicate;
    }
}
