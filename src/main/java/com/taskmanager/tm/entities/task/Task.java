package com.taskmanager.tm.entities.task;


import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "task_type")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Column(name = "component")
    private String component;

    @NotNull
    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;

    @Column(name = "reported_date")
    @CreationTimestamp
    private LocalDateTime reportedDate;

    @Column(name = "last_chage_date")
    private LocalDateTime lastChangeDate;

    @Column(name = "logged_time")
    private String loggedTime;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private List<Attachment> attachments;

    public Task() {
        // required for jpa
    }

    private Task(String name, TaskType taskType, String component, String description, Priority priority,
                 String estimatedTime, LocalDateTime deadlineDate, LocalDateTime reportedDate,
                 LocalDateTime lastChangeDate, String loggedTime, TaskStatus taskStatus) {
        this.name = name;
        this.taskType = taskType;
        this.component = component;
        this.description = description;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.deadlineDate = deadlineDate;
        this.reportedDate = reportedDate;
        this.lastChangeDate = lastChangeDate;
        this.loggedTime = loggedTime;
        this.taskStatus = taskStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private TaskType taskType;
        private String component;
        private String description;
        private Priority priority;
        private String estimatedTime;
        private LocalDateTime deadlineDate;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTaskType(TaskType taskType) {
            this.taskType = taskType;
            return this;
        }

        public Builder withComponent(String component) {
            this.component = component;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder withEstimatedTime(String estimatedTime) {
            this.estimatedTime = estimatedTime;
            return this;
        }

        public Builder withDeadlineDate(LocalDateTime deadlineDate) {
            this.deadlineDate = deadlineDate;
            return this;
        }

        public Task build() {
            LocalDateTime now = LocalDateTime.now();
            return new Task(name, taskType, component, description, priority, estimatedTime, deadlineDate,
                    now, now, "0", TaskStatus.NEW);
        }
    }
}
