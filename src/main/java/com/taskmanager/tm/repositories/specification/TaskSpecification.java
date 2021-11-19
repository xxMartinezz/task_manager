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

    private final TaskFilterDTO filter;
    private static final String LIKE = "%";

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getName() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), LIKE + filter.getName().toLowerCase() + LIKE));
        }

        if (filter.getTaskType() != null) {
            if (filter.getTaskType() == TaskType.ASSIGNMENT) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskType"), TaskType.ASSIGNMENT));
            }
            if (filter.getTaskType() == TaskType.BLOCKER) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskType"), TaskType.BLOCKER));
            }
            if (filter.getTaskType() == TaskType.BUG) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskType"), TaskType.BUG));
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
            if (filter.getPriority() == Priority.HIGH) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("priority"), Priority.HIGH));
            }
            if (filter.getPriority() == Priority.HIGHEST) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("priority"), Priority.HIGHEST));
            }
            if (filter.getPriority() == Priority.MEDIUM) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("priority"), Priority.MEDIUM));
            }
            if (filter.getPriority() == Priority.LOW) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("priority"), Priority.LOW));
            }
            if (filter.getPriority() == Priority.LOWEST) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("priority"), Priority.LOWEST));
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
            if (filter.getTaskStatus() == TaskStatus.NEW) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.NEW));
            }
            if (filter.getTaskStatus() == TaskStatus.ARCHIVED) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.ARCHIVED));
            }
            if (filter.getTaskStatus() == TaskStatus.FAILED) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.FAILED));
            }
            if (filter.getTaskStatus() == TaskStatus.IN_PROGRESS) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.IN_PROGRESS));
            }
            if (filter.getTaskStatus() == TaskStatus.IN_TEST) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.IN_TEST));
            }
            if (filter.getTaskStatus() == TaskStatus.RESOLVED) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.RESOLVED));
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
